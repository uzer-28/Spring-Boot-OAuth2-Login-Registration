package com.uzair.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.uzair.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter {

	private final UserService service;

    @Autowired
    public SecurityConfig(@Lazy UserService service) {
        this.service = service;
    }
    
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/login", "/registerUser/**", "/signup", 
								"/passwordResetRequest", "/resetPassword", "/resources/**"
								).permitAll()
	                    .anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.permitAll())
				.oauth2Login(oauth -> oauth
						.loginPage("/login")	                 
						.defaultSuccessUrl("/", true)
						.permitAll())
				.logout(logout -> logout
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.authenticationProvider(authenticationProvider())
				.build();
	}
	
		
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(service);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	//these above and below two methods helps to authenticate user at time of login.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
}
