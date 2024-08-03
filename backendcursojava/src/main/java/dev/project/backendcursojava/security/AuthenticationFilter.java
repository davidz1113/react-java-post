package dev.project.backendcursojava.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.project.backendcursojava.SpringApplicationContext;
import dev.project.backendcursojava.models.requests.UserLoginRequestModel;
import dev.project.backendcursojava.services.UserService;
import dev.project.backendcursojava.services.UserServiceInterface;
import dev.project.backendcursojava.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);
            // System.out.println(userModel.getEmail());
            // System.out.println(userModel.getPassword());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userModel.getEmail(),
                    userModel.getPassword(),
                    new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();

        // SecretKey key =
        // Keys.hmacShaKeyFor(SecurityConstants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

        SecretKey key = SecurityConstants.getSecretKey();

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .signWith(key, SignatureAlgorithm.HS512).compact();

        // add the public id to the header
        UserServiceInterface userService = (UserService) SpringApplicationContext.getBean("userService");

        UserDto userDto = userService.getUser(username);

        response.addHeader("Access-Control-Expose-Headers", "Authorization, userId");
        response.addHeader("userId", userDto.getUserId());

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    }
}
