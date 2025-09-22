package com.artgallery.tracker.service;

import com.artgallery.tracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")//app.prop
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long jwtExpiration;//1hr long

    public String extractEmail(String token){//extr email from jwttok
        return extractClaim(token, Claims::getSubject);//subject = email (subject is example of claim)
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){//extract custom claim
        final Claims claims = extractAllClaims(token);// get all claims
        return claimsResolver.apply(claims);// apply resolver - resolver is the pointer to the claim i care ab
    }

    //Generating token
    public String generateToken(UserDetails userDetails){// token without extra claims
        return generateToken(new HashMap<>(),userDetails);//hashmap for storing extra claims
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){// token with extra claims
        return buildToken(extraClaims,userDetails,jwtExpiration);
    }
    public Long getExpirationTime(){// return configured expiration time
        return jwtExpiration;
    }

    // build JWT
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)// add extra claims if any
                .setSubject(((User) userDetails).getEmail())// set email as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))// issue time
                .setExpiration(new Date(System.currentTimeMillis()+expiration))// expiry
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)// sign with HMAC key
                .compact();// compact string
    }

    public boolean isTokenValid(String token, UserDetails userDetails){// validate token
        final String email = extractEmail(token);// extract email
        return (email.equals(((User) userDetails).getEmail()) && !isTokenExpired(token));// check match + expiry
    }

    private boolean isTokenExpired(String token){// check expiry
        return extractExpiration(token).before(new Date());// expired if before now
    }

    private Date extractExpiration(String token){ //get expiration date
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){// parse all claims
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())// set key
                .build()
                .parseClaimsJws(token)// parse JWT
                .getBody();// return claims
    }

    private Key getSignInKey(){// generate signing key
        byte[] keyBites = Decoders.BASE64.decode(secretKey);// decode Base64 key
        return Keys.hmacShaKeyFor(keyBites);// return HMAC key
    }
}
