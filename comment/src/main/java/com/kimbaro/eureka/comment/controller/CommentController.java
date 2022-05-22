package com.kimbaro.eureka.comment.controller;

import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@ResponseBody
public class CommentController {
    @RequestMapping(method = RequestMethod.GET, value = "/comment/{id}")
    public String getUser(@PathVariable Long id) {
        Logger.getLogger("TEST").log(Level.INFO, "comment id :: " + id);
        return id + "";
    }
}
