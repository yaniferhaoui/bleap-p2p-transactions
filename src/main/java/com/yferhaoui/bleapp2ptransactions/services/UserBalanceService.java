package com.yferhaoui.bleapp2ptransactions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    private final ContractService contractService;

    @Autowired
    public UserBalanceService(ContractService contractService) {
        this.contractService = contractService;
    }

    public String getUserBalance(String userAddress) throws Exception {
        return this.contractService
                .loadContract()
                .balanceOf(userAddress)
                .send()
                .toString();
    }
}
