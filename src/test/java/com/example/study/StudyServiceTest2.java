package com.example.study;

import com.example.domain.Study;
import com.example.domain.StudyStatus;
import com.example.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceTest2.ContainerPropertyInitializer.class)
class StudyServiceTest2 {

    // Testcontainers 관련 테스트 파일

    @Mock MemberService memberService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    Environment environment;
    @Value("${container.port}") int port;

    @Container
    static GenericContainer postgreSQLContainer = new GenericContainer("postgres")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "studytest")
            .withEnv("POSTGRES_PASSWORD", "studytest")
            .waitingFor(Wait.forListeningPort())
            ;
    //static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer().withDatabaseName("studydb");

    @BeforeEach
    void beforeEach(){
        System.out.println("============");
        System.out.println(postgreSQLContainer.getMappedPort(5432));
        // 모든 로그 확인
        // System.out.println(postgreSQLContainer.getLogs());
        System.out.println(environment.getProperty("container.port"));
        System.out.println(port);
        studyRepository.deleteAll();
    }

    @BeforeAll
    static void beforeAll(){
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        // 스트리밍 로그
        postgreSQLContainer.followOutput(logConsumer);
    }

    @Test
    void createStudyService(){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        assertNull(study.getOpenedDateTime());
//        given(memberService.findById(1L)).willReturn(Optional.of(Member));

        // When
        studyService.openStudy(study);

        // Then
        // TODO study의 status가 OPENED로 변경됐는지 확인
        assertEquals(StudyStatus.OPENED, study.getStatus());
        // TODO study의 openedDataTime이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        // TODO memberService의 notify(study)가 호출 됐는지 확인.
        then(memberService).should().notify(study);
    }

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432))
                    .applyTo(context.getEnvironment());
            ;
        }
    }



}