package com.banktemplate.core;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.domain.entities.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Random;

public interface ICreditRepository extends JpaRepository<CreditEntity,Long> {

    CreditEntity findByIdentityNumber(String identityNumber);
    boolean existsByIdentityNumber(String identityNumber);

    default CreditDto saveCredit(CreditDto dto){
        CreditEntity entity = convertToCreditEntity(dto);
        CreditEntity newEntity = save(entity);
        CreditDto newDto = convertToCreditDto(newEntity);
        return newDto;
    }

    //Credit convert dto to entity method
    private CreditEntity convertToCreditEntity(CreditDto dto){
        CreditEntity entity = CreditEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .identityNumber(dto.getIdentityNumber())
                .phoneNumber(dto.getPhoneNumber())
                .creditScore(dto.getCreditScore())
                .creditLimit(dto.getCreditLimit())
                .monthlySalary(dto.getMonthlySalary())
                .creditStatus(dto.isCreditStatus())
                .build();

        return entity;
    }

    //Credit convert entity to dto method
    private CreditDto convertToCreditDto(CreditEntity entity){
        CreditDto dto = CreditDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .identityNumber(entity.getIdentityNumber())
                .phoneNumber(entity.getPhoneNumber())
                .monthlySalary(entity.getMonthlySalary())
                .creditScore(entity.getCreditScore())
                .creditLimit(entity.getCreditLimit())
                .creditStatus(entity.isCreditStatus())
                .build();
        return dto;
    }


    default CreditDto getCreditDtoEntityByIdentityNumber(String identityNumber){

        CreditEntity entity = findByIdentityNumber(identityNumber);
        CreditDto dto = convertToCreditDto(entity);
        return dto;
    }

    //Credit Limit Calculation Method
    default CreditDto calculateCreditLimit(CreditDto creditDto){

        if(creditDto.getCreditScore() < 500){
            creditDto.setCreditStatus(false);
            creditDto.setCreditLimit(0);
        }
        else if(creditDto.getCreditScore() >= 500 && creditDto.getCreditScore() < 1000){

            if(creditDto.getMonthlySalary() < 5000){
                creditDto.setCreditStatus(true);
                creditDto.setCreditLimit(10000);
            }
            else{
                creditDto.setCreditStatus(true);
                creditDto.setCreditLimit(20000);
            }
        }
        else{
            double creditLimit = creditDto.getMonthlySalary() * 4;
            creditDto.setCreditStatus(true);
            creditDto.setCreditLimit(creditLimit);
        }

        return creditDto;
    }

    //get Random number (0 - 1500)
    default int calculateCreditScore(){
        Random random = new Random();
        return random.nextInt(1500);
    }

}
