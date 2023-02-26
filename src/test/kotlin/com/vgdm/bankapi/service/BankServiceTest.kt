package com.vgdm.bankapi.service

import com.vgdm.bankapi.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class BankServiceTest {

    private val bankDataSource = mockk<BankDataSource>(relaxed = true)
    private val bankService = BankService(dataSource = bankDataSource)

    @Test
    fun `should call the data source to retrieve banks`() {
        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { bankDataSource.retrieveBanks() }
    }
}