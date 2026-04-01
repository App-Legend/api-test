package com.daelim.springtest.main.controller

import com.daelim.springtest.main.api.model.dto.TestDto
import com.daelim.springtest.main.api.model.dto.TestDtoRequest
import net.datafaker.Faker
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
            address = faker.address().fullAddress(),
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

    @GetMapping("/test/tel")
    fun getTestDtoByTel(
        @RequestParam tel: String
    ): ResponseEntity<TestDto> {

        val response = tests.firstOrNull { it.tel == tel }

        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}