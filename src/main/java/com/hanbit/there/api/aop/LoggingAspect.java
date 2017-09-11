package com.hanbit.there.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("execution(* com.hanbit.there.api..controller.*.*(..))")
	public void allControllerMethod() {
		
	}
	
	@Pointcut("execution(* com.hanbit.there.api..service.*.*(..))")
	public void allServiceMethod() {
		
	}
	
	@Before(value="allControllerMethod() || allServiceMethod()", argNames="joinPoint")
	public void logController(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + " has called (before)");
	}
	
	@AfterReturning("allServiceMethod()")
	public void logServiceReturn() {
		logger.debug("method returned");
	}
	
	@AfterThrowing("allServiceMethod()")
	public void logServiceThrow() {
		logger.debug("method threw");
	}
	
	@After(value="allServiceMethod()", argNames="joinPoint")
	public void logService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + " has called (after)");
	}
}
