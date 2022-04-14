package com.example.study;

import com.example.domain.Member;
import com.example.domain.Study;
import com.example.domain.StudyStatus;
import com.example.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

//  1번째방법
//    @Test
//    void createStudyService(){
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
//
//        StudyService studyService = new StudyService(memberService, studyRepository);
//        assertNotNull(studyService);
//    }

//  2번째방법
//    @Mock MemberService memberService;
//    @Mock StudyRepository studyRepository;

//    @Test
//    void createStudyService(){
//        StudyService studyService = new StudyService(memberService, studyRepository);
//        assertNotNull(studyService);
//    }

//  3번째방법
    @Test
    void createStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository){

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        // 1이라고 호출하는 순간 위에 정의한 member가 리턴된다.
        // any() 라고 정의하면 어떤 파라메터 값을 받든 위에서 정의한 member가 리턴된다.
        //when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

        assertEquals("test@email.com", memberService.findById(1L).get().getEmail());
        assertEquals("test@email.com", memberService.findById(2L).get().getEmail());

        // void를 리턴하는 경우 doThrow를 이용하면 validate를 1로 호출하면 예외를 던진다.
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        // 첫번째 호출했을때는 위에서 정의한 member가 리턴
        // 두번째 호출했을때는
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        assertEquals("test@email.com", memberService.findById(1L).get().getEmail());
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });
        assertEquals(Optional.empty(), memberService.findById(3L));

    }

    @Test
    void createStudyService2(@Mock MemberService memberService, @Mock StudyRepository studyRepository){

        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        // notify를 호출했는지 검증한다.
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        // memberService.notify를 순차적으로 호출해야한다.
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        // 더이상 memberService를 호출하지 않아야한다.
        verifyNoMoreInteractions(memberService);

    }

    // BDD스타일 작성법
    @Test
    void createStudyService3(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        // when(memberService.findById(1L)).thenReturn(Optional.of(member));
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        // when(studyRepository.save(study)).thenReturn(study);
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        // assertEquals(member, study.getOwner());
        // verify(memberService, times(1)).notify(member);
        then(memberService).should(times(1)).notify(study);
        // verifyNoMoreInteractions(memberService);
        then(memberService).shouldHaveNoInteractions();

    }

    // 연습문제풀기1
    @Test
    void mockTest1(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setEmail("test@email.com");
        member.setId(1L);

        Study study = new Study(10, "테스트");
        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study)).thenReturn(study);

        //assertEquals(memberService.findById(1L), Optional.of(member));
        //assertEquals(studyRepository.save(study), study);

        studyService.createNewStudy(1L, study);
        //assertEquals(member, study.getOwner());


    }

    // 연습문제풀기2
    @Test
    void mockTest2(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        assertNull(study.getOpenedDateTime());
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        // TODO study의 status가 OPENED로 변경됐는지 확인
        assertEquals(StudyStatus.OPENED, study.getStudyStatus());
        // TODO study의 openedDataTime이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        // TODO memberService의 notify(study)가 호출 됐는지 확인.
        then(memberService).should().notify(study);

    }

}