package com.yferhaoui.bleapp2ptransactions.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ethereum")
public class EthereumConfig {

    private String ownerPrivateKey;
    private String rpcEndpoint;
    private String p2pContractAddress;
}
