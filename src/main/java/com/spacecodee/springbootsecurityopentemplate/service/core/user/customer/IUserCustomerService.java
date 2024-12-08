package com.spacecodee.springbootsecurityopentemplate.service.core.user.customer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;

import java.util.List;

public interface IUserCustomerService {
    void add(CustomerAVO customerAVO, String locale);

    void update(int id, CustomerUVO customerUVO, String locale);

    void delete(int id, String locale);

    CustomerDTO findById(int id, String locale);

    List<CustomerDTO> findAll(String locale);
}