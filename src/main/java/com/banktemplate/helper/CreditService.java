package com.banktemplate.helper;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.core.ICreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
public class CreditService {

    @Autowired
    ICreditRepository creditRepository;

    // http://localhost:8090/api/credit/post
    @PostMapping("/post")
    public ResponseEntity<CreditDto> postInsertCredit(@RequestBody CreditDto dto){

        boolean isInsertable = creditRepository.existsByIdentityNumber(dto.getIdentityNumber());

        if(!isInsertable){

            dto.setCreditScore(creditRepository.calculateCreditScore());
            dto = creditRepository.calculateCreditLimit(dto);

            CreditDto newDto = creditRepository.saveCredit(dto);

            return ResponseEntity.ok().header("response","true").body(newDto);
        }

        return ResponseEntity.ok().header("response","false").body(null);
    }

    // http://localhost:8090/api/credit/get/83895093042
    @GetMapping("/get/{identity_number}")
    public ResponseEntity<CreditDto> getCredit(@PathVariable( value = "identity_number") String identiy_number){

        boolean isExist = creditRepository.existsByIdentityNumber(identiy_number);

        if(isExist){
            CreditDto dto = creditRepository.getCreditDtoEntityByIdentityNumber(identiy_number);
            return ResponseEntity.ok().header("response","true").body(dto);
        }
        else{
            return ResponseEntity.ok().header("response","false").body(null);
        }

    }

    // http://localhost:8090/api/credit/put
    @PutMapping("/put")
    public ResponseEntity<CreditDto> putUpdateCredit(@RequestBody CreditDto dto){

        CreditDto getDto = creditRepository.getCreditDtoEntityByIdentityNumber(dto.getIdentityNumber());

        if(getDto != null){

            dto.setId(getDto.getId());
            dto.setCreditScore(creditRepository.calculateCreditScore());
            dto = creditRepository.calculateCreditLimit(dto);

            CreditDto newDto = creditRepository.saveCredit(dto);

            return ResponseEntity.ok().header("response","true").body(newDto);
        }

        return ResponseEntity.ok().header("response","false").body(null);
    }

    // http://localhost:8090/api/credit/delete/2
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCredit(@PathVariable( value = "id") Long id){
        boolean isExist = creditRepository.existsById(id);

        if(isExist){
            creditRepository.deleteById(id);
        }
        return ResponseEntity.ok().body(isExist);

    }
}
