package com.daelim.springtest.main.resolver

import com.daelim.springtest.main.api.model.dto.TestDto
import net.datafaker.Faker
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller // @Component 대신 @Controller 사용 권장
class PostResolver {
    private val tests = mutableListOf<TestDto>()
    private val faker = Faker(Locale.KOREA)

    @QueryMapping // 스키마의 Query { findAllTests }와 매핑
    fun findAllTests(): List<TestDto> {
        return tests
    }

    @QueryMapping // 스키마의 Query { findTestById(id: String) }와 매핑
    fun findTestById(@Argument id: String): TestDto? { // 파라미터는 @Argument 필수
        return tests.find { it.id == id }
    }

    // 건우 주소 조회
    @QueryMapping
    fun findTestByAddress(@Argument address: String): TestDto? { // 파라미터는 @Argument 필수
        return tests.find { it.address == address }
    }
    
    // 윤서 전화번호 조회
    @QueryMapping
    fun findTestByTel(@Argument tel: String): TestDto? {
        return tests.find { it.tel == tel }
    }
    
    @MutationMapping // 스키마의 Mutation { createTest(userId: String) }와 매핑
    fun createTest(@Argument userId: String, @Argument userAddress: String, @Argument userTel: String): TestDto {
        val test = TestDto(
            id = userId,
            address = userAddress, //건우
            email = faker.internet().emailAddress(),
            tel = userTel, //윤서
            age = Random().nextInt(100)
      )
      tests.add(test)
      return test

    }
}