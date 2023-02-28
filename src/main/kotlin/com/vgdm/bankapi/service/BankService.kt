package com.vgdm.bankapi.service

import com.vgdm.bankapi.datasource.BankDataSource
import com.vgdm.bankapi.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("network") private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(newBank: Bank): Bank = dataSource.storeBank(newBank)

    fun updateBank(updatedBank: Bank): Bank = dataSource.updateBank(updatedBank)

    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)
}