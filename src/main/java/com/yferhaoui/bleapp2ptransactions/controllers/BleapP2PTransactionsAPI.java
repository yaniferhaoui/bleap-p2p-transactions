package com.yferhaoui.bleapp2ptransactions.controllers;

import com.yferhaoui.bleapp2ptransactions.models.UserAddress;
import com.yferhaoui.bleapp2ptransactions.services.KycService;
import com.yferhaoui.bleapp2ptransactions.services.UserBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BleapP2PTransactionsAPI {

    private final KycService kycService;
    private final UserBalanceService userBalanceService;

    @Autowired
    public BleapP2PTransactionsAPI(KycService kycService, UserBalanceService userBalanceService) {
        this.kycService = kycService;
        this.userBalanceService = userBalanceService;
    }

    @GetMapping("/users/{userAddress}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String userAddress) {
        try {
            var userBalance = this.userBalanceService.getUserBalance(userAddress);
            log.info("User balance {}: {}", userAddress, userBalance);
            return ResponseEntity.ok(userBalance);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("/valid-kyc")
    public ResponseEntity<String> validUserKyc(@RequestBody UserAddress user) {
        try {
            var txReceipt = this.kycService.validUserKyc(user);
            log.info("Valid KYC of {}: {}", user.getAddress(), txReceipt);
            return ResponseEntity.ok(txReceipt);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("/invalid-kyc")
    public ResponseEntity<String> invalidUserKyc(@RequestBody UserAddress user) {
        try {
            var txReceipt = this.kycService.invalidUserKyc(user);
            log.info("Invalid KYC of {}: {}", user.getAddress(), txReceipt);
            return ResponseEntity.ok(txReceipt);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}