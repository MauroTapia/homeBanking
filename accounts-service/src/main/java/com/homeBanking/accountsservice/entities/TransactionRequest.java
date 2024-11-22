package com.homeBanking.accountsservice.entities;

import lombok.Data;

@Data
public class TransactionRequest {
    private String destinyAccount;
    private Double amount;
}
