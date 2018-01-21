package com.github.wxiaoqi.security.auth.module.client.controller;

import com.github.wxiaoqi.security.auth.module.client.service.AuthService;
import com.github.wxiaoqi.security.auth.jwt.user.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.auth.jwt.user.JwtAuthenticationResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("jwt")
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "token", method = RequestMethod.POST)
    public ObjectRestResponse<String> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return new ObjectRestResponse<String>().data(token);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws Exception {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    public ResponseEntity<?> verify(String token) throws Exception {
        authService.validate(token);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> invalid(@RequestParam("token") String token) throws Exception {
        authService.invalid(token);
        return new ObjectRestResponse<Boolean>().data(true);
    }
}
