package snttgr.alkemy.challenge.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SnASecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SnAAuthenticationProvider snAAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(snAAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                    .antMatchers("/").permitAll()
                    .antMatchers("/login/success").hasAnyRole("USER","ADMIN")
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")

                .and()

                    .formLogin().loginPage("/login")
                    .defaultSuccessUrl("/login/success")
                    .failureUrl("/login?error")
                    .usernameParameter("username").passwordParameter("password")

                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/login?logout");

    }
}
