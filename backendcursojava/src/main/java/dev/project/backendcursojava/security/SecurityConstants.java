package dev.project.backendcursojava.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import dev.project.backendcursojava.SpringApplicationContext;
import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
    public static final long EXPIRATION_DATE = 864000000;// 10 days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";// Prefix for the token
    public static final String HEADER_STRING = "Authorization";// Header for the token
    public static final String SIGN_UP_URL = "/users";// URL for the sign up
    public static final String TOKEN_SECRET = "VZ5NRcHLHZCMX6-d3A52BzIiKNilhWpHJNr5hf9xBP26SxHpNPPaRvY0LGpSZmCdSYe3Tf5fCps9FkZowSrTCw"; // token secret for HS512 algorithm
    // public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);// Secret key for the token
    // public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));// Secret key for the token

    public static SecretKey getSecretKey() {
        Properties properties = (Properties) SpringApplicationContext.getBean("AppProperties");

        return Keys.hmacShaKeyFor(properties.getTokenSecret().getBytes(StandardCharsets.UTF_8));
    }
}
