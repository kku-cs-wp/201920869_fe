package kr.ac.kku.cs.wp.wsd.support.spring.aop;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import kr.ac.kku.cs.wp.wsd.tools.message.MessageException;

/**
 * NullCheckAspect
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Aspect
@Component
public class NullCheckAspect {

	private static final Logger logger = LogManager.getLogger(NullCheckAspect.class);

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void callMethods() {
	}

	@AfterReturning(pointcut = "callMethods()", returning = "reuslt")
	public void nullCheckAfterReturning(JoinPoint joinPoint, Object result) throws NoSuchMethodException {

		Method method = getMethodFromJoinPoint(joinPoint);

		logger.debug(method.getReturnType());

		if (!method.getReturnType().equals(Void.TYPE)) {
			String message = "";
			logger.trace("in nullcheck");
			if (result == null) {
				logger.trace("in nullcheck in null");
				if (joinPoint.getSignature().getName().equals("getUserById")) {
					message = "User not found : " + joinPoint.getArgs()[0];
					logger.debug("message : {}", message);
				}

				throw new MessageException(message);
			}
		}
	}

	/**
	 * joinpoint Method 객체 반환
	 * 
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getMethodFromJoinPoint(JoinPoint joinPoint) throws NoSuchMethodException {
		String methodName = joinPoint.getSignature().getName();
		Class<?> targetClass = joinPoint.getTarget().getClass();

		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

		return targetClass.getMethod(methodName, parameterTypes);
	}

}