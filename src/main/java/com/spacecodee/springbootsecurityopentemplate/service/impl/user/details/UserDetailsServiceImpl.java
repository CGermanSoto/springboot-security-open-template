package com.spacecodee.springbootsecurityopentemplate.service.impl.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.user.details.IUserDetailsService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
    private final IUserRepository userRepository;
    private final IUserMapper userDTOMapper;

    private final ExceptionShortComponent exceptionShortComponent;
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Override
    public UserDetailsDTO findByUsername(String username) {
        var user = this.userRepository.findByUsername(username);

        return user.map(this.userDTOMapper::toUserDetailsDTO)
                .orElseThrow(() -> this.exceptionShortComponent.notFoundException("user.exists.not.by.username", "en"));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(user -> {
                    Hibernate.initialize(user.getRoleEntity());
                    Hibernate.initialize(user.getRoleEntity().getPermissionEntities());
                    logger.info("User found with username: " + username);
                    return this.userDTOMapper.toUserDetailsDTO(user);
                })
                .orElseThrow(() -> this.exceptionShortComponent.usernameNotFoundException("user.exists.not.by.username", "en"));
    }
}
