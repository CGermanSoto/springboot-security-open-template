package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;
import com.spacecodee.springbootsecurityopentemplate.mappers.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IJwtTokenRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class JwtTokenServiceImpl implements IJwtTokenService {

    private final IJwtTokenRepository jwtTokenRepository;
    private final IJwtTokenMapper jwtTokenMapper;

    @Override
    public Optional<UserDetailsJwtTokenDTO> findByToken(String jwt, String lang) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetailsJwtTokenDTO> findByToken(String jwt) {
        return Optional.empty();
    }

    @Override
    public Optional<SecurityJwtTokenDTO> findBySecurityToken(String jwt) {
        return Optional.empty();
    }

    @Override
    public void save(UserDetailsJwtTokenDTO token) {

    }

    @Override
    public void save(SecurityJwtTokenDTO securityJwtTokenDTO) {

    }

    @Override
    public void save(JwtTokeUVO token) {
        this.jwtTokenRepository.save(this.jwtTokenMapper.voToEntity(token));
    }
}
