package com.vgdm.bankapi.datasource.mock

import com.vgdm.bankapi.datasource.BankDataSource
import com.vgdm.bankapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    override fun getBanks(): Collection<Bank> {
        TODO("Not yet implemented")
    }
}