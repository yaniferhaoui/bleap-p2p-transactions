// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract P2PTransaction is Initializable {
    uint256 public constant TIER_1_FEE = 20; // 0.2%
    uint256 public constant TIER_2_FEE = 15; // 0.15%
    uint256 public constant TIER_3_FEE = 10; // 0.1%

    address public bleapAddress;

    mapping(address => bool) private kycStatus;
    mapping(address => uint256) private balances;

    event Deposit(address indexed user, uint256 amount);
    event Withdrawal(address indexed user, uint256 amount);
    event Transfer(address indexed from, address indexed to, uint256 amount, uint256 fee);

    /*
    * Pass bleapAddress as a constructor parameter to deploy the Smart Contract using a random Hot-Wallet.
    * In that case, the real final owner could be a Cold Wallet
    */
    function initialize(address _bleapAddress) public initializer {
        bleapAddress = _bleapAddress;
        kycStatus[bleapAddress] = true;
    }

    function deposit() external payable { // Tested
        require(kycStatus[msg.sender], "Depositor is not KYC-validated");
        balances[msg.sender] += msg.value;
        emit Deposit(msg.sender, msg.value);
    }

    function withdraw(uint256 amount) external { // Tested
        require(balances[msg.sender] >= amount, "Insufficient balance");
        balances[msg.sender] -= amount;
        payable(msg.sender).transfer(amount);
        emit Withdrawal(msg.sender, amount);
    }

    function transfer(address to, uint256 amount) external { // Tested
        require(to != address(0), "Invalid recipient");
        require(balances[msg.sender] >= amount, "Insufficient balance");
        require(kycStatus[msg.sender], "Sender is not KYC-validated");
        require(kycStatus[to], "Recipient is not KYC-validated");

        uint256 fee;
        if (amount < 1 ether) {
            fee = (amount * TIER_1_FEE) / 10000;
        } else if (amount >= 1 ether && amount <= 5 ether) {
            fee = (amount * TIER_2_FEE) / 10000;
        } else {
            fee = (amount * TIER_3_FEE) / 10000;
        }
        uint256 amountAfterFee = amount - fee;

        balances[msg.sender] -= amount;
        balances[to] += amountAfterFee;
        balances[bleapAddress] += fee;

        emit Transfer(msg.sender, to, amountAfterFee, fee);
    }

    function invalidKyc(address user) external onlyOwner { // Tested
        require(user != address(0), "Invalid user address");
        kycStatus[user] = false;
    }

    function validKyc(address user) external onlyOwner { // Tested
        require(user != address(0), "Invalid user address");
        kycStatus[user] = true;
    }

    modifier onlyOwner() { // Tested
        require(msg.sender == bleapAddress, "Only the owner can call this function");
        _;
    }

    // Read function
    function isKYCValidated(address user) external view returns (bool) { // Tested
        return kycStatus[user];
    }

    function balanceOf(address user) external view returns (uint256) { // Tested
        return balances[user];
    }
}
