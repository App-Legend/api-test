package com.daelim.springtest.main.resolver

import com.daelim.springtest.main.api.model.dto.TestDto
import net.datafaker.Faker
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.Locale
import kotlin.random.Random

@Controller
class PostResolver {

    private val tests = mutableListOf<TestDto>()
    private val faker = Faker(Locale.KOREA)

    @QueryMapping
    fun findAllTests(): List<TestDto> {
        return tests
    }

    @QueryMapping
    fun findTestById(@Argument id: String): TestDto? {
        return tests.find { it.id == id }
    }

    @QueryMapping
    fun findTestByEmail(@Argument email: String): TestDto? {
        return tests.find { it.email == email }
    }

    @MutationMapping
    fun createTest(
        @Argument userId: String,
        @Argument userEmail: String
    ): TestDto {
        val test = TestDto(
            id = userId,
            address = faker.address().fullAddress(),
            email = userEmail,
            tel = faker.phoneNumber().phoneNumber(),
            age = Random.nextInt(1, 101)
        )
        tests.add(test)
        return test
    }
}