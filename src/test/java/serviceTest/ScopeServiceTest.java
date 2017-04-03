//package serviceTest;
//
//import java.util.List;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//import oauthServer.model.Scope;
//import oauthServer.model.Service;
//import oauthServer.service.ScopeService;
//import oauthServer.service.ServiceService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class})
//public class ScopeServiceTest {
//
//	@Autowired
//	private ScopeService sService;
//	@Autowired
//	private ServiceService serviceService;
//	
//	@Ignore
//	@Test
//	public void tetGetByServiceId(){
//		
//		List<Service> serviceList=serviceService.findAll();
//		
//		for(Service service:serviceList){
//			System.out.println("service:"+service.getName());
//			List<Scope> scpList=sService.getScopesBydServiceId(service.getId());
//			for(Scope scp:scpList){
//				System.out.println("\tscope:"+scp.getName());
//			}
//		}
//		
//	}
//	
//	@Test
//	public void testGetByClientId(){
//		List<Service> serviceList=serviceService.findAll();
//		
//		for(Service service:serviceList){
//			System.out.println("service:"+service.getName());
//			List<Scope> scpList=sService.getScopesByClientId(1);
//			for(Scope scp:scpList){
//				System.out.println("#1 :"+scp.getName());
//			}
//			List<Scope> scp2List=sService.getScopesByClientId(2);
//			
//			for(Scope s:scp2List){
//				System.out.println("2# :"+s.getName());
//			}
//		}
//	}
//}
