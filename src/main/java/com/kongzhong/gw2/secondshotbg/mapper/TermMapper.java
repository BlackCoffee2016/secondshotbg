package com.kongzhong.gw2.secondshotbg.mapper;

import java.util.Map;

import com.kongzhong.gw2.secondshotbg.domain.Term;

public interface TermMapper {
	
	Term getTermById(Integer id);
	
	Term getTermByItemId(Integer itemId);
	
	int updateTermById(Map<String, Object> map);
	
	int insertTerm(Term term);//添加新的一期
}
