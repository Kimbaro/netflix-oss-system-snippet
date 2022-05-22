package com.kimbaro.eureka.post.controller;

import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@ResponseBody
public class PostController {
    @RequestMapping(method = RequestMethod.GET, value = "/post/{id}")
    public String getUser(@PathVariable Long id) {
        Logger.getLogger("TEST").log(Level.INFO, "post id :: " + id);
        return id + "";
    }
}
