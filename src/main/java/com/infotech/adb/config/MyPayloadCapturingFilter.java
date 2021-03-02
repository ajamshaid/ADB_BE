//package com.infotech.adb.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class MyPayloadCapturingFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
////
////
////        request.getContextPath()
////        filterChain.doFilter(request, response);
//        String requestBody = new String(requestWrapper.getContentAsByteArray());
//        String responseBody = new String(responseWrapper.getContentAsByteArray());
//        System.out.println("requestBody" + requestBody);
//        System.out.println("requestBody" + responseBody);
//
//        filterChain.doFilter(request,response);
//    }
//}
