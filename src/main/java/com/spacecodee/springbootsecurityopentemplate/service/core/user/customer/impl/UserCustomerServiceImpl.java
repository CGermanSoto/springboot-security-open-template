package com.spacecodee.springbootsecurityopentemplate.service.core.user.client.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IClientMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.client.IUserCustomerService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenManagementService;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserCustomerServiceImpl implements IUserCustomerService {
    private static final String CLIENT_PREFIX = "client";

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IJwtTokenManagementService jwtTokenService;
    private final IClientMapper clientMapper;
    private final IUserValidationService userValidationService;
    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.default.customer.role}")
    private String customerRole;

    public UserCustomerServiceImpl(PasswordEncoder passwordEncoder,
                                   IUserRepository userRepository,
                                   IRoleService roleService,
                                   IJwtTokenManagementService jwtTokenService,
                                   IClientMapper clientMapper,
                                   IUserValidationService userValidationService,
                                   ExceptionShortComponent exceptionShortComponent) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenService = jwtTokenService;
        this.clientMapper = clientMapper;
        this.userValidationService = userValidationService;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    @Transactional
    public void add(@NotNull CustomerAVO customerAVO, String locale) {
        if (!AppUtils.comparePasswords(customerAVO.getPassword(), customerAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.userValidationService.validateUsername(customerAVO.getUsername(), CLIENT_PREFIX, locale);
        var roleEntity = this.roleService.findByName(this.customerRole, locale);
        var clientEntity = this.clientMapper.voToEntity(customerAVO);

        clientEntity.setPassword(this.passwordEncoder.encode(customerAVO.getPassword()));
        clientEntity.setRoleEntity(roleEntity);

        try {
            this.userRepository.save(clientEntity);
        } catch (Exception e) {
            log.error("Error saving client", e);
            throw this.exceptionShortComponent.cannotSaveException("client.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull CustomerUVO customerUVO, String locale) {
        var existingClient = this.userValidationService.validateUserUpdate(id, customerUVO.getUsername(), CLIENT_PREFIX,
                locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(customerUVO, existingClient);

        if (hasChanges) {
            saveClientChanges(existingClient, locale);
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingClient = this.userValidationService.validateUserUpdate(id, null, CLIENT_PREFIX, locale);
        validateLastClient(locale);

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(existingClient);
        } catch (Exception e) {
            log.error("Error deleting client", e);
            throw this.exceptionShortComponent.noDeletedException("client.deleted.failed", locale);
        }
    }

    @Override
    public CustomerDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("client.invalid.id", locale);
        }

        return this.userRepository.findById(id)
                .map(this.clientMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(
                        "client.not.exists.by.id",
                        locale));
    }

    @Override
    public List<CustomerDTO> findAll(String locale) {
        var clients = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.customerRole));
        return this.clientMapper.toDtoList(clients);
    }

    private void saveClientChanges(UserEntity client, String locale) {
        try {
            this.jwtTokenService.deleteByUserId(locale, client.getId());
            this.userRepository.save(client);
        } catch (Exception e) {
            log.error("Error updating client", e);
            throw this.exceptionShortComponent.noUpdatedException("client.updated.failed", locale);
        }
    }

    private void validateLastClient(String locale) {
        var clientsCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.customerRole));
        if (clientsCount <= 1) {
            throw this.exceptionShortComponent.lastClientException("client.deleted.failed.last", locale);
        }
    }
}