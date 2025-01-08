package com.kibernumacademy.authserver.helpers;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kibernumacademy.authserver.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtHelper {

  @Value("${application.jwt.secret}")
  private String jwtSecret;

  public String createToken(User user) {
    final Date now = new Date(); // Fecha y hora Actual
    Date expirationDate = new Date(now.getTime() + 1000 * 60 * 60 * 24); // Crea una fecha de expiracion de 24hrs

    return Jwts
      .builder()
        .setSubject(user.getUsername()) // sub
        .setIssuedAt(now) 
        .setExpiration(expirationDate)
        .signWith(getSecretKey())
      .compact();
  }

  public boolean validateToken(String token) {
    try {
      final Date expirationDate  = getExpirationDate(token); // Fecha de expiración
      return expirationDate.after(new Date()); // true si aún no expira 
    } catch(SignatureException e) {
      log.error("Token inválido firma incorrecta", e);
      return false;
    } catch(Exception e) {
      // Otras excepciones 
      log.error("Token inválido", e);
      return false;
    }
  }

  /**
   * Este método obtiene la fecha de expiracion del un Token JWT
   * @param token Del token JWT se extraera de la fecha de expiracion
   * @return Retornamos la fecha de expiración ('exp') y sera un objeto Date
   */
  private Date getExpirationDate(String token) {
    // return getClaimFromToken(token, Claims::getExpiration); // idear la manera de obtener datos de los claims
    return getClaimFromToken(token, claims -> claims.getExpiration());
    // claims -> claims.getExpiration();
  }

  /*
   * Este método extrae un valor especifico (claim) del token JWT
   * @param <T> Tipo de valor a extraer
   * @param token de donde se traera el claim
   * @param claimsResolver Funcion que define cómo extraer el claim deseado
   */
  private<T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(parseToken(token));
  }

  private Claims parseToken(String token) {
    return Jwts.parserBuilder()
               .setSigningKey(getSecretKey())
               .build()
               .parseClaimsJws(token) // validar el token y extraer los claims
               .getBody(); // Devuelve el contenido del payload ( con todos sus claims)
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)); // esto convierte el secreto almacenado en base 64 en el github por medio del config server a un secreto con clave encrypta en HMAC
  }

}
