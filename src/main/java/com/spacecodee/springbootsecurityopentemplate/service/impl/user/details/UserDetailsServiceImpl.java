package com.spacecodee.springbootsecurityopentemplate.service.impl.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.user.details.IUserDetailsService;
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
