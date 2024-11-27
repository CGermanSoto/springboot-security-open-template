package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
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
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public boolean existsByJwtTokenToken(String lang, String jwt) {
        var existsByToken = this.jwtTokenRepository.existsByToken(jwt);

        if (!existsByToken) {
            throw this.exceptionShortComponent.tokenNotFoundException("token.exists.not", lang);
        }

        return true;
    }

    @Override
    public String getTokenByUsername(String username) {
        return this.jwtTokenRepository.findByUserEntity_Username(username)
                .map(JwtTokenEntity::getToken)
                .orElse("");
    }

    @Override
    public Optional<JwtTokenDTO> findTokenByUsername(String username) {
        return this.jwtTokenRepository.findByUserEntity_Username(username)
                .map(this.jwtTokenMapper::toDTO);
    }

    @Override
    public SecurityJwtTokenDTO findBySecurityToken(String jwt) {
        var foundToken = this.jwtTokenRepository.findByToken(jwt);
        return foundToken.map(jwtTokenMapper::toSecurityJwtTokenDTO)
                .orElseThrow(() -> this.exceptionShortComponent.tokenNotFoundException("token.found.not", "eng"));
    }

    @Override
    public void save(UserDetailsJwtTokenDTO token) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void save(SecurityJwtTokenDTO securityJwtTokenDTO) {
        this.jwtTokenRepository.save(this.jwtTokenMapper.dtoToEntity(securityJwtTokenDTO));
    }

    @Override
    public void save(JwtTokeUVO token) {
        this.jwtTokenRepository.save(this.jwtTokenMapper.voToEntity(token));
    }

    @Override
    public void deleteByToken(String lang, String token) {
        try {
            this.jwtTokenRepository.deleteByToken(token);
        } catch (Exception e) {
            throw this.exceptionShortComponent.tokenNotFoundException("token.delete.not", lang);
        }
    }
}
