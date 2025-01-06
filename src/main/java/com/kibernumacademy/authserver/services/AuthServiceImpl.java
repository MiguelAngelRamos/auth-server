package com.kibernumacademy.authserver.services;

import org.springframework.stereotype.Service;

import com.kibernumacademy.authserver.dtos.TokenDto;
import com.kibernumacademy.authserver.dtos.UserDto;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
  
  @Override
  public TokenDto login(UserDto user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'login'");
  }

  @Override
  public TokenDto validateToken(TokenDto token) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
  }
  
}
