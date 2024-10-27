package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.CannotSaveException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.NoDeletedException;
import com.spacecodee.springbootsecurityopentemplate.mappers.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserAdminServiceImpl implements IUserAdminService {

    private final PasswordEncoder passwordEncoder;
    private final ExceptionShortComponent exceptionShortComponent;

    private final IUserRepository userRepository;
    private final IRoleService roleService;

    private final IUserMapper userDTOMapper;

    //ignore from constructor
    @Value("${security.default.roles}")
    private String adminRole;

    private static final Logger logger = Logger.getLogger(UserAdminServiceImpl.class.getName());

    public UserAdminServiceImpl(PasswordEncoder passwordEncoder, ExceptionShortComponent exceptionShortComponent, IUserRepository userRepository, IRoleService roleService, IUserMapper userDTOMapper) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    @Transactional
    public void add(AdminVO adminVO, String locale) {
        AppUtils.validatePassword(adminVO.getPassword(), adminVO.getRepeatPassword(), locale);
        this.alreadyExistByUsername(adminVO.getUsername(), locale);

        var adminRoleEntity = this.roleService.findAdminRole();
        var userEntity = this.userDTOMapper.toEntity(adminVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (CannotSaveException e) {
            logger.log(Level.SEVERE, "UserServiceImpl.addAdmin: error", e);
            throw this.exceptionShortComponent.noCreatedException("admin.added.failed", locale);
        }
    }

    @Override
    public void update(int id, AdminVO adminVO, String locale) {
        this.alreadyExistByUsername(adminVO.getUsername(), locale);
    }

    @Override
    public void delete(int id, String locale) {
        this.doNotExistsById(id, locale);
        this.howManyAdmins(locale);

        try {
            this.userRepository.deleteById(id);
        } catch (NoDeletedException e) {
            logger.log(Level.SEVERE, "UserServiceImpl.delete: error", e);
            throw this.exceptionShortComponent.noDeletedException("admin.deleted.failed", locale);
        }
    }

    private void alreadyExistByUsername(String username, String locale) {
        var alreadyExists = this.userRepository.existsByUsername(username);
        if (alreadyExists) {
            throw this.exceptionShortComponent.alreadyExistsException("admin.exists.by.username", locale);
        }
    }

    private void doNotExistsById(int id, String locale) {
        var doNotExistsById = this.userRepository.existsById(id);
        if (!doNotExistsById) {
            logger.log(Level.SEVERE, "UserServiceImpl.doNotExistsById: {0}", false);
            throw this.exceptionShortComponent.doNotExistsByIdException("admin.exists.not.by.id", locale);
        }
    }

    private void howManyAdmins(String locale) {
        var adminRoleEnum = AppUtils.getRoleEnum(adminRole);
        var howManyAdmins = this.userRepository.countAdmins(adminRoleEnum);
        if (howManyAdmins == 1) {
            logger.log(Level.SEVERE, "UserServiceImpl.howManyAdmins: {0}", howManyAdmins);
            throw this.exceptionShortComponent.lastAdminException("admin.deleted.failed.last", locale);
        }
    }
}
