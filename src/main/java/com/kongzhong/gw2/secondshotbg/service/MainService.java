package com.kongzhong.gw2.secondshotbg.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kongzhong.external.domaim.SuccessOrderResult;
import com.kongzhong.external.log.KongzhongLogger;
import com.kongzhong.external.webservice.WebserviceClient;
import com.kongzhong.gw2.secondshotbg.domain.Item;
import com.kongzhong.gw2.secondshotbg.domain.Order;
import com.kongzhong.gw2.secondshotbg.domain.Term;
import com.kongzhong.gw2.secondshotbg.domain.TermDetailed;
import com.kongzhong.gw2.secondshotbg.domain.TermHistory;
import com.kongzhong.gw2.secondshotbg.domain.Winning;
import com.kongzhong.gw2.secondshotbg.mapper.ItemMapper;
import com.kongzhong.gw2.secondshotbg.mapper.OrderMapper;
import com.kongzhong.gw2.secondshotbg.mapper.TermDetailedMapper;
import com.kongzhong.gw2.secondshotbg.mapper.TermHistoryMapper;
import com.kongzhong.gw2.secondshotbg.mapper.TermMapper;
import com.kongzhong.gw2.secondshotbg.mapper.TermWinningMapper;

@Service
public class MainService {
	
	private static final String ORDER_PREFIX="cdkey_MP";//订单前缀
	
	@Autowired
	private WebserviceClient webserviceClient;//webservice客户端
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private TermDetailedMapper termDetailedMapper;
	
	@Autowired
	private TermMapper termMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private TermWinningMapper termWinningMapper;
	
	@Autowired
	private TermHistoryMapper termHistoryMapper;
	
	@Autowired
	private CdkeyTypeService cdkey;
	
	@Autowired
	private KongzhongLogger logger;
	
	private static final Integer ORDER_PAY_SUCCESS=1;//支付成功
	private static final Integer ORDER_SECONDSHOT_SUCCESS=2;//秒杀成功
	
	
	public void service(long time) {
		System.out.println(new Date() + ": Service start...");
		while(true){
			try {
				List<SuccessOrderResult> successOrderList=webserviceClient.getPaySuccessOrder(ORDER_PREFIX);//根据订单前缀获取支付成功的订单信息
				if(successOrderList.size()>0){
					System.out.println(new Date() + ": Get " + successOrderList.size() + " order tasks.");
					orderTask(successOrderList);//处理订单
				}
				getNoCdkey();
				Thread.sleep(time);//休眠
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(e);//发邮件
			}
		}
	}
	
	/**
	 * 重新获取CDKEY
	 */
	public void getNoCdkey(){
		List<String> noCdekyList=termWinningMapper.getNoCdkey();
		if(null!=noCdekyList && noCdekyList.size()>0){
			for(String orderId:noCdekyList){
				Order order=orderMapper.getOrderByOrderId(orderId);//根据订单号查询出一条订单信息
				int batchId=cdkey.getMap().get(order.getCdkeyType()).getBatchId();//批次号ID
            	String batchCode=cdkey.getMap().get(order.getCdkeyType()).getBatchCode();//校验码
            	//获取cdkey
            	String cdkey="nocdkey";
            	try {
                	cdkey=webserviceClient.getCdkey(order.getOrderId(), order.getAccount(), order.getPayType(), batchId, batchCode, order.getCount(), order.getIp(), order.getOrderId());
				} catch (Exception e) {
					e.printStackTrace();
				}
            	Map<String, Object> map=new HashMap<String, Object>();
            	map.put("cdkey", cdkey);
            	map.put("orderId", order.getOrderId());
            	termWinningMapper.updateCdkey(map);
			}
			
		}
	}
	
	
	/**
	 * 处理支付成功的订单
	 * @param successOrderList
	 */
	private void orderTask(List<SuccessOrderResult> successOrderList){
		for(SuccessOrderResult successOrderResult:successOrderList){
			String orderId=successOrderResult.getShopNo();//订单号
			System.out.println(new Date() + ": Start " + orderId);
			//修改订单表中的订单状态和支付类型
			updateOrder(orderId, successOrderResult.getPaytypeId(),ORDER_PAY_SUCCESS);
			Order order=orderMapper.getOrderByOrderId(orderId);//根据订单号查询出一条订单信息
			if(order==null){
				throw new RuntimeException("从支付成功的订单中获取的订单号，在表中不存在。");
			}
			//返点
			webserviceClient.addPoints(orderId, order.getCount()*20, order.getAccount(), order.getIp());
			updateTerm(order);//拆单
			updateOrder(orderId,0,ORDER_SECONDSHOT_SUCCESS);
			//设置订单状态为成功
			webserviceClient.updateOrderStatus(successOrderResult.getId(), orderId, order.getAccount(), order.getIp());
				
		}
	}
	/**
	 * 拆单
	 * @param order 订单对象
	 */
	@Transactional
	public void updateTerm(Order order){
		int termId=order.getTermId();//某一个商品的当前期数的ID
		int count=order.getCount();//秒拍的数量
		int keyCount=updateTermDetailed(order);
		if(keyCount>0){
			int up=updateTermById(keyCount,termId);//修改表t_gw2_mp_term中的秒杀的数量
			if(up!=1){
				throw new RuntimeException("@修改秒杀数量返回结果不为1。"+termId);
			}
			//插入到历史记录表
			addTermHistory(termId,order,keyCount);
			//检查表t_gw2_mp_term中的秒杀数量是否等于份数
			checkTerm(termId,order,count-keyCount);
		}else if(keyCount==0){
			Term term=termMapper.getTermById(termId);//查询出表t_gw2_mp_term表中的数据
			int currentTermDetailedCount=termDetailedMapper.getCountByTermId(termId);//在表t_gw2_mp_term_detailed中查询出对应的条数
			if(term.getServings()<currentTermDetailedCount){
				throw new RuntimeException("秒杀的数量大于实际的份数,"+termId);
			}else if(term.getServings()>currentTermDetailedCount){
				addTermDetailed(order,term,count);
			}else{
				//如果term.getServings()==currentTermDetailedCount
				Term unFinishTerm=termMapper.getTermByItemId(term.getItemId());
				if(unFinishTerm==null){
					throw new RuntimeException("等待创建新期。"+termId);
				}
				order=orderMapper.getOrderByOrderId(order.getOrderId());//根据订单号查询出一条订单信息,这一行是多余的。
				order.setTermId(unFinishTerm.getId());
				updateTerm(order);
			}
		}
	}
	
