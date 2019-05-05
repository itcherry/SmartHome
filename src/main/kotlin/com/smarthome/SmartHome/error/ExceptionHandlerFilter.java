package com.smarthome.SmartHome.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.SmartHome.controller.model.ResponseBody;
import com.smarthome.SmartHome.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            ResponseBody<CustomError> responseBody = new ResponseBody<>(
                    ResponseBody.ERROR,
                    new CustomError(e.getCode(), e.getMessage()), null
            );
            response.getWriter().write(convertObjectToJson(responseBody));
            response.setContentType("application/json");
            response.setStatus(HttpStatus.OK.value());
            logger.error(e.getCode() + " : " + e.getMessage());
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Override
    public void destroy() {

    }
}
