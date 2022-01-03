package com.reos79.rmm.web.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reos79.rmm.security.CustomUsernamePasswordAuthenticationToken;
import com.reos79.rmm.utils.JwtTokenUtil;

import io.jsonwebtoken.Claims;

/**
 * Authorization filter
 * @author reos79
 *
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	/**
	 * Constant
	 */
	private static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	/**
	 * Date formatter pattern Constant
	 */
	private static final DateFormat dateFormatter = new SimpleDateFormat(ISO_DATE_TIME_FORMAT);
	/**
	 * Constant
	 */
	public static final String PREFIX = "Bearer ";
	/**
	 * Constant
	 */
	private static final String HEADER = "Authorization";
	
	/**
	 * The token utilitrian
	 */
	private JwtTokenUtil jwtTokenUtil;
	
	/**
     * {@inheritDoc}
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());		
			jwtTokenUtil = ctx.getBean(JwtTokenUtil.class);
			if (checkJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get(JwtTokenUtil.AUTHORITIES) != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			String jsonResponse = getForbiddenResponse(request.getServletPath());
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonResponse);
			out.flush();
		}
	}	

	/**
	 * Authentication method in Spring flow
	 * @param claims The token claims 
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>)claims.get(JwtTokenUtil.AUTHORITIES);
		Integer customerId = claims.get(JwtTokenUtil.CUSTOMER, Integer.class);

		CustomUsernamePasswordAuthenticationToken auth = new CustomUsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		auth.setCustomerId(customerId);
		
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
	
	/**
	 * Validates the auhorization token in the http request
	 * @param request The http request
	 * @return The token claims
	 */
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return jwtTokenUtil.validateJwtToken(jwtToken);
	}

	/**
	 * Check if the http request contains the token
	 * @param request The http request
	 * @param res The http response
	 * @return True if the http request contains the token, false otherwise
	 */
	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX));
	}
	
	/**
	 * Generate the error response
	 * @param path The path
	 * @return The json error response
	 */
	private String getForbiddenResponse(String path) {
		StringBuilder response = new StringBuilder();
		response
			.append('{')
			.append('\n')
			.append("    \"timestamp\": \"")
			.append(dateFormatter.format(new Date()))
			.append("\",\n")
			.append("    \"status\": 403,\n")			
			.append("    \"error\": \"Forbidden\",\n")
			.append("    \"path\": \"")
			.append(path)
			.append('"')
			.append('\n')
			.append('}');
		return response.toString();
	}
}
