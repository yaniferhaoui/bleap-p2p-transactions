package com.yferhaoui.bleapp2ptransactions.services;

import com.yferhaoui.bleapp2ptransactions.config.EthereumConfig;
import com.yferhaoui.bleapp2ptransactions.wrappers.P2PTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class ContractService {

    private final EthereumConfig ethereumConfig;

    @Autowired
    public ContractService(EthereumConfig ethereumConfig) {
        this.ethereumConfig = ethereumConfig;
    }

    public P2PTransaction loadContract() {
        var web3jService = new HttpService(this.ethereumConfig.getRpcEndpoint(), true);
        var web3j = Web3j.build(web3jService);

        var contractGasProvider = new DefaultGasProvider();
        var credentials = Credentials.create(this.ethereumConfig.getOwnerPrivateKey());
        return P2PTransaction.load(ethereumConfig.getP2pContractAddress(), web3j, credentials, contractGasProvider);
    }
}
