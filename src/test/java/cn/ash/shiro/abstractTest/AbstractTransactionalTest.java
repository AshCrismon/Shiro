package cn.ash.shiro.abstractTest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class AbstractTransactionalTest {

}
