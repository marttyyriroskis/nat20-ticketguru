package com.nat20.ticketguru.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User currentUser = userRepository.findByEmail(email).get();
        UserDetails user = new org.springframework.security.core.userdetails.User(email, currentUser.gethashedPassword(),
                AuthorityUtils.createAuthorityList(currentUser.getRole().getTitle()));
        return user;
    }

}
