package com.example.savenamecookie.controller;

import com.example.savenamecookie.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class UserController {
    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    @RequestMapping("/login")
            public String Index(@CookieValue(value = "setUser", defaultValue = "") String setUser, Model model) {
        Cookie cookie = new Cookie("setUser", setUser);
        model.addAttribute("cookieValue", cookie);
        return "/login";
    }


    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("user") User user, Model model
            , @CookieValue(value = "setUser", defaultValue = "") String setUser,
                          HttpServletRequest request, HttpServletResponse response) {
        // implement business logic
        if (user.getEmail().equals("dat@gmail.com") && user.getPassWord().equals("123456")) {
            if (user.getEmail() != null) {
                setUser = user.getEmail();
            }
            // create cookie and set it im response
            Cookie cookie = new Cookie("setUser", setUser);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);


            // get all cookie
            Cookie[] cookies = request.getCookies();
            // iterate each cookie
            for (Cookie ck : cookies) {
                // display only the cookie with the name 'setUser'
                if (!ck.getName().equals("setUser")) {
                    ck.setValue("");
                }
                model.addAttribute("cookieValue", ck);
            }
            model.addAttribute("message", "login Success");
        } else {
            user.setEmail("");
            Cookie cookie = new Cookie("setUser", setUser);
            model.addAttribute("cookieValue", cookie);
            model.addAttribute("message", "Login Failed, Try again!");
        }
        return "login";
    }
}
