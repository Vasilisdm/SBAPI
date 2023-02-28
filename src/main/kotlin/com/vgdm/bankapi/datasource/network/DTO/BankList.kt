package com.vgdm.bankapi.datasource.network.DTO

import com.vgdm.bankapi.model.Bank

data class BankList(
    val result: List<Bank>
)