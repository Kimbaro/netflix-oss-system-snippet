package com.kimbaro.eureka.user;

import com.kimbaro.eureka.user.entity.UserCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApplicationTests {

    @LocalServerPort
    int serverPort;

    @Test
    void 로그인테스트수행() {
        //가상 클라이언트
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8883")
                .build();
//
//        //기능수행
//        //유저생성
//        webClient.put().uri("/user/signup")
//                .bodyValue(Mono.just(new UserCredentials("new@example.com", "1234")))
//                .block();
//
//        webClient.post()
//                .uri("/user/login")
//                .bodyValue(new UserCredentials("new@example.com", "1234"))
//                .exchangeToMono()
//                .block();


    }


    @Test
    void contextLoads() {
    }

}
