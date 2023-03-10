package com.vgdm.bankapi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MockBankDataSourceTest {

    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun `getBanks should provide a collection of banks`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        assertThat(banks).isNotEmpty
    }

    @Test
    fun `getBanks should provide some mock data`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }
}