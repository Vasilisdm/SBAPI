package com.vgdm.bankapi.datasource.mock

import com.vgdm.bankapi.datasource.BankDataSource
import com.vgdm.bankapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    private val banks = mutableListOf(
        Bank("1234", 3.15, 2),
        Bank("4567", 0.0, 2),
        Bank("7890", 15.5, 0),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number: $accountNumber")

    override fun storeBank(newBank: Bank): Bank {
        if (banks.any { it.accountNumber == newBank.accountNumber }) {
            throw IllegalArgumentException("Bank with account id ${newBank.accountNumber} already exists.")
        }
        banks.add(newBank)
        return newBank
    }

    override fun updateBank(updatedBank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == updatedBank.accountNumber }
            ?: throw NoSuchElementException("Bank with account id ${updatedBank.accountNumber} doesn't exist.")

        banks.remove(currentBank)
        banks.add(updatedBank)

        return updatedBank
    }
}