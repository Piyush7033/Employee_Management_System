package EmployeeManagementSystem.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {
    private final String SECRET_KEY="mysecretkeymysecretkeymysecretkeymysecretkey";
    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
    }
    public String extractUsername(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
}
