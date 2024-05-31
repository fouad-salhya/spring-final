package com.products.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {


	private static final long serialVersionUID = 3834920441617488567L;
	private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String fullname;
    private String phoneNumber;
    private String country;
    private String idType;
    private String idNumber;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             String fullname, String phoneNumber, String country, String idType, String idNumber) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getIdType() {
        return idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
