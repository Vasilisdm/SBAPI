package com.vgdm.bankapi.datasource

import com.vgdm.bankapi.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
}