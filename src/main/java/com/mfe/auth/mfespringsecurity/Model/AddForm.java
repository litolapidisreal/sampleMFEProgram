package com.mfe.auth.mfespringsecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddForm {
    private String bankName;
    private String depositAmount;
    private Date dateOfDeposit;
    private String fundType;
    private Long salesLoad;
    private Long investmentAmount;
    private Long sourceOfFunds;
    private Boolean termsAgreement;

}
