package com.vgdm.bankapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.vgdm.bankapi.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMVC: MockMvc,
    val objectMapper: ObjectMapper
) {

    private val baseURL = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // when/then
            mockMVC.get(baseURL)
                .also { println() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return a bank with given account number`() {
            // when/then
            mockMVC.get("${baseURL}/1234")
                .also { println() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // when
            val nonExistingAccountNumber = "does_not_exist"

            // then
            mockMVC.get("${baseURL}/$nonExistingAccountNumber")
                .also { println() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // when
            val newBank = Bank("acc123", 37.100, 10)

            // when
            val performPost = mockMVC.post(baseURL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost
                .also {
                    println()
                }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMVC.get("${baseURL}/${newBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
        }

        @Test
        fun `creating bank with the same account should return BAD REQUEST`() {
            // when
            val newBank = Bank("1234", 37.100, 10)

            // when
            val performPost = mockMVC.post(baseURL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost
                .also {
                    println()
                }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("1234", 10.5, 3)

            // when
            val performPatch = mockMVC.patch(baseURL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatch
                .also { println() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMVC.get("$baseURL/${updatedBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if no bank with account number exists`() {
            // given
            val notExistingBankAccountNumber = "does_not_exist"
            val updatedBank = Bank(notExistingBankAccountNumber, 10.5, 3)

            // when
            val performPatch = mockMVC.patch(baseURL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatch
                .also { println() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        @Test
        @DirtiesContext
        fun `should delete an existing bank`() {
            // given
            val accountNumber = "1234"

            // when/then
            mockMVC.delete("${baseURL}/${accountNumber}")
                .also { println() }
                .andExpect {
                    status {
                        isNoContent()
                    }
                }

            mockMVC.get("${baseURL}/${accountNumber}")
                .also { println() }
                .andExpect {
                    status {
                        isNotFound()
                    }
                }
        }

        @Test
        fun `should return NOT FOUND for non existnig bank`() {
            // given
            val notExistingAccountNumber = "does_not_exist"

            // when
            mockMVC.delete("${baseURL}/${notExistingAccountNumber}")
                .also { println() }
                .andExpect {
                    status {
                        isNotFound()
                    }
                }
        }
    }
}