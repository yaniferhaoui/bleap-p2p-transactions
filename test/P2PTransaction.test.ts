import { upgrades, ethers } from 'hardhat'
import { expect } from 'chai'

describe('KYC Tests', function () {
  it('Only owner is KYC validated', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const ownerKycValidated = await contract.isKYCValidated(owner.address)
    const user1KycNotValidated = await contract.isKYCValidated(user1.address)
    const user2KycNotValidated = await contract.isKYCValidated(user2.address)

    expect(ownerKycValidated).true
    expect(user1KycNotValidated).false
    expect(user2KycNotValidated).false
  })

  it('Valid KYC User 1', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const ownerKycValidated = await contract.isKYCValidated(owner.address)
    const user1KycNotValidated = await contract.isKYCValidated(user1.address)
    const user2KycNotValidated = await contract.isKYCValidated(user2.address)

    expect(ownerKycValidated).true
    expect(user1KycNotValidated).false
    expect(user2KycNotValidated).false

    await contract.validKyc(user1.address)
    const user1KycValidated = await contract.isKYCValidated(user1.address)
    expect(user1KycValidated).true
    expect(user2KycNotValidated).false
  })

  it('Valid then Invalid KYC User 1', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const ownerKycValidated = await contract.isKYCValidated(owner.address)
    const user1KycNotValidated = await contract.isKYCValidated(user1.address)
    const user2KycNotValidated = await contract.isKYCValidated(user2.address)

    expect(ownerKycValidated).true
    expect(user1KycNotValidated).false
    expect(user2KycNotValidated).false

    await contract.validKyc(user1.address)
    const user1KycValidated = await contract.isKYCValidated(user1.address)
    expect(user1KycValidated).true
    expect(user2KycNotValidated).false

    await contract.invalidKyc(user1.address)
    const user1KycInvalided = await contract.isKYCValidated(user1.address)
    expect(user1KycInvalided).false
  })

  it('Only owner modifier works on validKyc', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    // Attempt to execute validKyc as user1 and capture the exception
    let errorType1 = ''
    let errorMessage1 = ''
    try {
      await contract.connect(user1).validKyc(user1.address)
    } catch (error: any) {
      errorType1 = error.constructor.name
      errorMessage1 = error.message
    }
    expect(errorType1).to.be.eq('SolidityError')
    expect(errorMessage1).to.includes('Only the owner can call this function')

    // Attempt to execute validKyc as user2 and capture the exception
    let errorType2 = ''
    let errorMessage2 = ''
    try {
      await contract.connect(user2).validKyc(user2.address)
    } catch (error: any) {
      errorType2 = error.constructor.name
      errorMessage2 = error.message
    }
    expect(errorType2).to.be.eq('SolidityError')
    expect(errorMessage2).to.includes('Only the owner can call this function')
  })
})

describe('Deposit Tests', function () {
  it('Fail deposit before KYC, then do the KYC and succeed the deposit', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    // Attempt to execute validKyc as user1 and capture the exception
    let errorName1 = ''
    try {
      await contract.connect(user1).deposit({ value: 1 })
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('SolidityError')

    const user1BalanceBeforeDeposit = await contract.balanceOf(user1.address)
    expect(user1BalanceBeforeDeposit.toString()).to.eq(0n.toString())

    await contract.validKyc(user1.address)
    await contract.connect(user1).deposit({ value: 1 })
    const user1BalanceAfterDeposit = await contract.balanceOf(user1.address)
    expect(user1BalanceAfterDeposit.toString()).to.eq(1n.toString())
  })

  it('Not enough funds for deposit', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)

    // Check the initial balance of the user's wallet
    const initialBalance = await ethers.provider.getBalance(user1.address)

    // Calculate the amount for the deposit (exceeding the wallet balance)
    const depositAmount = initialBalance + ethers.parseEther('1') // Adding 1

    // Attempt to execute the deposit and capture the exception
    let errorName1 = ''
    try {
      await contract.connect(user1).deposit({ value: depositAmount })
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('InvalidInputError') // Not enough funds to the Tx value

    const user1Balance = await contract.balanceOf(user1.address)
    expect(user1Balance.toString()).to.eq(0n.toString())
  })
})

describe('Withdrawal Tests', function () {
  it('Deposit then do 2 Withdrawals and fail on the third Withdrawal because empty balance', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const tenEth = ethers.parseEther('10')
    await contract.validKyc(user1.address)
    await contract.connect(user1).deposit({ value: tenEth })

    const user1BalanceAfterDeposit = await contract.balanceOf(user1.address)
    expect(user1BalanceAfterDeposit.toString()).to.eq(tenEth.toString())

    const fiveEth = ethers.parseEther('5')
    await contract.connect(user1).withdraw(fiveEth)
    const user1BalanceAfterFirstWithdrawal = await contract.balanceOf(user1.address)
    expect(user1BalanceAfterFirstWithdrawal.toString()).to.eq(fiveEth)

    await contract.connect(user1).withdraw(fiveEth)
    const user1BalanceAfterSecondWithdrawal = await contract.balanceOf(user1.address)
    expect(user1BalanceAfterSecondWithdrawal.toString()).to.eq(0n.toString())

    // Now balance should be empty
    let errorName1 = ''
    try {
      await contract.connect(user1).withdraw(1)
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('SolidityError') // Balance is empty
  })
})

