package com.banktemplate.api;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.services.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
public class CreditApi {


    @Autowired
    ICreditService creditService;

    // http://localhost:8090/api/credit/post
    @PostMapping("/post")
    public ResponseEntity<CreditDto> postInsertCredit(@RequestBody CreditDto dto){

        boolean isUserExist = creditService.existsByIdentityNumber(dto.getIdentityNumber());

        if(!isUserExist){
            CreditDto newDto = creditService.createCredit(dto);
            return ResponseEntity.ok().header("response","true").body(newDto);
        }

        return ResponseEntity.ok().header("response","false").body(null);
    }

    // http://localhost:8090/api/credit/get/83895093042
    @GetMapping("/get/{identity_number}")
    public ResponseEntity<CreditDto> getCredit(@PathVariable( value = "identity_number") String identiy_number){

        boolean isUserExist = creditService.existsByIdentityNumber(identiy_number);

        if(isUserExist){
            CreditDto dto = creditService.getByIdentityNumber(identiy_number);

            if(dto != null){
                return ResponseEntity.ok().header("response","true").body(dto);
            }
        }

        return ResponseEntity.ok().header("response","false").body(null);
    }

    // http://localhost:8090/api/credit/put
    @PutMapping("/put")
    public ResponseEntity<CreditDto> putUpdateCredit(@RequestBody CreditDto dto){

        CreditDto getDto = creditService.getByIdentityNumber(dto.getIdentityNumber());

        if(getDto != null){

            CreditDto newDto = creditService.updateCredit(dto);

            if(newDto != null)
                return ResponseEntity.ok().header("response","true").body(newDto);
        }

        return ResponseEntity.ok().header("response","false").body(null);
    }

    // http://localhost:8090/api/credit/delete/2
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCredit(@PathVariable( value = "id") Long id){
        boolean isExist = creditService.existsById(id);

        if(isExist){
            creditService.deleteCredit(id);
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);

    }
}
