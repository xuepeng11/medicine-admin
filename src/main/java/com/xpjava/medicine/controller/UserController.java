package com.xpjava.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xpjava.medicine.entity.User;
import com.xpjava.medicine.service.UserService;
import com.xpjava.medicine.util.CookieUtil;
import com.xpjava.medicine.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Helen
 * @since 2019-12-03
 */
@Controller
@RequestMapping("/medicine/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, "userInfo");
        CookieUtil.deleteCookie(request, response, "userName");
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, User user, ModelMap map, RedirectAttributes attributes) {
        map.put("userName", user.getUserName());
        String code = (String) request.getSession().getAttribute("SESSION_CODE");
        if (StringUtils.isBlank(code) || !code.equals(user.getUserCode())) {
            map.put("errorMsg", "验证码错误");
            return "login";
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName())
                .eq("user_password", user.getUserPassword())
                .eq("user_type", user.getRole());
        User userDb = userService.getOne(queryWrapper);
        if (userDb == null) {
            map.put("errorMsg", "用户或密码错误");
            return "login";
        }
        String token = null;

        String userName = user.getUserName();
        String role = user.getRole();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userName", userName);
        userMap.put("role", role);
        token = JwtUtil.encode("xpjava", userMap, "xpjava");
        if (StringUtils.isNotBlank(user.getRememberMe()) && user.getRememberMe().equals("on")) {
            CookieUtil.setCookie(request, response, "userInfo", token, 60 * 60 * 24 * 15, true);
            CookieUtil.setCookie(request, response, "userName", userName, 60 * 60 * 24 * 15, true);
        } else {
            CookieUtil.setCookie(request, response, "userInfo", token, -1, true);
            CookieUtil.setCookie(request, response, "userName", userName, -1, true);
        }
        attributes.addFlashAttribute("userName", user.getUserName());
        return "redirect:/medicine/user/home";
    }


    @GetMapping("/home")
    public String qwe(HttpServletRequest request) {
        String token = CookieUtil.getCookieValue(request, "userInfo", true);
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        if (StringUtils.isBlank(token)) {
            return "redirect:/medicine/user/login";
        }
        Map<String, Object> map = JwtUtil.decode(token, "xpjava", "xpjava");
        if (map == null) {
            return "redirect:/medicine/user/login";
        }
        String role = (String) map.get("role");
        map.put("userName", userName);
        if (role.equals("1")) {
            return "normal-home";
        } else {
            return "home";
        }
    }

}

