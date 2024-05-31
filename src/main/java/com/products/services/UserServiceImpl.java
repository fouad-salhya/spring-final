package com.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.products.entities.UserEntity;
import com.products.repository.UserRespository;
import com.products.shared.Utils;

@Service
public class UserServiceImpl  {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    Utils utils;
    
    private UserRespository userRepository;
    
    public UserServiceImpl(UserRespository userRepository) {
        super();
        this.userRepository = userRepository;
    }



   
    public UserEntity save(UserEntity user) {
       
    	user.setUserId(utils.generateStringId(32));
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	
    	
        return userRepository.save(user);
    }
}
