package com.mfe.auth.mfespringsecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchFundForm {
    private String modeOfTransfer;
    private String depositAmount;
    private String depositFrom;
    private Boolean isTransferForSpecificAppNo;
    private String depositTo;
    private String totalAmount;
    private Boolean termsAgreement;
}
