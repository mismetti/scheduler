package com.mila.scheduler.infrastructure.security;
import com.mila.scheduler.business.dto.UserDTO;
import com.mila.scheduler.infrastructure.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UserClient client;

    public UserDetails loadUserData(String email, String token){
        UserDTO userDTO = client.searchUserByEmaildto(email, token);
        return User
                .withUsername(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
