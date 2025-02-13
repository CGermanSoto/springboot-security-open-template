package com.spacecodee.springbootsecurityopentemplate.service.user.guest.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.CreateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.GuestFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.UpdateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.guest.IGuestMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IGuestRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.guest.IGuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements IGuestService {

    private final IRoleService roleService;
    private final IGuestRepository guestRepository;
    private final IGuestMapper guestMapper;
    private final ExceptionShortComponent exceptionShortComponent;
    private final PasswordEncoder passwordEncoder;

    @Value("${role.default.guest}")
    private String guestRole;

    @Override
    @Transactional
    public GuestDTO createGuest(CreateGuestVO createGuestVO) {
        this.validateGuestRequest(createGuestVO, "guest.create.empty");

        try {
            String username = createGuestVO.getUsername().trim();
            String email = createGuestVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.guestRole);

            UserEntity userEntity = this.guestMapper.toEntity(createGuestVO);
            userEntity.setPassword(this.passwordEncoder.encode(createGuestVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.guestMapper.toDto(this.guestRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("guest.create.failed", createGuestVO.getUsername());
        }
    }

    @Override
    @Transactional
    public GuestDTO updateGuest(UpdateGuestVO updateGuestVO) {
        this.validateGuestRequest(updateGuestVO, "guest.update.empty");
        this.validateId(updateGuestVO.getId());

        try {
            UserEntity existingGuest = ((IGuestService) AopContext.currentProxy()).getGuestEntityById(updateGuestVO.getId());

            if (updateGuestVO.getStatus() != null) {
                existingGuest.setStatus(Boolean.parseBoolean(updateGuestVO.getStatus()));
            }
            this.guestMapper.updateEntity(existingGuest, updateGuestVO);

            return this.guestMapper.toDto(this.guestRepository.save(existingGuest));
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("guest.update.failed", updateGuestVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GuestDTO getGuestById(Integer id) {
        this.validateId(id);

        try {
            UserEntity guest = ((IGuestService) AopContext.currentProxy()).getGuestEntityById(id);

            return this.guestMapper.toDto(guest);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GuestDetailDTO getGuestDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity guest = ((IGuestService) AopContext.currentProxy()).getGuestEntityById(id);

            return this.guestMapper.toDetailDto(guest);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving guest details with ID: {}", id, e);
            throw this.exceptionShortComponent.objectNotFoundException("guest.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getGuestEntityById(Integer id) {
        this.validateId(id);

        try {
            var guest = this.guestRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("guest.not.found", id.toString()));
            var roleEntity = this.roleService.findByName(this.guestRole);
            if (!guest.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("guest.invalid.role", id.toString());
            }

            return guest;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuestDTO> searchGuests(GuestFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new GuestFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.guestRole);

            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.guestRepository.searchGuests(filterVO.getUsername(), filterVO.getFirstName(), filterVO.getLastName(),
                            roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.guestMapper::toDto
                    );

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public GuestDTO changeGuestStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("guest.status.empty");
        }

        try {
            UserEntity guestEntity = ((IGuestService) AopContext.currentProxy()).getGuestEntityById(id);
            guestEntity.setStatus(status);

            return this.guestMapper.toDto(this.guestRepository.save(guestEntity));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("guest.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("guest.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.guestRole);
            return this.guestRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("guest.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.guestRole);
            return this.guestRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteGuest(Integer id) {
        this.validateId(id);

        UserEntity guestEntity = ((IGuestService) AopContext.currentProxy()).getGuestEntityById(id);

        if (!this.guestRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("guest.not.found", id.toString());
        }

        try {
            this.guestRepository.delete(guestEntity);
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("guest.delete.failed", id.toString());
        }
    }

    private void validateGuestRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.guestRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("guest.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {

        if (this.guestRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("guest.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("guest.id.invalid", String.valueOf(id));
        }
    }

}
