package skeletone.api.sample;

import org.springframework.stereotype.Repository;

import skeletone.base.dao.AbstractDAO;

@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO {
private final String NAMESPACE="skeletone.SampleMapper.";
	
	public Object getNow(){
		return selectOne(NAMESPACE+"getNow");
	}
	
	public boolean insert1(){
		return insertB(NAMESPACE+"insert1",null);
	}
	
	public boolean insert2(){
		return insertB(NAMESPACE+"insert2",null);
	}
}
