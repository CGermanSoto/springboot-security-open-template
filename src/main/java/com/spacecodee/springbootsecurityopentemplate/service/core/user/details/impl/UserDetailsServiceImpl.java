package com.spacecodee.springbootsecurityopentemplate.service.core.user.details.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IUserDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.details.IUserDetailsService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
    private final IUserRepository userRepository;
    private final IUserDetailsMapper userDetailsMapper; // Change to new mapper
    private final ExceptionShortComponent exceptionShortComponent;
    private final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Transactional(readOnly = true, noRollbackFor = UsernameNotFoundException.class)
    @Override
    public UserDetailsDTO findByUsername(String locale, String username) {
        this.logger.log(Level.INFO, "Finding user by username: {0}", username);
        return this.userRepository.findByUsername(username)
                .map(this.userDetailsMapper::toUserDetailsDTO)
                .orElseThrow(() -> this.exceptionShortComponent.notFoundException("user.not.exists.by.username", locale));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(user -> {
                    Hibernate.initialize(user.getRoleEntity());
                    Hibernate.initialize(user.getRoleEntity().getPermissionEntities());
                    this.logger.info("User found with username: " + username);
                    return this.userDetailsMapper.toUserDetailsDTO(user);
                })
                .orElseThrow(() -> this.exceptionShortComponent.usernameNotFoundException("user.not.exists.by.username",
                        "en"));
    }
}
