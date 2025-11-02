package lk.wedrent.wedrent_backend.controller;

import jakarta.validation.Valid;
import lk.wedrent.wedrent_backend.dto.JwtAuthResponse;
import lk.wedrent.wedrent_backend.dto.LoginRequest;
import lk.wedrent.wedrent_backend.dto.RegisterRequest;
import lk.wedrent.wedrent_backend.entity.User;
import lk.wedrent.wedrent_backend.security.JwtProvider;
import lk.wedrent.wedrent_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtProvider.generateToken(request.getEmail());
        
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
