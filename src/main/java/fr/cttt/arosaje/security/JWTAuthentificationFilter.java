package fr.cttt.arosaje.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private String secret = "arosaje";
    private Integer durationTime = 1;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email, password;
        try {
            HashMap jsonData = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
            email = jsonData.get("email").toString();
            password = jsonData.get("password").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        User user = userRepository.findByEmail(userPrincipal.getUsername()).get();
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + durationTime * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("userId", user.getId())
                .sign(algorithm);
        Map<String, String> token = new HashMap<>();
        token.put("token", accessToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }
}
