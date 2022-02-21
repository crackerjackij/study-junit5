package com.example.study;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    @DisplayName("assertEquals")
    void create_new_study_1(){
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus());
        System.out.println("create");
    }

    @Test
    @DisplayName("assertNotNull")
    void create_new_study_2(){
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @DisplayName("assertEquals")
    void create_new_study_3(){
        Study study = new Study();
        //assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이다.");
        // 아래처럼 작성하면 텍스트 더하기 연산자가 있지만 실패했을 경우에만 조합한다. 위에처럼 하면 무조건 메시지를 조합한다.
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이다.");
    }

    @Test
    @DisplayName("assertTrue")
    void create_new_study_4(){
        Study study = new Study(10);
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
    }

    @Test
    @DisplayName("assertAll")
    void create_new_study_5(){
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "성공인가 실패인가."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );
    }

    @Test
    @DisplayName("assertThrows")
    void create_new_study_6(){
        Study study = new Study(10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals(exception.getMessage(), "limit는 0보다 커야한다.");
    }

    @Test
    @DisplayName("assertTimeout")
    void create_new_study_7(){
        Study study = new Study(10);
        // assertTimeout(Duration.ofSeconds(10), () -> new Study(10));
        // 내부 메소드의 결과가 나올때까지 대기. 로직 종료 후 비교
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
        // 앞에 선언한 시간이 지나면 즉시 테스트 실패 처리
        // 다만 트랜잭션처럼 ThreadLocal을 사용한 코드가 있다면 예상치 못한 결과를 발생시킬 수 있다.
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll(){
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