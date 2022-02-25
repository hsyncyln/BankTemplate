package com.banktemplate.services;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.domain.entity.CreditEntity;
import com.banktemplate.domain.repository.ICreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CreditService implements ICreditService {

    @Autowired
    private ICreditRepository creditRepository;

    //Create
    @Override
    public CreditDto createCredit(CreditDto dto) {

        dto.setCreditScore(calculateCreditScore());

        boolean isApproved = isCreditApproved(dto.getCreditScore());
        dto.setCreditStatus(isApproved);
        dto.setCreditLimit(isApproved ? calculateCreditLimit(dto.getCreditScore(),dto.getMonthlySalary()) : 0 );

        CreditEntity entity = convertDtoToEntity(dto);
        CreditEntity newEntity = creditRepository.save(entity);
        CreditDto newDto = convertEntityToDto(newEntity);
        return newDto;
    }

    //Update
    @Override
    public CreditDto updateCredit(CreditDto dto) {

        dto.setCreditScore(calculateCreditScore());

        boolean isApproved = isCreditApproved(dto.getCreditScore());
        dto.setCreditStatus(isApproved);
        dto.setCreditLimit(isApproved ? calculateCreditLimit(dto.getCreditScore(),dto.getMonthlySalary()) : 0 );

        CreditEntity entity = convertDtoToEntity(dto);
        CreditEntity newEntity = creditRepository.save(entity);
        CreditDto newDto = convertEntityToDto(newEntity);
        return newDto;
    }

    //Delete
    @Override
    public Boolean deleteCredit(Long id) {
        creditRepository.deleteById(id);
        return true;
    }


    //Read
    public CreditDto getById(Long id){

        Optional<CreditEntity> entity = creditRepository.findById(id);
        CreditDto dto = convertEntityToDto(entity.get());
        return dto;
    }

    //Get CreditDto by IdentityNumber
    public CreditDto getByIdentityNumber(String identityNumber){

        CreditEntity entity = creditRepository.findByIdentityNumber(identityNumber);
        CreditDto dto = convertEntityToDto(entity);
        return dto;
    }

    //Is Identity number exists?
    public boolean existsByIdentityNumber(String identityNumber){
        return creditRepository.existsByIdentityNumber(identityNumber);
    }
    public boolean existsById(Long id){
        return creditRepository.existsById(id);
    }


    //Calculate Methods
    //get Random number (0 - 1500)
    public int calculateCreditScore(){
        Random random = new Random();
        return random.nextInt(1500);
    }


    //Credit Limit Approve Method
    private boolean isCreditApproved(int creditScore){

        if(creditScore < 500)
            return false;
        else
            return true;
    }

    //Credit Limit Calculation Method
    private double calculateCreditLimit(int creditScore, double monthlySalary){

       double creditLimit = monthlySalary * 4;
       if(creditScore >= 500 && creditScore < 1000){

            if(monthlySalary < 5000)
                creditLimit = 10000;
            else
                creditLimit = 20000;
        }

        return creditLimit;
    }


    //Convert Actions
    private CreditDto convertEntityToDto(CreditEntity entity) {
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

    private CreditEntity convertDtoToEntity(CreditDto dto) {
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

}
