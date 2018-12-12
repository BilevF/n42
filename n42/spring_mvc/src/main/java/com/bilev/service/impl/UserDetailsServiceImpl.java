package com.bilev.service.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        try {
            user = userDao.findByEmail(username);
        } catch (UnableToFindException e) {
            throw new UsernameNotFoundException("Username not found");
        }

        if (user.getPassword().isEmpty()) throw new UsernameNotFoundException("Username not found");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName().name()));

        return new ExtendUser(user.getId(), user.getEmail(), user.getPassword(), grantedAuthorities);
    }


    @Setter
    @Getter
    public class ExtendUser extends org.springframework.security.core.userdetails.User {

        private final Integer id;

        public ExtendUser(Integer id, String username, String password, Set<GrantedAuthority> grantedAuthorities) {

            super(username, password, grantedAuthorities);

            this.id = id;
        }
    }


}
