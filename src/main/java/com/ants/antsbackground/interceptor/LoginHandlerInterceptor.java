package com.ants.antsbackground.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("==================");
        HttpSession session = request.getSession();
        String studentId =  (String)session.getAttribute("9999");
        //未登录拦截
        if (studentId == null || "".equals(studentId)){
            request.setAttribute("msg","您未登录，请先登录!");
//            request.getRequestDispatcher("/#/login.html").forward(request,response);
//            return false;
            return true;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
