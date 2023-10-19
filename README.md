# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)



### Install Node.js
 - sudo apt update
 - sudo apt install curl git
 - curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
 - sudo apt-get install -y nodejs

### Setup the Hardhat project using yarn
 - yarn init
 - yarn add --dev hardhat
 - npx hardhat init
 - npw hardhat compile

# Install Solc
 - yarn add solc

### Solidity scan using MythX

### Install Web3j
 - curl -L get.web3j.io | sh && source ~/.web3j/source.sh

### Compilation
1) npx hardhat compile : For Unit tests
2) solc ./solidity/contracts/*.sol --bin --abi --optimize -o ./solidity/build/ --overwrite
3) Web3j wrapping :

./web3j/bin/web3j generate solidity \
-a=./solidity/build/P2PTransaction.abi \
-b=./solidity/build/P2PTransaction.bin \
-o=./src/main/java/ \
-p=com.yferhaoui.bleapp2ptransactions.wrappers

### Run Unit tests
 -  npx hardhat test

### Deploy smart contract
 - npx hardhat run scripts/deployProxy.ts --network sepolia
 - npx hardhat verify --network sepolia 0xbda1d4e29689fb935bab590fd6cd7afc8de20a73
 - npx hardhat run scripts/transferProxyOwnership.ts --network sepolia

### Owner : https://sepolia.etherscan.io/address/0xeD210377de31a819B691D37e6093c02f9f15E76f
### Creator : https://sepolia.etherscan.io/address/0xB437eCAB194142887F5ABb95f0F1a3fE37a398CC
### ProxyAdmin : https://sepolia.etherscan.io/address/0x7ba0cb5a61869e2e51c852befc94995c6176948a#code
### P2PTransaction V1 : https://sepolia.etherscan.io/address/0x5bba2b5dd88ac4e852966c62bc838119832cf1f6#code
### P2PTransaction Proxy : https://sepolia.etherscan.io/address/0xbda1d4e29689fb935bab590fd6cd7afc8de20a73#readProxyContract

### KYC Validated from the Spring Boot backend endpoint :
 - https://sepolia.etherscan.io/tx/0x8fa6edcbfad4e386e2a9dcf0a31ec4364aab53563cbd3435b77f96f9a000668e