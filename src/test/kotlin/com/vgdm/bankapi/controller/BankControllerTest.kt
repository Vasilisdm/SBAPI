package com.vgdm.bankapi.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    lateinit var mockMVC: MockMvc

    @Test
    fun `should return all banks`() {
        // when/then
        mockMVC.get("/api/banks")
            .also { println() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") { value("1234") }
            }
    }

    @Test
    fun `should return a bank with given account number`() {
        // when/then
        mockMVC.get("/api/banks/1234")
            .also { println() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("1234") }
            }
    }
}