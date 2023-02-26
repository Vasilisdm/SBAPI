package com.vgdm.bankapi.datasource.mock

import com.vgdm.bankapi.datasource.BankDataSource
import com.vgdm.bankapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    private val banks = listOf(
        Bank("1234", 3.15, 2),
        Bank("4567", 0.0, 2),
        Bank("7890", 15.5, 0),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

}