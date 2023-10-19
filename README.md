# bleap-p2p-transactions

Small PoC of a P2P transaction system using a proxied smart contract for Upgradability with a Java Spring Boot backend to interact with KYC RPC methods and read methods

## Smart Contract Creation
### Install Node.js
 - sudo apt update
 - sudo apt install curl git
 - curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
 - sudo apt-get install -y nodejs

### Setup the Hardhat project using yarn
 - yarn init
 - yarn add --dev hardhat
 - npx hardhat init
 - touch ./solidity/contracts/P2PTransaction.sol
 - npw hardhat compile

### Solidity scan using Mythril : https://github.com/Consensys/mythril
- pip3 install mythril
- myth analyze ./solidity/contracts/P2PTransaction.sol --solc-json ./remappings.json

## TS Unit Tests
### Hardhat Node.js (TypeScript) scripts
 - touch ./test/P2PTransaction.test.ts
 - npx hardhat test

## Java Smart Contract Wrapping
### Install Solc
 - yarn add solc
 - solc ./solidity/contracts/*.sol --bin --abi --optimize -o ./solidity/build/ --overwrite --base-path '/' --include-path 'node_modules/'

### Install Web3j
 - curl -L get.web3j.io | sh && source ~/.web3j/source.sh
 - Web3 wrapping :

```
./web3j/bin/web3j generate solidity \
   -a=./solidity/build/P2PTransaction.abi \
   -b=./solidity/build/P2PTransaction.bin \
   -o=./src/main/java/ \
   -p=com.yferhaoui.bleapp2ptransactions.wrappers
```

## Sepolia Testnet deployment
### Env configuration
 - Rename ./.env.example to ./.env
 - Fill the global variables of this file
### Deploy on Sepolia Testnet
 - npx hardhat run scripts/deployProxy.ts --network sepolia
### Upload smart contract code verification on Etherscan
 - npx hardhat verify --network sepolia 0xbda1d4e29689fb935bab590fd6cd7afc8de20a73
### Give the Proxy ownership to another address (Maybe a cold wallet in production)
 - npx hardhat run scripts/transferProxyOwnership.ts --network sepolia

### Deployed Smart Contracts on Sepolia Testnet
 - Owner : https://sepolia.etherscan.io/address/0xeD210377de31a819B691D37e6093c02f9f15E76f
 - Creator : https://sepolia.etherscan.io/address/0xB437eCAB194142887F5ABb95f0F1a3fE37a398CC
 - ProxyAdmin : https://sepolia.etherscan.io/address/0x7ba0cb5a61869e2e51c852befc94995c6176948a#code
 - P2PTransaction V1 : https://sepolia.etherscan.io/address/0x5bba2b5dd88ac4e852966c62bc838119832cf1f6#code
 - P2PTransaction Proxy : https://sepolia.etherscan.io/address/0xbda1d4e29689fb935bab590fd6cd7afc8de20a73#readProxyContract
 - Tx of a user KYC validated using the Java backend : https://sepolia.etherscan.io/tx/0x8fa6edcbfad4e386e2a9dcf0a31ec4364aab53563cbd3435b77f96f9a000668e

## Java application launch 
### Env.yml configuration
 - Rename ./.env.example.yml to ./.env.yml
 - Fill the global variables of this file

### Maven start (Let's keep it simple here)
 - mvn spring-boot:run

### Endpoints
 - Get User Balance : http://localhost:9500/users/{userAddress}/balance
 - Post valid-kyc : http://localhost:9500/valid-kyc
 - Post invalid-kyc : http://localhost:9500/invalid-kyc

## Future Improvements
 - More SC features and endpoints
 - Separate the Smart contract Owner (Cold Wallet) and the KYC wallet manager (Hot Wallet)
 - Creating a frontend to interact with SC deposit / withdraw / transfer RPC methods
 - Try smart contract attacks in hardhat Tests
 - Setup TLS communication
 - Improve the security authentication system
 - Adding Register / Login authentication mechanisme
 - Use env profile (prod, local, dev, uat)
 - Doing external smart contract audits
 - Maybe more that I can't think of right now :)

### Thanks for Review !
