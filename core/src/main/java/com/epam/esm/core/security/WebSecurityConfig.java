package com.epam.esm.core.security;

import com.epam.esm.core.filter.JwtAuthenticationFilter;
import com.epam.esm.core.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.util.Collections;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessToken.lifetime}")
    private long accessTokenLifetime;

    @Value("${jwt.refreshToken.lifetime}")
    private long refreshTokenLifetime;

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter authNFilter = new JwtAuthenticationFilter(authenticationManagerBean(), secret, accessTokenLifetime, refreshTokenLifetime);
        authNFilter.setFilterProcessesUrl("/api/v1/users/login");
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/users/login/**", "/api/v1/users/signup/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/users/refresh-token/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/gift-certificates/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET).hasAuthority("USER")
                .and()
//                .authorizeRequests().anyRequest().hasAuthority("ADMIN")
//                .and()
                .addFilter(authNFilter)
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
//    }

    //    public static final String REALM_NAME = "MyRealm";
//    public static final String API_KEY_PARAM = "apikey";
//    public static final Pattern AUTHORIZATION_HEADER_PATTERN = Pattern.compile(
//            String.format("%s %s=\"(\\S+)\"", REALM_NAME, API_KEY_PARAM)
//    );
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//                .addFilterAfter(preAuthenticationFilter(), RequestHeaderAuthenticationFilter.class)
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
//                .and()
//                .csrf().disable();
//    }
//
//    @Bean
//    public RequestHeaderAuthenticationFilter preAuthenticationFilter() {
//        RequestHeaderAuthenticationFilter preAuthenticationFilter = new RequestHeaderAuthenticationFilter();
//        preAuthenticationFilter.setPrincipalRequestHeader("Authorization");
//        preAuthenticationFilter.setCredentialsRequestHeader("Authorization");
//        preAuthenticationFilter.setAuthenticationManager(authenticationManager());
//        preAuthenticationFilter.setExceptionIfHeaderMissing(false);
//
//        return preAuthenticationFilter;
//    }
//
//    @Override
//    protected AuthenticationManager authenticationManager() {
//        return new ProviderManager(Collections.singletonList(authenticationProvider()));
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
//        authenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
//        authenticationProvider.setThrowExceptionWhenTokenRejected(false);
//
//        return authenticationProvider;
//    }
//
//    @Bean
//    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
//        return new AuthorizationUserDetailsService();
//    }
//
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return new Http401AuthenticationEntryPoint(REALM_NAME);
//    }
}
