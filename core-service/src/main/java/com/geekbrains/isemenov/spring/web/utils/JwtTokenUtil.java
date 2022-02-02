package com.geekbrains.isemenov.spring.web.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    //Для генерации токена нужен к-л ключ, который по хорошему должны знать только мы.
    //Нужно определить сколько времени будет жить токен
    @Value("${jwt.secret}") // данный формат записи означает, что мы это достаем из какого-то property-файла
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            rolesList.add(authority);
        }
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims :: getSubject);
    }

    public List<String> getRoles(String token){
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        //вытаскиваем из токена какую-то информацию. В параметры подаем токен, и некую функцию, которая из Claims вытащит
        //какой-то интересующий нас кусок данных
        Claims claims = getAllClaimsFromToken(token); // все Claims из токена получили (проверили токен, вытащили тело данных)
        return claimsResolver.apply(claims); // вытаскиваем кусок данных с помощью функции, которая нам пришла.
    }

    private Claims getAllClaimsFromToken(String token){
        // когда к нам приходит токен, мы можем достать из него все полезные данные:
        return Jwts.parser() // создаем парсер из подключенной библиотеки
            .setSigningKey(secret) // подшиваем к парсеру secret key чтобы он мог по header и payload сгенерировать подпись и сравнить подписи
            .parseClaimsJws(token) // Если подпись корректная и время жизни позволяет - парсим токен, и
            .getBody(); // вытаскиваем оттуда claims - список ключей/значений
    }
}
