package com.hanbit.there.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
	
	@AfterReturning(value="allServiceMethod()", returning="retVal")
	public void logServiceReturn(Object retVal) {
		logger.debug("method returned " + retVal);
	}
	
	@AfterThrowing(value="allServiceMethod()", throwing="thrown")
	public void logServiceThrow(Throwable thrown) {
		logger.debug("method threw " + thrown);
	}
	
	@After(value="allServiceMethod()", argNames="joinPoint")
	public void logService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + " has called (after)");
	}
	
	@Around("allServiceMethod()") // 리턴 값 있음.
	public Object logServiceAround(ProceedingJoinPoint pjp) throws Throwable {
		// Before
		logger.debug("Before (around)");
		
		Object retVal = null;		
		try {
			retVal = pjp.proceed(); // 메소드 실행.
			// AfterReturning
			logger.debug("AfterReturning (around)");
		} catch (Throwable t) {
			// AfterThrowing
			logger.debug("AfterThrowing (around)");
			throw t;
		}
		
		return retVal;
	}
}
