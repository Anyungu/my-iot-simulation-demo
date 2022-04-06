package com.anyungu.data.mapping.security;

import com.anyungu.data.mapping.v1.models.requests.ChangePasswordRequest;
import com.anyungu.data.mapping.v1.models.requests.LoginRequest;
import com.anyungu.data.mapping.v1.models.requests.PasswordResetRequest;
import com.anyungu.data.mapping.v1.models.requests.SignupRequest;
import com.anyungu.data.mapping.v1.models.response.JwtResponse;
import com.anyungu.data.mapping.v1.models.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserActionService userActionService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userActionService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        //        if (userDetailsService.loadUserByUsername(signUpRequest.getUsername()).){}
        //        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        //            return ResponseEntity
        //                    .badRequest()
        //                    .body(new MessageResponse("Error: Username is already taken!"));
        //        }
        //
        //        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        //            return ResponseEntity
        //                    .badRequest()
        //                    .body(new MessageResponse("Error: Email is already in use!"));
        //        }
        userActionService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/password/send-link")
    public ResponseEntity<?> sendResetLink(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        userActionService.generatePasswordLink(passwordResetRequest.getEmail());
        return ResponseEntity.ok(new MessageResponse("Link Sent!"));
    }

    @PostMapping("/password/token/{token}")
    public ResponseEntity<?> confirmResetToken(@PathVariable UUID token) {
        if (userActionService.confirmResetLinkIsActive(token)) {
            return ResponseEntity.ok(new MessageResponse("Token is ok"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Token Expired/Invalid"));
    }

    @PostMapping("/password/reset/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable UUID token, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        if (userActionService.confirmResetLinkIsActive(token)) {
            userActionService.resetPasswordUsingToken(token, changePasswordRequest);
            return ResponseEntity.ok(new MessageResponse("Password Changed"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Token Expired/Invalid"));
    }
}