	/**
	 * 检查秒杀的数量是否和商品份数相等，如果相等，就抽奖，获取cdkey
	 * @param termId
	 * @param order
	 * @param addOver
	 */
	@Transactional
	public void checkTerm(Integer termId,Order order,int addOver){
		Term term=termMapper.getTermById(termId);//查询出表t_gw2_mp_term表中的数据
		if(term.getServings().equals(term.getAuctionServings())){
			Random rand=new Random();
        	int num = rand.nextInt(term.getServings());
        	TermDetailed termDetailed=getLuckyKey(termId,num+1);//选出一位中奖信息
        	if(termDetailed==null){
        		throw new RuntimeException("选出中奖用户失败"+termId);
        	}
        	Item item=itemMapper.getItemById(term.getItemId());//获取商品的信息
        	//根据用户名和商品名称来查询在获奖表中是否已经获得某一种类型CDKEY。
        	
        	if(!isWinning(termDetailed.getUserName(),item.getName())){ //如果没有获过某种类型的CDKEY
        		//获取批次号ID和校验码
            	int batchId=cdkey.getMap().get(order.getCdkeyType()).getBatchId();//批次号ID
            	String batchCode=cdkey.getMap().get(order.getCdkeyType()).getBatchCode();//校验码
            	//获取cdkey
            	String cdkey="nocdkey";
            	try {
                	cdkey=webserviceClient.getCdkey(order.getOrderId(), order.getAccount(), order.getPayType(), batchId, batchCode, order.getCount(), order.getIp(), order.getOrderId());
				} catch (Exception e) {
					e.printStackTrace();
				}
            	Winning winning=new Winning();
            	winning.setAccount(termDetailed.getUserName());
            	winning.setCdkey(cdkey);
            	winning.setIp(termDetailed.getIp());
            	winning.setItemName(item.getName());
            	winning.setOrderId(termDetailed.getOrderId());
            	winning.setTermId(termId);
            	termWinningMapper.addTermWinning(winning);//添加到获奖表中去。
        	}else{  //已经获过奖励了,就排除这个账号
        		List<TermDetailed> termDetaileds=getNotWinningTermDetailed(termDetailed.getUserName(),termId);
        		if(termDetaileds!=null && termDetaileds.size()>0){
        			Random randAgain=new Random();
                	int numAgain = randAgain.nextInt(termDetaileds.size());
                	TermDetailed termDetailed2=termDetaileds.get(numAgain);
            		
                	//添加到获奖表中去
                	
                	//获取批次号ID和校验码
                	int batchId=cdkey.getMap().get(order.getCdkeyType()).getBatchId();//批次号ID
                	String batchCode=cdkey.getMap().get(order.getCdkeyType()).getBatchCode();//校验码
                	//获取cdkey
                	String cdkey="nocdkey";
                	try {
                    	cdkey=webserviceClient.getCdkey(order.getOrderId(), order.getAccount(), order.getPayType(), batchId, batchCode, order.getCount(), order.getIp(), order.getOrderId());
					} catch (Exception e) {
						e.printStackTrace();
					}
                	Winning winning=new Winning();
                	winning.setAccount(termDetailed2.getUserName());
                	winning.setCdkey(cdkey);
                	winning.setIp(termDetailed2.getIp());
                	winning.setItemName(item.getName());
                	winning.setOrderId(termDetailed2.getOrderId());
                	winning.setTermId(termId);
                	termWinningMapper.addTermWinning(winning);//添加到获奖表中去。
        		}	
        	}
        	Term newTerm =new Term();
        	newTerm.setId(null);
        	newTerm.setAuctionServings(0);
        	newTerm.setItemId(item.getId());
        	newTerm.setNumber(term.getNumber()+1);
        	newTerm.setPrice(item.getPrice());
        	newTerm.setServings(item.getPrice());
        	newTerm.setSt(term.getSt());
        	int insertCount=termMapper.insertTerm(newTerm);//添加新的一期
        	if(insertCount!=1){
        		throw new RuntimeException("添加新的一期失败。"+item.getId()+","+newTerm.getId());
        	}
        	addTermDetailed(order,newTerm,addOver);
		}
	}
	
	
	
	
	/**
	 * 增加对应的份数
	 * @param order
	 * @param term
	 * @param addOver
	 */
	@Transactional
	public void addTermDetailed(Order order,Term term,int addOver){
		int termId=term.getId();
		int servings=term.getServings();
		TermDetailed termDetailed=new TermDetailed();
		termDetailed.setCount(1);
		termDetailed.setTermId(termId);
		termDetailed.setItemId(term.getItemId());
		int add=addOver;
		int insertRows=0;
		for(int i=1;i<=servings;i++){
			termDetailed.setKeyNumber(i);
			if(addOver>0){
				termDetailed.setUserName(order.getAccount());
				termDetailed.setIp(order.getIp());
				termDetailed.setOrderId(order.getOrderId());
				addOver--;
				if(termDetailedMapper.insertTermDetailed(termDetailed)>0){
					insertRows++;
				}
			}else{
				termDetailed.setUserName(null);
				termDetailed.setIp(null);
				termDetailed.setOrderId(null);
				termDetailedMapper.insertTermDetailed(termDetailed);//插入到表t_gw2_mp_term_detailed
			}
		}
		if(insertRows>0){
			int up=updateTermById(insertRows,termId);
			if(up!=1){
				throw new RuntimeException("修改秒杀数量返回结果不为1。"+termId);
			}
			//插入到历史表中
			addTermHistory(termId,order,insertRows);
			//判断秒杀数量是否超了总的份,如果超过了就要增加新的期数。
			checkTerm(termId,order,add-insertRows);
		}
		
	}
	
