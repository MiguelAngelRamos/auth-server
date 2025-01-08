package com.kibernumacademy.authserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kibernumacademy.authserver.dtos.TokenDto;
import com.kibernumacademy.authserver.dtos.UserDto;
import com.kibernumacademy.authserver.services.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(authService.login(userDto));
  }

  @PostMapping("jwt")
  public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken) {
    return ResponseEntity.ok(authService.validateToken(TokenDto.builder().accessToken(accessToken).build()));
  }
}
