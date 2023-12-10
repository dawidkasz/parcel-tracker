package com.parcel.testservice;

import org.springframework.web.bind.annotation.RequestMapping;

public interface GreetingController {
    @RequestMapping("/")
    String greeting();
}