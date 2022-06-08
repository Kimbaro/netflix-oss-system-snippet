package com.kimbaro.eureka.user.utils.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//ServerAuthenticationConverter
//요청 헤더에 포함된 JWT 값에서 이름과 패스워드를 Authentication.class 객체로 변환한다.
@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    //요청 헤더의 쿠키정보에 담긴 X-Auth 키값이 존재하는지 확인.
    //존재한다면 ID, Password를 추출
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(it -> Mono.justOrEmpty(it.getRequest().getCookies().get("X-Auth")))
                .filter(o -> !o.isEmpty())
                .map(httpCookies -> httpCookies.get(0).getValue())
                .map(s -> new UsernamePasswordAuthenticationToken(s, s));
    }


}
