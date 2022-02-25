package com.banktemplate.services;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.domain.entity.CreditEntity;
import org.springframework.http.ResponseEntity;

public interface ICreditService {

    //Create - Update - Delete
    CreditDto createCredit(CreditDto dto);
    CreditDto updateCredit(CreditDto dto);
    Boolean deleteCredit(Long id);

    //Read
    boolean existsByIdentityNumber(String identityNumber);
    boolean existsById(Long id);
    CreditDto getByIdentityNumber(String identityNumber);
    CreditDto getById(Long id);

}
