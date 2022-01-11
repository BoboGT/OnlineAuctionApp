package com.sda.onlineAuctionApp.config;

import com.sda.onlineAuctionApp.service.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    // ne ajuta sa encriptam parola
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // asta e o metoda de la WebSecurity si mai jos facem implementarea de Security pentru login
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ne folosim de acest configure ca sa dam requests catre security prin http, ce permitem si ce nu
        //fiecare linie e o instructiune catre spring security
        http
                // autorizeaza toate requesturile de la Dispatcher Servlet
                .authorizeRequests()
                //autorizeaza pe acest path
                .antMatchers("/registration", "/assets/**").permitAll()
                // vrem sa autentificam fiecare request, sa verificam, aici sunt autentificati cei care nu sunt logati
                .anyRequest().authenticated()
                // un si delimitator de reguli
                .and()
                // vrem sa avem un punct de logare
                .formLogin()
                // pagina de login sa fie pe adresa /login si permite la orice user sa intre pe/ login
                .loginPage("/login").permitAll()
                .failureUrl("/login-error")
                .and()
                // ofera posibilitate de logout pe mai jos
                .logout()
                // aceasta posibilitate in caz ca userul introduce /logout, il scoate din aplicatie, clasa Ant stocheaza ruta
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // dupa ce s-au delocat ii redirectioneaza catre home, in stanga ? este ruta si in dreapta parametrii, adica sa vada userul ca este pe logout
                .logoutSuccessUrl("/login")
                // permite la toti
                .permitAll();
    }

    //aceasta metoda ii spune springului sa se foloseasca specific de acel userDetailsServiceImplementation si specific bCryptPasswordEncoder() dupa implementarea noastra
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceImplementation)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
