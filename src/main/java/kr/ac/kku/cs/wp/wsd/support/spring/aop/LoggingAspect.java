package kr.ac.kku.cs.wp.wsd.support.spring.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
	
	@Pointcut("within(@org.springframework.stereotype.Controller *)|| within(@org.springframework.stereotype.Service *) || within(@org.springframework.stereotype.Repository *)")
	public void callMethods() {}
	
	@Before("callMethods()")
	public void logBefore(JoinPoint joinPoint) {
		logger.trace("before {} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
		
	}
	
	@After("callMethods()")
	public void logAter(JoinPoint joinPoint) {
		logger.trace("after {} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
	}
	
	@AfterReturning(pointcut = "callMethods()", returning = "reuslt")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.trace("after returning {} result {} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), result);
	}
	
	@AfterThrowing(pointcut = "callMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.trace("after throwing {} {} Exception : {} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), error);
    }
	
}