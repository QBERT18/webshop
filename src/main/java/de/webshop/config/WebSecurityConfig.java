package de.webshop.config;

import de.webshop.services.impl.UserAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
    private static final String[] UNRESTRICTED_ACCESS = {"/login", "/register", "/login/failed", "/register/**", "/impressum", "/contact"};
    private static final String LOGIN_SITE = "/login";
    private static final String LOGIN_SUCCESS = "/login/success";
    private static final String LOGIN_FAILED = "/login/failed";

    private final UserAuthServiceImpl userAuthService;

    @Autowired
    public WebSecurityConfig(final UserAuthServiceImpl userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
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
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