	/**
	 * 插入到历史记录表中
	 * @param termId
	 * @param order
	 * @param keyCount
	 */
	@Transactional
	public void addTermHistory(Integer termId,Order order,Integer keyCount){
		Item item=itemMapper.getItemById(termMapper.getTermById(termId).getItemId());//获取商品的信息
		TermHistory termHistory=new TermHistory();
		termHistory.setAccount(order.getAccount());
		termHistory.setCount(keyCount);
		termHistory.setItemName(item.getName());
		termHistory.setOrderDate(order.getCreateDate());
		termHistory.setOrderId(order.getOrderId());
		termHistory.setPrice(item.getPrice());
		termHistory.setTermDate(order.getLastUpdateTime());
		termHistory.setTermId(termId);
		termHistoryMapper.addTermHistory(termHistory);
	}
	
	
	/**
	 * 修改订单的状态和支付类型
	 * @param orderId
	 * @param payType
	 */
	@Transactional
	public void updateOrder(String orderId,int payType,Integer status){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("payType", payType);
		map.put("status", status);
		map.put("time", new Date());
		map.put("orderId", orderId);
		orderMapper.updateOrder(map);
	}
	
	/**
	 * 根据秒拍的数量来
	 * 修改t_gw2_mp_term_detailed表中对应的数据
	 * @param order 订单对象
	 * @return
	 */
	@Transactional
	public int updateTermDetailed(Order order){
		TermDetailed termDetailed=new TermDetailed();
		termDetailed.setCount(1);
		termDetailed.setIp(order.getIp());
		termDetailed.setLimit(order.getCount());
		termDetailed.setOrderId(order.getOrderId());
		termDetailed.setUserName(order.getAccount());
		termDetailed.setTermId(order.getTermId());
		return termDetailedMapper.updateTermDetailedByLimit(termDetailed);
	}
	
	/**
	 * 修改表t_gw2_mp_term中的秒杀的数量
	 * @param limit
	 * @param termId
	 * @return
	 */
	@Transactional
	public int updateTermById(int limit,int termId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("termId", termId);
		return termMapper.updateTermById(map);
	}
	
	/**
	 * 选出一位中奖用户
	 * @param termId
	 * @param keyNumber
	 * @return
	 */
	public TermDetailed getLuckyKey(int termId,int keyNumber){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("termId", termId);
		map.put("keyNumber", keyNumber);
		return termDetailedMapper.getTermDetailedByTermIdAndKey(map);
	}
	
	/**
	 * 查询某一个用户是否已经某种类型的CDKEY
	 * @param account
	 * @param itemName
	 * @return
	 */
	public boolean isWinning(String account,String itemName){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("account", account);
		map.put("itemName", itemName);
		Integer count=termWinningMapper.getIsWinning(map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询出未获奖的秒拍明细
	 * @param account 被排除的账号
	 * @return
	 */
	public List<TermDetailed> getNotWinningTermDetailed(String account,Integer termId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("account", account);
		map.put("termId", termId);
		return termDetailedMapper.getNotWinningTermDetailed(map);
	}
}