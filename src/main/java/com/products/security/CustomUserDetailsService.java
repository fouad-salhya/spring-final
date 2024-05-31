package com.products.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.products.entities.UserEntity;
import com.products.repository.UserRespository;



@Service
public class CustomUserDetailsService implements UserDetailsService {

 private UserRespository userRepository;

 public CustomUserDetailsService(UserRespository userRepository) {
  super();
  this.userRepository = userRepository;
 }

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

  UserEntity user = userRepository.findByUsername(username);
  if (user == null) {
   throw new UsernameNotFoundException("Username or Password not found");
  }
  return new CustomUserDetails(
          user.getUsername(),
          user.getPassword(),
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
          user.getFullname(),
          user.getPhoneNumber(),
          user.getCountry(),
          user.getIdType(),
          user.getIdNumber()
  );
 }

 public Collection<? extends GrantedAuthority> authorities() {
  return Arrays.asList(new SimpleGrantedAuthority("USER"));
 }

}