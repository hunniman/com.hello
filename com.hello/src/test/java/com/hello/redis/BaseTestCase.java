package com.hello.redis;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })  
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{

}
