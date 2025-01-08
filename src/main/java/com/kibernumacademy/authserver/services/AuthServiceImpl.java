package com.kibernumacademy.authserver.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kibernumacademy.authserver.dtos.TokenDto;
import com.kibernumacademy.authserver.dtos.UserDto;
import com.kibernumacademy.authserver.helpers.JwtHelper;
import com.kibernumacademy.authserver.model.User;
import com.kibernumacademy.authserver.repositories.UserRepository;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
  
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private static final String USER_EXCEPTION_MESSAGE = "Error to auth user";
  private final JwtHelper jwtHelper;

  @Override
  public TokenDto login(UserDto user) {
    final User userFromDB = userRepository.findByUsername(user.getUsername())
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE));

    validPassword(user, userFromDB);
    return TokenDto.builder().accessToken(jwtHelper.createToken(userFromDB)).build();
  }

  @Override
  public TokenDto validateToken(TokenDto token) {
   if(!jwtHelper.validateToken(token.getAccessToken())) {
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE);
   }
   return TokenDto.builder().accessToken(token.getAccessToken()).build();
  }

  private void validPassword(UserDto userDto, User user) {
    if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE);
    }
  }
  
}