describe('Transfer Tests', function () {
  it('Fail transfer because depositor KYC invalided', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user1).deposit({ value: 1 })
    await contract.invalidKyc(user1.address)

    // Attempt to execute the transfer and fail because depositor KYC invalided after deposit
    let errorName1 = ''
    try {
      await contract.connect(user1).transfer(user2.address, 1)
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('SolidityError')
  })

  it('Fail transfer because Recipient KYC invalided', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user1).deposit({ value: 1 })
    await contract.invalidKyc(user2.address)

    // Attempt to execute the transfer and fail because recipient KYC invalided after deposit
    let errorName1 = ''
    try {
      await contract.connect(user1).transfer(user2.address, 1)
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('SolidityError')
  })

  it('Fail transfer because Insufficient balance', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user1).deposit({ value: 1 })

    // Attempt to execute the transfer and fail because recipient KYC invalided after deposit
    let errorName1 = ''
    try {
      await contract.connect(user1).transfer(user2.address, 10)
    } catch (error: any) {
      errorName1 = error.constructor.name
    }
    expect(errorName1).to.be.eq('SolidityError')
  })

  it('Transfer Fee Tier 1', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user1).deposit({ value: ethers.parseEther('1') })

    const halfEth = ethers.parseEther('0.5')
    await contract.connect(user1).transfer(user2.address, halfEth)

    const user1Balance = await contract.balanceOf(user1.address)
    const user2Balance = await contract.balanceOf(user2.address)

    const fee = (halfEth * 20n) / 10000n // 0.2 %
    const amountMinusFees = halfEth - fee
    expect(user1Balance.toString()).to.eq(halfEth)
    expect(user2Balance.toString()).to.eq(amountMinusFees.toString())
  })

  it('Transfer Fee Tier 2', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user2).deposit({ value: ethers.parseEther('2') })

    const oneEth = ethers.parseEther('1')
    await contract.connect(user2).transfer(user1.address, oneEth)

    const user2Balance = await contract.balanceOf(user2.address)
    const user1Balance = await contract.balanceOf(user1.address)

    const fee = (oneEth * 15n) / 10000n // 0.15 %
    const amountMinusFees = oneEth - fee
    expect(user2Balance.toString()).to.eq(oneEth)
    expect(user1Balance.toString()).to.eq(amountMinusFees.toString())
  })

  it('Transfer Fee Tier 3', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.connect(user2).deposit({ value: ethers.parseEther('16') })

    const sixEth = ethers.parseEther('6')
    const tenEth = ethers.parseEther('10')
    await contract.connect(user2).transfer(user1.address, sixEth)

    const user2Balance = await contract.balanceOf(user2.address)
    const user1Balance = await contract.balanceOf(user1.address)

    const fee = (sixEth * 10n) / 10000n // 0.1 %
    const amountMinusFees = sixEth - fee
    expect(user2Balance.toString()).to.eq(tenEth)
    expect(user1Balance.toString()).to.eq(amountMinusFees.toString())
  })

  it('Self Transfer Accepted', async () => {
    const [owner, user1, user2] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const tenEth = ethers.parseEther('10')
    await contract.validKyc(user1.address)
    await contract.connect(user1).deposit({ value: tenEth })

    await contract.connect(user1).transfer(user1.address, tenEth)
    const user1Balance = await contract.balanceOf(user1.address)

    const fee = (tenEth * 10n) / 10000n // 0.1 %
    const amountMinusFees = tenEth - fee
    expect(user1Balance.toString()).to.eq(amountMinusFees.toString())
  })
})

describe('End-to-End Tests', function () {
  it('User 1 deposit, transfer to User2, User2 transfer to User3 then User3 withdraw everything', async () => {
    const [owner, user1, user2, user3] = await ethers.getSigners()
    const v1ContractFactory = await ethers.getContractFactory('P2PTransaction')
    const contract = await upgrades.deployProxy(v1ContractFactory, [owner.address])

    const fiveEth = ethers.parseEther('5')
    await contract.validKyc(user1.address)
    await contract.validKyc(user2.address)
    await contract.validKyc(user3.address)
    await contract.connect(user1).deposit({ value: ethers.parseEther('10') })

    await contract.connect(user1).transfer(user2.address, fiveEth)
    const user1BalanceAfterFirstTransfer = await contract.balanceOf(user1.address)
    const user2BalanceAfterFirstTransfer = await contract.balanceOf(user2.address)
    const feeOfFirstTransfer = (fiveEth * 15n) / 10000n // 0.1 %
    const finalAmountOfFirstTransfer = fiveEth - feeOfFirstTransfer
    expect(user1BalanceAfterFirstTransfer.toString()).to.eq(fiveEth)
    expect(user2BalanceAfterFirstTransfer.toString()).to.eq(finalAmountOfFirstTransfer.toString())

    await contract.connect(user2).transfer(user3.address, finalAmountOfFirstTransfer)

    const feeOfSecondTransfer = (finalAmountOfFirstTransfer * 15n) / 10000n // 0.1 %
    const finalAmountOfSecondTransfer = finalAmountOfFirstTransfer - feeOfSecondTransfer
    await contract.connect(user3).withdraw(finalAmountOfSecondTransfer)

    const finalBalanceUser2 = await contract.balanceOf(user2.address)
    const finalBalanceUser3 = await contract.balanceOf(user3.address)
    expect(finalBalanceUser2.toString()).to.eq(0n.toString())
    expect(finalBalanceUser3.toString()).to.eq(0n.toString())
  })
})
