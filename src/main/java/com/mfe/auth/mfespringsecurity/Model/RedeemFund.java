package com.mfe.auth.mfespringsecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedeemFund {
    private String modeOfRedemption;
    private Double amount;
    private String from;
    private Boolean isRedeemForSpecificAppNo;
    private String redemptionOptions;
    private String reasonOfRedemption;
    private Boolean isAgree;
}