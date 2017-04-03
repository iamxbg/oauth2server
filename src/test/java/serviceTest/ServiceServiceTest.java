//package serviceTest;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import oauthServer.config.RootConfig;
//import oauthServer.model.Service;
//import oauthServer.service.ServiceService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class})
//public class ServiceServiceTest {
//
//	@Autowired
//	private ServiceService sService;
//	
//	@Test
//	public void testfindAll(){
//		List<Service> sList=sService.findAll();
//		
//		System.out.println("service-size:"+sList.size());
//		
//		for(Service s:sList){
//			System.out.println("service:"+s.getName());
//		}
//	}
//}
