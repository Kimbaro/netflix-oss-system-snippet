package com.kimbaro.eureka.user.controller;

import com.kimbaro.eureka.user.config.JwtSigner;
import com.kimbaro.eureka.user.dto.User;
import com.kimbaro.eureka.user.entity.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
public class UserController {
    private static HashMap<String, UserCredentials> users = new HashMap<String, UserCredentials>();

    @Autowired
    public JwtSigner jwtSigner;

    @GetMapping
    public Mono<ResponseEntity<User>> getMyself() {
        String emailAddress = "email@example.com";// ultimately this will be obtained from the JWT
        return Mono.justOrEmpty(users.get(emailAddress))
                .map(it -> {
                    return ResponseEntity.ok(
                            User.builder().email(it.getEmail()).build()
                    );
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PutMapping("/signup")
    public Mono<ResponseEntity<Object>> signUp(@RequestBody UserCredentials user) {
        Logger.getLogger("TEST").log(Level.INFO, user.toString());
        users.put(user.getEmail(), user);
        return Mono.just(ResponseEntity.noContent().build());
    }

    //
    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody UserCredentials user) {
        Logger.getLogger("TEST").log(Level.INFO, user.toString());
        return Mono.justOrEmpty(users.get(user.getEmail()))
                .filter(it -> it.getPassword().equals(user.getPassword()))  //로그인성공시
                .map(o -> {
                    String jwt = this.jwtSigner.createJwt(o.getEmail());
                    ResponseCookie authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt)
                            .maxAge(3600)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build();
                    //jwt토큰을 포함하여 응답한다.
                    return ResponseEntity.noContent()
                            .header("Set-Cookie", authCookie.toString())
                            .build();
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
