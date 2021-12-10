package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // implementam aceasta metoda pentru a putea sa o foloseasca SpringSecurity, usernName poate fi emailul
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // apelam baza de date ca sa verificam daca exista un user cu emailul folosit la logare
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        // despachetam optional user luat din baza de date cu get si il stocam in user
        User user = optionalUser.get();
        // construim o colectie  in care vom stoca rolurile ale userului, instantiem un set
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // adaugam unicul drept al userului, pe rolul respectiv ce Seller sau Bidder, ii dam stringul sa il autorizeze Springul
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        //aici este User din spring framework, incarcam un user de la noi din aplicatie si il translatam catre userul din Spring pe care il asteapta SpringFrameWork
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorities);
// pe baza informatiei din userul nostru, construim un user din userDetails al Springului ca acel obiect sa fie folosit in login
    }
}
