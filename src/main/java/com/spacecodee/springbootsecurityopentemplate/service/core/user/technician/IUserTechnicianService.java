package com.spacecodee.springbootsecurityopentemplate.service.core.user.technician;

import java.util.List;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;

public interface IUserTechnicianService {
    void add(TechnicianAVO technicianAVO, String locale);

    void update(int id, TechnicianUVO technicianUVO, String locale);

    void delete(int id, String locale);

    TechnicianDTO findById(int id, String locale);

    List<TechnicianDTO> findAll(String locale);
}