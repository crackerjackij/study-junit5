package com.example.study;

import com.example.domain.Study;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest2 {

    int value = 1;

    @DisplayName("테스트 인스턴스")
    @Test
    void create_new_study_19(){
        // 테스트 메소드가 수행될때마다 인스턴스를 새로 만든다.
        // 그러므로 전역변수값을 수정했다고 해도 다른 메소드에 반영되지 않는다.
        // @BeforeAll, AfterAll, BeforeEach, AfterEach 제외
        // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 사용 시 인스턴스를 공유해서 같이 쓴다.
        System.out.println(this);
        System.out.println(value++);
        Study actual = new Study(1);
        assertThat(actual.getLimitCount()).isGreaterThan(0);
    }

    @DisplayName("테스트 인스턴스2")
    @Test
    void create_new_study_20(){
        // 테스트 메소드가 수행될때마다 인스턴스를 새로 만든다.
        // 그러므로 전역변수값을 수정했다고 해도 다른 메소드에 반영되지 않는다.
        // @BeforeAll, AfterAll, BeforeEach, AfterEach 제외
        // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 사용 시 인스턴스를 공유해서 같이 쓴다.
        System.out.println(this);
        System.out.println(value);
    }

    @Order(2)
    @DisplayName("테스트 순서")
    @Test
    void create_new_study_21(){
        // @TestMethodOrder랑 @TestInstance(TestInstance.Lifecycle.PER_CLASS) 랑 상관없다.
        // 다만 같이 쓰면 상태정보도 공유하니깐 용도에 맞게 써야한다.
        System.out.println(this);
        System.out.println(value);
    }

    @Order(1)
    @DisplayName("테스트 순서2")
    @Test
    void create_new_study_22(){
        // @TestMethodOrder랑 @TestInstance(TestInstance.Lifecycle.PER_CLASS) 랑 상관없다.
        // 다만 같이 쓰면 상태정보도 공유하니깐 용도에 맞게 써야한다.
        System.out.println(this);
        System.out.println(value++);
    }

    @DisplayName("동작안함")
    @Test
    @Disabled
    void create_new_study_23(){
        // @TestMethodOrder랑 @TestInstance(TestInstance.Lifecycle.PER_CLASS) 랑 상관없다.
        // 다만 같이 쓰면 상태정보도 공유하니깐 용도에 맞게 써야한다.
        System.out.println(this);
        System.out.println(value++);
    }

    @BeforeAll
    void beforeAll(){
        // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 사용 시 static 일 필요가 없다.
        // 인스턴스를 하나만 만들기 때문이다.
        System.out.println("beforeAll");
    }

    @AfterAll
    void afterAll(){
        // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 사용 시 static 일 필요가 없다.
        // 인스턴스를 하나만 만들기 때문이다.
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach(){

        System.out.println("afterEach");
    }
}