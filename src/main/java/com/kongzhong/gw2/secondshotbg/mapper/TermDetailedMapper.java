package com.kongzhong.gw2.secondshotbg.mapper;

import java.util.List;
import java.util.Map;

import com.kongzhong.gw2.secondshotbg.domain.TermDetailed;

public interface TermDetailedMapper {
	
	int updateTermDetailedByLimit(TermDetailed termDetailed);//更新表t_gw2_mp_term_detailed通过limit
	
	int getCountByTermId(Integer termId);//通过termId来查询出有多条记录
	
	int insertTermDetailed(TermDetailed termDetailed);//向表中t_gw2_mp_term_detailed插入数据
	
	TermDetailed getTermDetailedByTermIdAndKey(Map<String, Object> map);// 选出一个中奖的用户
	
	List<TermDetailed> getNotWinningTermDetailed(Map<String,Object> map);//查询已经排除获奖用户的秒拍明细
}
