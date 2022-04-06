package com.anyungu.data.mapping.security;

import com.anyungu.data.mapping.entities.PasswordReset;
import com.anyungu.data.mapping.entities.Role;
import com.anyungu.data.mapping.entities.User;
import com.anyungu.data.mapping.enums.ERole;
import com.anyungu.data.mapping.repos.PasswordResetRepository;
import com.anyungu.data.mapping.repos.RoleRepository;
import com.anyungu.data.mapping.repos.UserRepository;
import com.anyungu.data.mapping.v1.models.requests.ChangePasswordRequest;
import com.anyungu.data.mapping.v1.models.requests.LoginRequest;
import com.anyungu.data.mapping.v1.models.requests.SignupRequest;
import com.anyungu.data.mapping.v1.models.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserActionService {

    @Autowired
    PasswordResetRepository passwordResetRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public void registerUser(SignupRequest signUpRequest) {
        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
    }


    public void generatePasswordLink(String email) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username/email: " + email));

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordResetRepository.save(passwordReset);
        // TODO: Send Email with link
    }


    public Boolean confirmResetLinkIsActive(UUID token) {
        PasswordReset passwordReset = passwordResetRepository.getById(token);
        return passwordReset.getActive();
    }

    public void resetPasswordUsingToken(UUID token, ChangePasswordRequest changePasswordRequest) {
        PasswordReset passwordReset = passwordResetRepository.getById(token);
        if (passwordReset.getActive()) {
            // Update Users Password
            User user = passwordReset.getUser();
            user.setPassword(encoder.encode(changePasswordRequest.getPassword()));

            // Invalidate token
            passwordReset.setActive(false);
            userRepository.save(user);
            passwordResetRepository.save(passwordReset);
        }
    }
}
