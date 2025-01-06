package com.kibernumacademy.authserver.services;

import com.kibernumacademy.authserver.dtos.TokenDto;
import com.kibernumacademy.authserver.dtos.UserDto;

public interface AuthService {
  TokenDto login(UserDto user);
  TokenDto validateToken(TokenDto token);
}
