package skeletone.api.sample;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{

	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public Object getNow() {
		// TODO Auto-generated method stub
		sampleDAO.insert1();
		sampleDAO.insert2();
		return sampleDAO.getNow();
	}

}
