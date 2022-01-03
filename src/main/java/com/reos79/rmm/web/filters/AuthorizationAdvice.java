package com.reos79.rmm.web.filters;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.reos79.rmm.security.CustomUsernamePasswordAuthenticationToken;

/**
 * Secuirty authoriation advice
 * @author reos79
 *
 */
@Aspect
@Component
public class AuthorizationAdvice {

	/**
	 * Verifies that the URL customer id matches the token customer id
	 * @param joinPoint The joint point
	 * @param customerId The URL customer id
	 * @return The original method return object
	 * @throws Throwable When error ocurred.
	 */
	@Around("(execution(* com.reos79.rmm.web.controllers.DeviceController.*(..)) || execution(* com.reos79.rmm.web.controllers.ServiceController.*(..)) || execution(* com.reos79.rmm.web.controllers.ProformaInvoiceController.*(..))) && args(customerId,..)")
	public Object verifyExecutionPermissions(ProceedingJoinPoint joinPoint, Integer customerId) throws Throwable{
		try {
			CustomUsernamePasswordAuthenticationToken auth = (CustomUsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();		
			if(!auth.getCustomerId().equals(customerId)) {
				throw new RuntimeException("Invalid session.");
			}
			return joinPoint.proceed();
		}catch (ResponseStatusException e) {
			throw e;
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
}
