package com.xpjava.medicine.interceptors;

import com.xpjava.medicine.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 帅鹏
 * @create 2019-11-03 22:17
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userInfo = CookieUtil.getCookieValue(request, "userInfo", true);

        if(StringUtils.isBlank(userInfo)){
            response.sendRedirect("/medicine/user/login");
            return false;
        }

        return true;
    }
}
