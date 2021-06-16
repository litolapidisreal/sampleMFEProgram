package com.mfe.auth.mfespringsecurity.Model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
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
