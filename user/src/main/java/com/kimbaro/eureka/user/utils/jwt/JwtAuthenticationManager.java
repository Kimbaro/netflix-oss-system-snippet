package com.kimbaro.eureka.user.utils.jwt;

import com.kimbaro.eureka.user.config.JwtSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

//ReactiveAuthenticationManager
//아이디 패스워드로 이뤄진 유효한 토큰인지 확인한다.
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private JwtSigner jwtSigner;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(authentication1 -> jwtSigner.validateJwt(authentication1.getCredentials().toString()))
                .onErrorResume(throwable -> Mono.empty())
                .map(claimsJws -> new UsernamePasswordAuthenticationToken(
                        claimsJws.getBody().getSubject(),
                        authentication.getCredentials().toString(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                ));

    }
}
