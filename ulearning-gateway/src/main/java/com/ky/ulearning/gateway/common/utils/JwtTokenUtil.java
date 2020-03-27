package com.ky.ulearning.gateway.common.utils;

import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.DefaultJwtSigner;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * token工具类
 *
 * @author luyuhao
 * @date 19/12/09 03:19
 */
@Component
public class JwtTokenUtil {

    private static final long serialVersionUID = -3301605591108950415L;
    private Clock clock = DefaultClock.INSTANCE;

    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Integer getLoginTypeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("loginType", Integer.class);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("id", String.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(gatewayConfigParameters.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException eje) {
            return eje.getClaims();
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        claims.put("id", jwtAccount.getId());
        claims.put("loginType", jwtAccount.getLoginType());
        return doGenerateToken(claims, jwtAccount.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, gatewayConfigParameters.getSecret())
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, gatewayConfigParameters.getSecret())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        final Date created = getIssuedAtDateFromToken(token);
//        final Date expiration = getExpirationDateFromToken(token);
//        如果token存在，且token创建日期 > 最后修改信息的日期 则代表token有效
        return (!isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, jwtAccount.getPwdUpdateTime())
        );
    }

    /**
     * 防篡改校验
     */
    public Boolean tamperProof(String token) {
        try {
            //获取header.payload
            String headerAndPayload = token.substring(0, token.lastIndexOf("."));
            String signature = token.substring(token.lastIndexOf(".") + 1);
            //获取key的字节流
            byte[] keyBytes = TextCodec.BASE64.decode(gatewayConfigParameters.getSecret());
            //生成key
            SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
            //定义算法和key
            JwtSigner signer = new DefaultJwtSigner(SignatureAlgorithm.HS256, key);
            //对jwt进行重新加密
            String base64UrlSignature = signer.sign(headerAndPayload);
            return signature.equals(base64UrlSignature);
        } catch (Exception e) {
            return false;
        }
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + gatewayConfigParameters.getExpiration());
    }

}
