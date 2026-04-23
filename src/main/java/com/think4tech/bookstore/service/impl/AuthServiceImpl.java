package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.auth.LoginRequest;
import com.think4tech.bookstore.auth.LoginResponse;
import com.think4tech.bookstore.dto.RegisterRequest;
import com.think4tech.bookstore.dto.UserResponse;
import com.think4tech.bookstore.entity.Role;
import com.think4tech.bookstore.entity.User;
import com.think4tech.bookstore.exception.ApiException;
import com.think4tech.bookstore.jwt.JwtService;
import com.think4tech.bookstore.repository.UserRepository;
import com.think4tech.bookstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new LoginResponse(
                token,
                "Login successful",
                userDetails.getUsername(), // This is the email
                authorities
        );
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setUserName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .userId(savedUser.getId())
                .fullName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .status(savedUser.getStatus())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
