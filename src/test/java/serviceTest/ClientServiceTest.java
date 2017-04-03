//package serviceTest;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//import oauthServer.model.Client;
//import oauthServer.service.ClientService;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class})
//public class ClientServiceTest {
//
//
//	@Autowired
//	private ClientService cService;
//	
//	
//	@Test
//	public void testGetClientList(){
//		
//		List<Client> cList=cService.findAll();
//		
//		System.out.println("client-list-size:"+cList.size());
//	
//		for(Client c:cList){
//			System.out.println("Client:"+c.getName());
//		}
//	}
//}
