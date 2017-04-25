package skeletone.api.sample;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import skeletone.base.map.CommandMap;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

	@Resource(name="sampleService")
	private SampleService sampleService;
	
	@RequestMapping(value="/hello", method = RequestMethod.POST)
	public ResponseEntity<?> hello(CommandMap commandMap) {
		Map<String, Object> datas = new HashMap<>();
		datas.put("datetime", sampleService.getNow());
        return ResponseEntity.ok(datas);
    }
	
	@RequestMapping(value="/apple", method = RequestMethod.GET)
	public ResponseEntity<?> apple() {
		Map<String, String> datas = new HashMap<>();
		datas.put("hello1", "spring1");
		datas.put("hello2", "spring2");
		datas.put("hello3", "spring3");
 
        return ResponseEntity.ok(datas);
    }
	 
}
