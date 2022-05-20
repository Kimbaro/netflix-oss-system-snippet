package com.kimbaro.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public String response_test() {
        return "테스트 응답";
    }
}
