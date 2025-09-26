package com.govind.blogging.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.govind.blogging.blog.Security.CustomUserDetailService;
import com.govind.blogging.blog.Security.JwtTokenHelper;
import com.govind.blogging.payload.JwtAuthRequest;
import com.govind.blogging.payload.JwtAuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        try {
            // Authenticate username & password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Load user details
            UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUsername());

            // Generate token
            String token = jwtTokenHelper.generateToken(userDetails);

            // Return token
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }
}
