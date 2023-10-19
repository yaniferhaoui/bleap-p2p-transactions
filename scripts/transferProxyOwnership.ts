import { ethers, upgrades } from 'hardhat'
async function main() {
  if (process.env.PROXY_OWNER_ADDRESS === undefined || process.env.PROXY_OWNER_ADDRESS === '')
    throw new Error('PROXY_OWNER_ADDRESS not defined!')

  console.log('Transferring ownership of ProxyAdmin...')
  // The owner of the ProxyAdmin can upgrade our contracts
  await upgrades.admin.transferProxyAdminOwnership(process.env.PROXY_OWNER_ADDRESS)
  console.log('Transferred ownership of ProxyAdmin to:', process.env.PROXY_OWNER_ADDRESS)
}

main()
