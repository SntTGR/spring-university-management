package snttgr.alkemy.challenge.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.services.StudentService;

import java.util.List;
import java.util.NoSuchElementException;


@Component
public class SnAAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(SnAAuthenticationProvider.class);

    @Autowired
    private StudentService studentService;

    private static final List<String> adminPasswords = List.of(
            passwordEncoder().encode("admin"),
            passwordEncoder().encode("foo"),
            passwordEncoder().encode("root")
    );


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.debug(" ------ Starting authentication -> " + authentication.getName() + ":" + authentication.getCredentials());

        if(authentication.getName().equals("admin")){

            if (adminPasswords.stream().anyMatch(s -> passwordEncoder().matches(authentication.getName(),s)))
                return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }else{

            //throws if not found or username contains invalid
            Student studentAuth;
            try {
                studentAuth = studentService.findById(Long.parseLong(authentication.getName()));
            } catch(NumberFormatException|NoSuchElementException e){ throw new BadCredentialsException("Invalid user ID"); }

            //log.debug(" ------ Authenticating user -> " + studentAuth.getId() + ":" + studentAuth.getDni() + " against " + authentication.getName().toString() + ":" + authentication.getCredentials());

            if (studentAuth.getDni().equals(Integer.parseInt(authentication.getCredentials().toString())))

                return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }

        return null;
    }

    //NOTE: Should be bean or already instanced because of @Component?
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
