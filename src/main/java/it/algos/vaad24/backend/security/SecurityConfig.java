package it.algos.vaad24.backend.security;

import com.vaadin.flow.spring.security.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {
    public class SecurityConfig  {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//
//        setLoginView(http, LoginView.class);
//    }
//
//    /**
//     * Allows access to static resources, bypassing Spring security.
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/images/**");
//        super.configure(web);
//    }
//
//    /**
//     * Demo UserDetailService, which only provides two hardcoded
//     * in-memory users and their roles.
//     * NOTE: This should not be used in real-world applications.
//     */
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(User.withUsername("user")
//                .password("{noop}userpass")
//                .roles("USER")
//                .build());
//    }

}