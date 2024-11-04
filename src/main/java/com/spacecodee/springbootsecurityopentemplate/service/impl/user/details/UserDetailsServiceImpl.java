package com.spacecodee.springbootsecurityopentemplate.service.impl.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.user.details.IUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
    private final IUserRepository userRepository;
    private final IUserMapper userDTOMapper;

    private final ExceptionShortComponent exceptionShortComponent;
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public UserDetailsDTO findOneByUsername(String locale, String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> {
                    logger.info("User found with username: " + username);
                    return this.userDTOMapper.toUserDetailsDTO(user);
                })
                .orElseThrow(() -> this.exceptionShortComponent.usernameNotFoundException("user.exists.not.by.username", locale));
    }

    @Override
    public Optional<UserDetailsDTO> findByUsername(String username) {
        var user = this.userRepository.findByUsername(username);

        return user.map(this.userDTOMapper::toUserDetailsDTO).or(Optional::empty);
    }
}
