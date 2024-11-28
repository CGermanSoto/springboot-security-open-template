package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.ITechnicianMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserTechnicianService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import com.spacecodee.springbootsecurityopentemplate.utils.UserUpdateUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserTechnicianServiceImpl implements IUserTechnicianService {

    private static final String TECHNICIAN_INVALID_ID = "technician.invalid.id";
    private static final String TECHNICIAN_NOT_EXISTS_BY_ID = "technician.not.exists.by.id";

    private final Logger logger = Logger.getLogger(UserTechnicianServiceImpl.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final ExceptionShortComponent exceptionShortComponent;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IJwtTokenService jwtTokenService;
    private final ITechnicianMapper technicianMapper;

    @Value("${security.default.technician.role}")
    private String technicianRole;

    public UserTechnicianServiceImpl(PasswordEncoder passwordEncoder, ExceptionShortComponent exceptionShortComponent, IUserRepository userRepository, IRoleService roleService, IJwtTokenService jwtTokenService, ITechnicianMapper technicianMapper) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenService = jwtTokenService;
        this.technicianMapper = technicianMapper;
    }

    @Override
    @Transactional
    public void add(TechnicianAVO technicianAVO, String locale) {
        if (!AppUtils.comparePasswords(technicianAVO.getPassword(), technicianAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.alreadyExistByUsername(technicianAVO.getUsername(), locale);

        var roleEntity = this.roleService.findByName(this.technicianRole, locale);
        var technicianEntity = this.technicianMapper.voToEntity(technicianAVO);

        technicianEntity.setPassword(this.passwordEncoder.encode(technicianAVO.getPassword()));
        technicianEntity.setRoleEntity(roleEntity);

        try {
            this.userRepository.save(technicianEntity);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error saving technician", e);
            throw this.exceptionShortComponent.cannotSaveException("technician.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, TechnicianUVO technicianUVO, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(TECHNICIAN_INVALID_ID, locale);
        }

        var existingTechnician = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(TECHNICIAN_NOT_EXISTS_BY_ID,
                        locale));

        if (!existingTechnician.getUsername().equals(technicianUVO.getUsername())) {
            this.alreadyExistByUsername(technicianUVO.getUsername(), locale);
        }

        boolean hasChanges = UserUpdateUtils.checkForChanges(technicianUVO, existingTechnician);

        if (hasChanges) {
            try {
                this.jwtTokenService.deleteByUserId(locale, existingTechnician.getId());
                this.userRepository.save(existingTechnician);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating technician", e);
                throw this.exceptionShortComponent.noUpdatedException("technician.updated.failed", locale);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(TECHNICIAN_INVALID_ID, locale);
        }

        var technician = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(TECHNICIAN_NOT_EXISTS_BY_ID,
                        locale));

        var technicianCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.technicianRole));
        if (technicianCount <= 1) {
            throw this.exceptionShortComponent.noDeletedException("technician.deleted.failed.last", locale);
        }

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(technician);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting technician", e);
            throw this.exceptionShortComponent.noDeletedException("technician.deleted.failed", locale);
        }
    }

    @Override
    public TechnicianDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(TECHNICIAN_INVALID_ID, locale);
        }

        return this.userRepository.findById(id)
                .map(this.technicianMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(TECHNICIAN_NOT_EXISTS_BY_ID,
                        locale));
    }

    @Override
    public List<TechnicianDTO> findAll(String locale) {
        var technicians = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.technicianRole));
        return this.technicianMapper.toDtoList(technicians);
    }

    private void alreadyExistByUsername(String username, String locale) {
        if (this.userRepository.existsByUsername(username)) {
            throw this.exceptionShortComponent.alreadyExistsException("technician.exists.by.username", locale);
        }
    }
}