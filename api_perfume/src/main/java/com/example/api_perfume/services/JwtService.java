package com.example.api_perfume.services;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.repository.PerfumeRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private PerfumeRepository perfumeRepo;
    
    
    public String generarJwt(Perfume perfume) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setSubject(String.valueOf(perfume.getId()))
            .claim("Numero ID", perfume.getId())
            /* .claim("role", "admin")*/
            .setIssuedAt(new Date(0))
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    
    }
    
    public Perfume comprobarToken(String token){
        
        if(token.startsWith("Bearer ")){
            token = token.replace("Bearer ", "");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String perfumeId = claims.getSubject();
            Perfume perfume = perfumeRepo.findById((long) Integer.parseInt(perfumeId)).orElse(null);
            if(perfume != null){
                return perfume;
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Perfume no encontrado");
            }
            

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token invalido");
        }
    }






}    



   
    
 
