package com.kimbaro.eureka.user.utils.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

//AuthenticationWebFilter
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager jwtAuthenticationManager,
            ServerAuthenticationConverter jwtServerAuthenticationConverter
    ) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(jwtServerAuthenticationConverter);

        /*
        * /user -> 토큰이 없다면 401에러를 반환한다.
        * /user/login -> 토큰을 반환한다. 만약 jwt토큰이 없는 경우 생성하지만 계정이없는경우 no content를 반환한다.
        * /user/sighup -> 신규 계정을 생성하고 토큰을 생성하고 반환한다.
        * */
        return http.authorizeExchange()
                .pathMatchers("/user/signup")
                .permitAll()
                .pathMatchers("/user/login")
                .permitAll()
                .pathMatchers("/user")
                .authenticated()
                .and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .logout()
                .disable()
                .build();


//        return http.authorizeExchange()
//                .pathMatchers("/**")
//                .permitAll()
//                .and()
//                .httpBasic()
//                .disable()
//                .csrf()
//                .disable()
//                .formLogin()
//                .disable()
//                .logout()
//                .disable()
//                .build();
    }
}
