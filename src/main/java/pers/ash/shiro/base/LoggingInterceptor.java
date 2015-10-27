package pers.ash.shiro.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingInterceptor {

	private static Logger logger;

	@Around("execution(public * pers.ash.shiro.service..*.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		long begin = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long end = System.currentTimeMillis();
		logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass()
				.getName());
		logger.info("==============>logging:[ executing method:" + //
				joinPoint.getSignature().getName() + //
				", total time:" + (end - begin) + "ms ]");
		return result;
	}
	
}
