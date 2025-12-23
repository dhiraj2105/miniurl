package com.dhiraj.miniurl.service;

import com.dhiraj.miniurl.dto.LoginRequest;
import com.dhiraj.miniurl.dto.RegisterRequest;
import com.dhiraj.miniurl.exception.EmailAlreadyExistsException;
import com.dhiraj.miniurl.exception.InvalidCredentialsException;
import com.dhiraj.miniurl.exception.JwtGenerationException;
import com.dhiraj.miniurl.model.UserEntity;
import com.dhiraj.miniurl.repository.UserRepository;
import com.dhiraj.miniurl.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder encoder,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        UserEntity user = new UserEntity(
                request.getEmail(),
                encoder.encode(request.getPassword())
        );

        userRepository.save(user);
        try {
            return jwtUtil.generateToken(user.getEmail());
        }catch (Exception e){
            throw new JwtGenerationException();
        }
        }

    public String login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        try {
            return jwtUtil.generateToken(user.getEmail());
        } catch (Exception e) {
            throw new JwtGenerationException();
        }
    }
}