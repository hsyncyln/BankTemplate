package com.banktemplate.domain.repository;

import com.banktemplate.domain.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditRepository extends JpaRepository<CreditEntity, Long> {
    CreditEntity findByIdentityNumber(String identityNumber);
    boolean existsByIdentityNumber(String identityNumber);
}
