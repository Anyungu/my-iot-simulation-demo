package com.anyungu.data.mapping.security;

import com.anyungu.data.mapping.entities.PasswordReset;
import com.anyungu.data.mapping.entities.User;
import com.anyungu.data.mapping.repos.PasswordResetRepository;
import com.anyungu.data.mapping.repos.UserRepository;
import com.anyungu.data.mapping.v1.models.requests.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordResetRepository passwordResetRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username/email: " + username));
        return UserDetailsImpl.build(user);
    }


    public void generatePasswordLink(String email) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username/email: " + email));

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordResetRepository.save(passwordReset);
        // TODO: Send Email with link
    }

    public Boolean confirmResetLinkisActive(UUID token) {
        PasswordReset passwordReset = passwordResetRepository.getById(token);
        return passwordReset.getActive();
    }

    public void resetPasswordUsingToken(UUID token, ChangePasswordRequest changePasswordRequest) {
        PasswordReset passwordReset = passwordResetRepository.getById(token);
        if (passwordReset.getActive()) {
            passwordReset.setActive(false);

        }
    }
}