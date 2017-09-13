package com.hanbit.there.api.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void allControllerMethod() {
		
	}
	
	@Before("allControllerMethod()")
	public void logRequest(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.toShortString();
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String remoteAddr = request.getRemoteAddr(); // IP
		String uri = request.getRequestURI();
				
		logger.debug(methodName + " has requsted by " + remoteAddr + "(" + uri + ")"); 
	}
	
}
