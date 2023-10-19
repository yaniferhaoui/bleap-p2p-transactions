package com.yferhaoui.bleapp2ptransactions.services;

import com.yferhaoui.bleapp2ptransactions.models.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KycService {

    private final ContractService contractService;

    @Autowired
    public KycService(ContractService contractService) {
        this.contractService = contractService;
    }

    public String validUserKyc(UserAddress user) throws Exception {
        return this.contractService
            .loadContract()
            .validKyc(user.getAddress())
                .send()
                .toString();
    }

    public String invalidUserKyc(UserAddress user) throws Exception {
        return this.contractService
                .loadContract()
                .invalidKyc(user.getAddress())
                .send()
                .toString();
    }
}
