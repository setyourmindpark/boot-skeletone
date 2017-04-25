package skeletone.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import skeletone.base.pojo.PaginationInfo;

public class AbstractDAO {

	private static final Logger log = LoggerFactory.getLogger(AbstractDAO.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()){
			log.debug("\t QueryId  \t:  " + queryId);
		}
	}
	
	
	/**
	 * @param queryId
	 * @param params
	 * @return
	 */
	public boolean insertB(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params) == 1 ? true:false;
	}
	
	/**
	 * @param queryId
	 * @param params
	 * @return
	 */
	public boolean updateB(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params) > 0 ? true:false;
	}
	
	/**
	 * @param queryId
	 * @param params
	 * @return
	 */
	public boolean deleteB(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params) > 0 ? true:false;
	}
	
	public Object insert(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId){
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId,params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectPagingList(String queryId, Object params){
	    printQueryId(queryId);
	    
	    Map<String,Object> map = (Map<String,Object>)params;
	    PaginationInfo paginationInfo = null;
	    
	    if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
	        map.put("currentPageNo","1");
	     
	    paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
	    if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
	        paginationInfo.setRecordCountPerPage(10);
	    }
	    else{
	        paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
	    }
	    paginationInfo.setPageSize(10);
	    
	    int start = paginationInfo.getFirstRecordIndex();
	    /* ORACLE */
//	    int end = start + paginationInfo.getRecordCountPerPage();
//	    map.put("START",start+1);
	    /* MARIA */
	    int end = paginationInfo.getRecordCountPerPage();
	    map.put("START",start);
	    map.put("END",end);
	    
	    params = map;
	     
	    Map<String,Object> returnMap = new HashMap<String,Object>();

	    List<Map<String,Object>> list = sqlSession.selectList(queryId,params);

	    if(list.size() == 0){
	        map = new HashMap<String,Object>();
	        map.put("totalCount",0);  
	        list.add(map);
	         
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(0);
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    else{
	        if(paginationInfo != null){
//	            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));
	            paginationInfo.setTotalRecordCount(Integer.parseInt(map.get("totalCount").toString()));
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    returnMap.put("result", list);
	    return returnMap;
	}

}