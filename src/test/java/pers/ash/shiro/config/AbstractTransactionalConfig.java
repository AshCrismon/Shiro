package pers.ash.shiro.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml","classpath:shiro/spring-shiro-web.xml","classpath:activiti/activiti-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class AbstractTransactionalConfig{

}
