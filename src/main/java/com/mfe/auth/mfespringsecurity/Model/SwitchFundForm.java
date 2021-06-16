package com.mfe.auth.mfespringsecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SwitchFundForm {
    private String modeOfTransfer;
    private String depositAmount;
    private String depositFrom;
    private Boolean isTransferForSpecificAppNo;
    private String depositTo;
    private Long totalAmount;
    private Boolean termsAgreement;
}
