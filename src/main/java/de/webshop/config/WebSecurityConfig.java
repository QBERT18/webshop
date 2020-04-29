package de.webshop.config;

import de.webshop.constants.UserPermission;
import de.webshop.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Where all resources are stored.
     */
    private static final String[] RESOURCES = {"/css/**", "/js/**", "/img/**"};

    /**
     * These sites will be visible to anyone.
     */
    private static final String[] UNRESTRICTED_ACCESS = {"/login", "/registration", "/login/failed", "/registration/**", "/impressum", "/contact"};

    /**
     * These sites will only be visible if user has UserPermission.FULL.
     */
    private static final String[] ADMIN_ACCESS = {"/admin/**"};

    private static final String LOGIN_SITE = "/login";
    private static final String LOGIN_SUCCESS = "/login/success";
    private static final String LOGIN_FAILED = "/login/failed";

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(final UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ADMIN_ACCESS).hasRole(UserPermission.FULL.getDbCode())
                .antMatchers(RESOURCES).permitAll()
                .antMatchers(UNRESTRICTED_ACCESS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_SITE)
                .defaultSuccessUrl(LOGIN_SUCCESS)
                .failureUrl(LOGIN_FAILED)
                .and()
                .logout()
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
