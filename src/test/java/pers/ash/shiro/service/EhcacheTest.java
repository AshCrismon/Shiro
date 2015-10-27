package pers.ash.shiro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml"})
public class EhcacheTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testEhcache(){
		for(int i = 0; i < 10; i++){
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					userService.findByUsername("admin");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}, "userService thread-" + (i + 1) + " start");
			t.start();
			System.out.println("--------------------->" + t.getName());
		}
		try {
			Thread.sleep(10000);	//由于事务传递性，等待子线程执行完主线程再退出
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
