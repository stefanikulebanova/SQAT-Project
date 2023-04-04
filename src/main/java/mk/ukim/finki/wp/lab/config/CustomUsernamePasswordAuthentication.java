package mk.ukim.finki.wp.lab.config;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUsernamePasswordAuthentication implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomUsernamePasswordAuthentication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("".equals(username) || "".equals(password)) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        Student student;
        if (this.userService.loadUserByUsername(username).isPresent()) {
            student = this.userService.loadUserByUsername(username).get();
            String pass = passwordEncoder.encode(student.getPassword());
            if (!passwordEncoder.matches(password, pass)) {
                throw new BadCredentialsException("Password is incorrect!");
            }
            return new UsernamePasswordAuthenticationToken(student, student.getPassword());
        } else throw new BadCredentialsException("Invalid Credentials");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

