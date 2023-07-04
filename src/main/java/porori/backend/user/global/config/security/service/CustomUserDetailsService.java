package porori.backend.user.global.config.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import porori.backend.user.domain.user.application.validate.UserValidationService;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserValidationService validateService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String appleId) throws UsernameNotFoundException {
        return new CustomUserDetails(this.validateService.validateAppleId(appleId));
    }
}
