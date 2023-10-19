import { ethers, upgrades } from 'hardhat'

async function main() {
  if (process.env.PROXY_OWNER_ADDRESS === undefined || process.env.PROXY_OWNER_ADDRESS === '')
    throw new Error('PROXY_OWNER_ADDRESS not defined!')

  const P2PTransaction = await ethers.getContractFactory('P2PTransaction')
  const proxy = await upgrades.deployProxy(P2PTransaction, [process.env.PROXY_OWNER_ADDRESS])

  console.log(`Proxy address: ${proxy.address}`)
}

main()
