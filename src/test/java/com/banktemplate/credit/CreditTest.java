package com.banktemplate.credit;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.services.ICreditService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditTest {

    @Autowired
    private ICreditService creditService;

    //Create
    @Test
    @Order(1)
    void insertCreditTest(){

        CreditDto staticDtoExample =  CreditDto.builder()
                .id(0L)
                .name("TEST")
                .surname("DENEME")
                .identityNumber("12345678904")
                .phoneNumber("555 444 33 22")
                .monthlySalary(6000)
                .build();

        CreditDto newDto = creditService.createCredit(staticDtoExample);
        Assertions.assertNotNull(newDto,"Error occured in CreditService/insertCreditTest method. Entity failed to create ");
    }

    //Find
    @Test
    @Order(2)
    void findCreditTest(){

        boolean isUserExists = creditService.existsByIdentityNumber("12345678904");
        Assertions.assertTrue(isUserExists,"Error occured in CreditService/findCreditTest method. Entity not found ");

    }

    //Update
    @Test
    @Order(3)
    void updateCreditTest(){

        String updatedText = "DENEME - UPDATE";

        CreditDto dto = creditService.getByIdentityNumber("12345678904");
        dto.setSurname(updatedText);
        CreditDto newDto = creditService.updateCredit(dto);

        Assertions.assertNotNull(newDto,"Error occured in CreditService/updateCreditTest method. Entity not found");
        Assertions.assertEquals(newDto.getSurname(),updatedText,"Error occured in CreditService/updateCreditTest method. Entity failed to update");

    }

    //Delete
    @Test
    @Order(4)
    void deleteCreditTest(){

        CreditDto dto = creditService.getByIdentityNumber("12345678904");

        boolean isDeleted = creditService.deleteCredit(dto.getId());
        Assertions.assertTrue(isDeleted,"Error occured in CreditService/deleteCreditTest method. Entity not deleted");

        boolean isUserExists = creditService.existsById(dto.getId());
        Assertions.assertFalse(isUserExists,"Error occured in CreditService/deleteCreditTest method. Entity could not be deleted");

    }

}
