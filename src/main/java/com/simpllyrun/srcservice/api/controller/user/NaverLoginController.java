package com.simpllyrun.srcservice.api.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NaverLoginController {

    @GetMapping("/")
    public String naverLogin(){

        return "test/naverLogin";
    }
    @GetMapping("/OAuth/login")
    public String callback(){

        return "test/callback";
    }

}
