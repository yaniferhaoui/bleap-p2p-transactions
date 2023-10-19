package com.yferhaoui.bleapp2ptransactions.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class P2PTransaction extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50610a768061001d5f395ff3fe6080604052600436106100a5575f3560e01c8063a43768b511610062578063a43768b51461018e578063a9059cbb146101a2578063c4d66de8146101c1578063d0e30db0146101e0578063daa34330146101e8578063e72f2be61461022f575f80fd5b80632e1a7d4d146100a95780634b0f7c7f146100ca578063701b09a0146100e957806370a08231146101105780637c33d3351461014457806388093b1214610158575b5f80fd5b3480156100b4575f80fd5b506100c86100c336600461090b565b61024e565b005b3480156100d5575f80fd5b506100c86100e436600461093d565b61032e565b3480156100f4575f80fd5b506100fd601481565b6040519081526020015b60405180910390f35b34801561011b575f80fd5b506100fd61012a36600461093d565b6001600160a01b03165f9081526002602052604090205490565b34801561014f575f80fd5b506100fd600a81565b348015610163575f80fd5b505f54610176906001600160a01b031681565b6040516001600160a01b039091168152602001610107565b348015610199575f80fd5b506100fd600f81565b3480156101ad575f80fd5b506100c86101bc36600461095d565b6103ca565b3480156101cc575f80fd5b506100c86101db36600461093d565b610683565b6100c86107bd565b3480156101f3575f80fd5b5061021f61020236600461093d565b6001600160a01b03165f9081526001602052604090205460ff1690565b6040519015158152602001610107565b34801561023a575f80fd5b506100c861024936600461093d565b610875565b335f908152600260205260409020548111156102a85760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b60448201526064015b60405180910390fd5b335f90815260026020526040812080548392906102c6908490610999565b9091555050604051339082156108fc029083905f818181858888f193505050501580156102f5573d5f803e3d5ffd5b5060405181815233907f7fcf532c15f0a6db0bd6d0e038bea71d30d808c7d98cb3bf7268a95bf5081b659060200160405180910390a250565b5f546001600160a01b031633146103575760405162461bcd60e51b815260040161029f906109b2565b6001600160a01b0381166103a45760405162461bcd60e51b8152602060048201526014602482015273496e76616c69642075736572206164647265737360601b604482015260640161029f565b6001600160a01b03165f908152600160208190526040909120805460ff19169091179055565b6001600160a01b0382166104145760405162461bcd60e51b8152602060048201526011602482015270125b9d985b1a59081c9958da5c1a595b9d607a1b604482015260640161029f565b335f908152600260205260409020548111156104695760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b604482015260640161029f565b335f9081526001602052604090205460ff166104c75760405162461bcd60e51b815260206004820152601b60248201527f53656e646572206973206e6f74204b59432d76616c6964617465640000000000604482015260640161029f565b6001600160a01b0382165f9081526001602052604090205460ff1661052e5760405162461bcd60e51b815260206004820152601e60248201527f526563697069656e74206973206e6f74204b59432d76616c6964617465640000604482015260640161029f565b5f670de0b6b3a764000082101561055e5761271061054d6014846109f7565b6105579190610a0e565b90506105ac565b670de0b6b3a7640000821015801561057e5750674563918244f400008211155b156105915761271061054d600f846109f7565b61271061059f600a846109f7565b6105a99190610a0e565b90505b5f6105b78284610999565b335f908152600260205260408120805492935085929091906105da908490610999565b90915550506001600160a01b0384165f9081526002602052604081208054839290610606908490610a2d565b90915550505f80546001600160a01b031681526002602052604081208054849290610632908490610a2d565b909155505060408051828152602081018490526001600160a01b0386169133917f9ed053bb818ff08b8353cd46f78db1f0799f31c9e4458fdb425c10eccd2efc44910160405180910390a350505050565b7ff0c57e16840df040f15088dc2f81fe391c3923bec73e23a9662efc9c229c6a008054600160401b810460ff16159067ffffffffffffffff165f811580156106c85750825b90505f8267ffffffffffffffff1660011480156106e45750303b155b9050811580156106f2575080155b156107105760405163f92ee8a960e01b815260040160405180910390fd5b845467ffffffffffffffff19166001178555831561073a57845460ff60401b1916600160401b1785555b5f80546001600160a01b0319166001600160a01b03881690811782558152600160208190526040909120805460ff1916909117905583156107b557845460ff60401b19168555604051600181527fc7f505b2f371ae2175ee4913f4499e1f2633a7b5936321eed1cdaeb6115181d29060200160405180910390a15b505050505050565b335f9081526001602052604090205460ff1661081b5760405162461bcd60e51b815260206004820152601e60248201527f4465706f7369746f72206973206e6f74204b59432d76616c6964617465640000604482015260640161029f565b335f9081526002602052604081208054349290610839908490610a2d565b909155505060405134815233907fe1fffcc4923d04b559f4d29a8bfc6cda04eb5b0d3c460751c2402c5c5cc9109c9060200160405180910390a2565b5f546001600160a01b0316331461089e5760405162461bcd60e51b815260040161029f906109b2565b6001600160a01b0381166108eb5760405162461bcd60e51b8152602060048201526014602482015273496e76616c69642075736572206164647265737360601b604482015260640161029f565b6001600160a01b03165f908152600160205260409020805460ff19169055565b5f6020828403121561091b575f80fd5b5035919050565b80356001600160a01b0381168114610938575f80fd5b919050565b5f6020828403121561094d575f80fd5b61095682610922565b9392505050565b5f806040838503121561096e575f80fd5b61097783610922565b946020939093013593505050565b634e487b7160e01b5f52601160045260245ffd5b818103818111156109ac576109ac610985565b92915050565b60208082526025908201527f4f6e6c7920746865206f776e65722063616e2063616c6c20746869732066756e60408201526431ba34b7b760d91b606082015260800190565b80820281158282048414176109ac576109ac610985565b5f82610a2857634e487b7160e01b5f52601260045260245ffd5b500490565b808201808211156109ac576109ac61098556fea2646970667358221220454964650fbb00c3d7ac5b59c20a14741fb9de719db7e2c2e524fb2405edb50664736f6c63430008150033";

    public static final String FUNC_TIER_1_FEE = "TIER_1_FEE";

    public static final String FUNC_TIER_2_FEE = "TIER_2_FEE";

    public static final String FUNC_TIER_3_FEE = "TIER_3_FEE";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BLEAPADDRESS = "bleapAddress";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_INVALIDKYC = "invalidKyc";

    public static final String FUNC_ISKYCVALIDATED = "isKYCValidated";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_VALIDKYC = "validKyc";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event INITIALIZED_EVENT = new Event("Initialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWAL_EVENT = new Event("Withdrawal", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected P2PTransaction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected P2PTransaction(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected P2PTransaction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected P2PTransaction(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DepositEventResponse getDepositEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEPOSIT_EVENT, log);
        DepositEventResponse typedResponse = new DepositEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDepositEventFromLog(log));
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public static List<InitializedEventResponse> getInitializedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
        ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializedEventResponse typedResponse = new InitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InitializedEventResponse getInitializedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZED_EVENT, log);
        InitializedEventResponse typedResponse = new InitializedEventResponse();
        typedResponse.log = log;
        typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInitializedEventFromLog(log));
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
        return initializedEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWAL_EVENT, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawalEventResponse getWithdrawalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAWAL_EVENT, log);
        WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawalEventFromLog(log));
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWAL_EVENT));
        return withdrawalEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> TIER_1_FEE() {
        final Function function = new Function(FUNC_TIER_1_FEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> TIER_2_FEE() {
        final Function function = new Function(FUNC_TIER_2_FEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> TIER_3_FEE() {
        final Function function = new Function(FUNC_TIER_3_FEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String user) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> bleapAddress() {
        final Function function = new Function(FUNC_BLEAPADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _bleapAddress) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _bleapAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> invalidKyc(String user) {
        final Function function = new Function(
                FUNC_INVALIDKYC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isKYCValidated(String user) {
        final Function function = new Function(FUNC_ISKYCVALIDATED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> validKyc(String user) {
        final Function function = new Function(
                FUNC_VALIDKYC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static P2PTransaction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new P2PTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static P2PTransaction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new P2PTransaction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static P2PTransaction load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new P2PTransaction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static P2PTransaction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new P2PTransaction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<P2PTransaction> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(P2PTransaction.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<P2PTransaction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(P2PTransaction.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<P2PTransaction> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(P2PTransaction.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<P2PTransaction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(P2PTransaction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DepositEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }

    public static class InitializedEventResponse extends BaseEventResponse {
        public BigInteger version;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;

        public BigInteger fee;
    }

    public static class WithdrawalEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }
}
