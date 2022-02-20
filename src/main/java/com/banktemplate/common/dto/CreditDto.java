package com.banktemplate.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDto implements Serializable {

    private Long id;

    @NotEmpty(message = "Ad alanını boş geçemezsiniz")
    private String name;

    @NotEmpty(message = "Soyad alanını boş geçemezsiniz")
    private String surname;

    @NotEmpty(message = "TC Kimlik Numarası alanını boş geçemezsiniz")
    @Size(max = 11)
    private String identityNumber;

    @NotEmpty(message = "Telefon Numarası alanını boş geçemezsiniz")
    private String phoneNumber;

//    @NotEmpty(message = "Aylık gelir alanını boş geçemezsiniz")
    @Min(value = 0)
    private double monthlySalary;

    private int creditScore;
    private double creditLimit;
    private boolean creditStatus;


}
