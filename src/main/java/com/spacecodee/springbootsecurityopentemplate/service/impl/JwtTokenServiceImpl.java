package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IJwtTokenRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class JwtTokenServiceImpl implements IJwtTokenService {

    private final IJwtTokenRepository jwtTokenRepository;
    private final IJwtTokenMapper jwtTokenMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    private final Logger logger = Logger.getLogger(JwtTokenServiceImpl.class.getName());

    @Override
    public boolean existsByJwtTokenToken(String lang, String jwt) {
        var existsByToken = this.jwtTokenRepository.existsByToken(jwt);

        if (!existsByToken) {
            throw this.exceptionShortComponent.tokenNotFoundException("token.not.exists", lang);
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
    public SecurityJwtTokenDTO findBySecurityToken(String locale, String jwt) {
        var foundToken = this.jwtTokenRepository.findByToken(jwt);
        return foundToken.map(jwtTokenMapper::toSecurityJwtTokenDTO)
                .orElseThrow(() -> this.exceptionShortComponent.tokenNotFoundException("token.not.found", locale));
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
    @Transactional
    public void deleteByToken(String locale, String token) {
        try {
            var jwtToken = this.jwtTokenRepository.findByToken(token)
                    .orElseThrow(() -> this.exceptionShortComponent.tokenNotFoundException("token.not.found", locale));

            this.jwtTokenRepository.delete(jwtToken);
        } catch (Exception e) {
            throw this.exceptionShortComponent.tokenNotFoundException("token.not.delete", locale);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(String locale, Integer userId) {
        try {
            this.jwtTokenRepository.deleteByUserEntityId(userId);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting tokens for user", e);
            throw this.exceptionShortComponent.tokenNotFoundException("token.not.delete", locale);
        }
    }
}
