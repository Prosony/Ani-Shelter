package services;
/**
 * @author Prosony
 * @since 0.2.4
 */
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import com.google.gson.JsonObject;
import io.jsonwebtoken.*;
import memcache.JsonWebTokenCache;
import test.TestLog;

import java.util.Date;

public class JWTService {
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();


    public String createJWT(HttpServletRequest request) {

        byte[] secretStringBytes = DatatypeConverter.parseBase64Binary("supersecretstringmotherfucker"); //We will sign our JWT with our ApiKey secret

        String dataNow = new Date().toString();
        String ipHost = request.getRemoteHost();

        JsonObject object = new JsonObject();
        object.addProperty("data", dataNow);
        object.addProperty("ipHost", ipHost);
        Key tokenKey = new SecretKeySpec(secretStringBytes, String.valueOf(object));

        String compactJws = Jwts.builder()
                .setSubject(String.valueOf(object))
                .signWith(SignatureAlgorithm.HS256,tokenKey)
                .compact();

        testLog.sendToConsoleMessage("#TEST [class JWTService] JWT builder: "+compactJws);
        //Builds the JWT and serializes it to a compact, URL-safe string
        return compactJws;

    }
    public void parseJWT(String tokenJws) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("supersecretstringmotherfucker"))
                .parseClaimsJws(tokenJws).getBody();
        testLog.sendToConsoleMessage("#TEST [class JWTService] Subject: " + claims.getSubject());
    }
}
