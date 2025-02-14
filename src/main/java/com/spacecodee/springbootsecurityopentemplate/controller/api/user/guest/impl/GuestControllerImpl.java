package com.spacecodee.springbootsecurityopentemplate.controller.api.user.guest.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.guest.IGuestController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.CreateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.GuestFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.UpdateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.guest.IGuestService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/guest")
public class GuestControllerImpl extends AbstractController implements IGuestController {

    private final IGuestService guestService;

    public GuestControllerImpl(MessageParameterHandler messageParameterHandler, IGuestService guestService) {
        super(messageParameterHandler);
        this.guestService = guestService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<GuestDTO>> createGuest(CreateGuestVO request) {
        GuestDTO createdGuest = this.guestService.createGuest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdGuest,
                        "guest.create.success",
                        HttpStatus.CREATED,
                        createdGuest.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<GuestDTO>> updateGuest(Integer id, @NotNull UpdateGuestVO request) {
        request.setId(id);
        GuestDTO updatedGuest = this.guestService.updateGuest(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedGuest,
                "guest.update.success",
                HttpStatus.OK,
                updatedGuest.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<GuestDTO>> getGuestById(Integer id) {
        GuestDTO guest = this.guestService.getGuestById(id);
        return ResponseEntity.ok(super.createDataResponse(
                guest,
                "guest.found.success",
                HttpStatus.OK,
                guest.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<GuestDetailDTO>> getGuestDetailById(Integer id) {
        GuestDetailDTO guestDetail = this.guestService.getGuestDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                guestDetail,
                "guest.detail.found.success",
                HttpStatus.OK,
                guestDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<GuestDTO>>> searchGuests(GuestFilterVO filterVO) {
        Page<GuestDTO> guests = this.guestService.searchGuests(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                guests,
                "guest.search.success",
                HttpStatus.OK,
                guests.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<GuestDTO>> changeGuestStatus(Integer id, Boolean status) {
        GuestDTO updatedGuest = this.guestService.changeGuestStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedGuest,
                "guest.status.change.success",
                HttpStatus.OK,
                updatedGuest.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteGuest(Integer id) {
        this.guestService.deleteGuest(id);
        return ResponseEntity.ok(super.createResponse(
                "guest.delete.success",
                HttpStatus.OK,
                id.toString()));
    }
    
}
