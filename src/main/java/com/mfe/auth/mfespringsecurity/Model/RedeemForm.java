package com.mfe.auth.mfespringsecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedeemForm {
    private String modeOfRedemption;
    private Double amount;
    private String from;
    private String redemptionOptions;
    private String reasonOfRedemption;
    private Boolean isAgree;
}
