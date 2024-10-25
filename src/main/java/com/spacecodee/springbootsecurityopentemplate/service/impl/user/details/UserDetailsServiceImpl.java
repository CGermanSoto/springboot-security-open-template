package com.spacecodee.springbootsecurityopentemplate.service.impl.user.details;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.ticklyspace.mappers.IUserMapper;
import com.spacecodee.ticklyspace.persistence.repository.IUserRepository;
import com.spacecodee.ticklyspace.service.user.details.IUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
    private final IUserRepository userRepository;
    private final IUserMapper userDTOMapper;

    @Override
    public Optional<UserDetailsDTO> findByUsername(String username) {
        var user = this.userRepository.findByUsername(username);

        return user.map(this.userDTOMapper::toUserDetailsDTO).or(Optional::empty);
    }
}
