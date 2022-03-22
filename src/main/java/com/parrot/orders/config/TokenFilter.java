package com.parrot.orders.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.parrot.orders.service.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenFilter implements Filter{

	@Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	log.debug("init token filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        boolean requireToken = req.getServletPath().contains("parrot");
        if(requireToken) {
	            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
	            boolean validToken = false;
	        if (token != null ) {
	        	validToken = jwtTokenProvider.validateToken(token, (HttpServletRequest) req);
	        }
	        if(Objects.nonNull(req.getAttribute("invalid")) || !validToken) {
	        	res.reset();
	            res.setContentType("application/json");
	            res.setStatus(403);
	            PrintWriter out = res.getWriter();
	            out.print("Token Expired");
	            out.flush();
	            return;
	        }
        }
  
      chain.doFilter(req, res);  
    }

    @Override
    public void destroy() {
    	log.debug("destroy token filter");
    }
}
