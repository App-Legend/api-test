package com.daelim.springtest.main.controller

import com.daelim.springtest.main.api.model.dto.TestDto
import com.daelim.springtest.main.api.model.dto.TestDtoRequest
import net.datafaker.Faker
import net.datafaker.providers.base.Address
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Locale
import kotlin.random.Random

@RestController
@RequestMapping("/api")
class Controller {

    private val tests = mutableListOf<TestDto>()
    private val faker = Faker(Locale.KOREA)

    @PostMapping("/test")
    fun postTestDto(
        @RequestBody testDtoRequest: TestDtoRequest
    ): ResponseEntity<TestDto> {
        val test = TestDto(
            id = testDtoRequest.id,
            address = testDtoRequest.address,
            email = testDtoRequest.email,
            tel = faker.phoneNumber().phoneNumber(),
            age = Random.nextInt(1, 101)
        )
        tests.add(test)
        return ResponseEntity.ok(test)
    }

    @GetMapping("/test")
    fun getAllTestDto(): ResponseEntity<List<TestDto>> {
        return ResponseEntity.ok(tests)
    }

    @GetMapping("/test/{id}")
    fun getTestDto(
        @PathVariable("id") userId: String
    ): ResponseEntity<TestDto> {
        val response = tests.firstOrNull { it.id == userId }

        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/test/{address}")
    fun getTestDtoAddress(
        @PathVariable("address") userAddress: String
    ): ResponseEntity<List<TestDto>> {
        tests.firstOrNull{it.address == userAddress}
        return ResponseEntity.ok(tests)
    }

    @DeleteMapping("/test/{id}")
    fun deleteTestDto(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        val removed = tests.removeIf { it.id == id }

        return if (removed) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/test/{email}")
    fun getTestDtoByEmail(
        @RequestParam email: String
    ): ResponseEntity<TestDto> {

        val response = tests.firstOrNull { it.email == email }

        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }


}