package com.example.study;

import com.example.domain.Member;
import com.example.domain.Study;
import com.example.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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


        //studyService.createNewStudy(1L, study);


    }

}