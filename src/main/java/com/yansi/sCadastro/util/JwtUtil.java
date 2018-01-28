/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 *
 * @author yansi
 */
public class JwtUtil {
    
    private static String chave = "KOFRE_TELECOMUNICA";
    
    public static final String TOKEN_HEADER = "Authentication";
    
    public static String create(String subject){
        return  Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, chave)
                .compact();
    }
    
    public static Jws<Claims> decode(String token) {
        return Jwts.parser()
                .setSigningKey(chave)
                .parseClaimsJws(token);
    }
    
    
}
