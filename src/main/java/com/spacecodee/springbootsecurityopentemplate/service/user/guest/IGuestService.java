package com.spacecodee.springbootsecurityopentemplate.service.user.guest;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.CreateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.UpdateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.GuestFilterVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IGuestService {

    GuestDTO createGuest(CreateGuestVO createGuestVO);

    GuestDTO updateGuest(UpdateGuestVO updateGuestVO);

    GuestDTO getGuestById(Integer id);

    GuestDetailDTO getGuestDetailById(Integer id);

    UserEntity getGuestEntityById(Integer id);

    Page<GuestDTO> searchGuests(GuestFilterVO filterVO);

    GuestDTO changeGuestStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteGuest(Integer id);

}
