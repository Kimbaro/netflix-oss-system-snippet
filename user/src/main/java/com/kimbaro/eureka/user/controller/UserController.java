package com.kimbaro.eureka.user.controller;

import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@ResponseBody
public class UserController {
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    public String getUser(@PathVariable Long id) {
        Logger.getLogger("TEST").log(Level.INFO, "user id :: " + id);
        return id + "";
    }
}
