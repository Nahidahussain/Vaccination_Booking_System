package com.vaccinationbookingsystem.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vaccinationbookingsystem.Service.auth.AuthServices;
import com.vaccinationbookingsystem.Service.jwt.UserDetailsServiceImpl;
import com.vaccinationbookingsystem.dto.AuthenticateRequest;
import com.vaccinationbookingsystem.dto.SignUpRequestDto;
import com.vaccinationbookingsystem.dto.UserResponseDto;
import com.vaccinationbookingsystem.model.User;
import com.vaccinationbookingsystem.repository.UserRepository;
import com.vaccinationbookingsystem.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthServices authServices;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticateRequest authenticateRequest , HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(), authenticateRequest.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmailId(userDetails.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(
                    new JSONObject()
                            .put("name",optionalUser.get().getName())
                            .put("userName", optionalUser.get().getEmailId())
                            .put("password", optionalUser.get().getPassword())
                            .put("userRole",optionalUser.get().getUserRole())
                            .toString()
            );

            // this two lines are to expose the headers to frontend
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Oigin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");


            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequestDto signupRequestDto){
        // check if the user already exists
        if(authServices.hasUserWithEmail(signupRequestDto.getEmail())){
            return new ResponseEntity<>("user with this email id already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        // if not exists then create the user
        UserResponseDto createdUser = authServices.createUser(signupRequestDto);

        // return the response
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);

    }
}
