import { HardhatUserConfig } from 'hardhat/config'
import '@nomicfoundation/hardhat-toolbox'
import '@openzeppelin/hardhat-upgrades'
import '@typechain/hardhat'
import { configDotenv } from 'dotenv'

// Load configuration
configDotenv()

const config: HardhatUserConfig = {
  paths: {
    root: './',
    tests: './test',
    cache: './solidity/cache',
    sources: './solidity/',
    artifacts: './solidity/artifacts',
  },
  solidity: {
    compilers: [
      {
        version: '0.8.20',
        settings: {
          optimizer: {
            enabled: true,
            runs: 999999,
          },
          viaIR: true,
        },
      },
    ],
  },
  networks: {
    sepolia: {
      url: process.env.SEPOLIA_RPC_ENDPOINT,
      accounts: {
        mnemonic: process.env.SEPOLIA_URL_MNEMONIC,
      },
    },
  },
  etherscan: {
    apiKey: process.env.ETHERSCAN_KEY,
  },
}

export default config
