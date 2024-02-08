package com.elevatemart.security.elevatemartsecurity.services.customization;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomizeUserDetails implements UserDetails {

    private ElevateMartUser martUser;
    public CustomizeUserDetails(ElevateMartUser martUser){
        this.martUser=martUser;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
/*    List<Authority> auths = martUser.getAuthorities();
        for(Authority auth:auths){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth.getName())
            authorities.add(simpleGrantedAuthority);
        }
 */
        return authorities;
    }

    @Override
    public String getPassword() {
        return martUser.getPassword();
    }

    @Override
    public String getUsername() {
        return martUser.getEmail();
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
