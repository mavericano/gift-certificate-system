package com.epam.esm.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.Role;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.ExceptionMessageHandler;
import com.epam.esm.core.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;
    final OrderController orderController;

    @GetMapping(params = {"page", "size"})
    public List<UserDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(name = "sortBy", required = false) String sortBy, @RequestParam(name = "sortType", required = false) String sortType) {
        return userService.getAllUsers(page, size, sortBy, sortType).stream().map(this::addLinksToUser).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return addLinksToUser(userService.getUserById(id));
    }

    @GetMapping(path = "/{id}/orders", params = {"page", "size"})
    public List<OrderDto> getOrdersForUserById(@PathVariable String id, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(name = "sortBy", required = false) String sortBy, @RequestParam(name = "sortType", required = false) String sortType) {
        return userService.getOrdersForUserById(id, page, size, sortBy, sortType).stream().map(orderController::addLinksToOrder).collect(Collectors.toList());
    }

    @GetMapping("/refresh-token")
    @SneakyThrows
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authZHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authZHeader != null && authZHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authZHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //TODO reconsider
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUsername(username).orElseThrow(null); //TODO fix

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) //ten minutes
                        .withIssuer(request.getRequestURI())
                        .withIssuedAt(new Date(System.currentTimeMillis()))
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (TokenExpiredException e) {
                Map<String, Object> errors = new LinkedHashMap<>();
                errors.put("httpStatus", HttpStatus.UNAUTHORIZED);
                errors.put("errorCode", 40101);
                errors.put("errorMessage", ExceptionMessageHandler.getMessage("authTokenExpiredExceptionMessage", LocaleContextHolder.getLocale()));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            } catch (Exception e) {
                Map<String, Object> errors = new LinkedHashMap<>();
                errors.put("httpStatus", HttpStatus.UNAUTHORIZED);
                errors.put("errorCode", 40102);
                errors.put("errorMessage", ExceptionMessageHandler.getMessage("authTokenInvalidExceptionMessage", LocaleContextHolder.getLocale()));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            //TODO refresh token is missing exception
            throw new RuntimeException("refresh token is missing exception");
        }
    }

    private UserDto addLinksToUser(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class)
                        .getUserById(String.valueOf(userDto.getId()))).withSelfRel());
        userDto.add(linkTo(methodOn(UserController.class)
                        .getOrdersForUserById(String.valueOf(userDto.getId()), 1, 5, "orderId", "asc")).withRel("orders"));
        return userDto;
    }
}
