package com.nat20.ticketguru.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
    
    @GetMapping
    public String getMethodName() {
        return "index";
    }
    

}
