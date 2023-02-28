package com.vgdm.bankapi.datasource.network

import com.vgdm.bankapi.datasource.BankDataSource
import com.vgdm.bankapi.datasource.network.DTO.BankList
import com.vgdm.bankapi.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        val banksResponse = restTemplate.getForEntity<BankList>("http://54.193.31.159/banks")

        return banksResponse.body?.result
            ?: throw IOException("Could not fetch banks from the network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun storeBank(newBank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(updatedBank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}