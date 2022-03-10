package com.example.study;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

public class StudyTest3 {

    // 선언적으로 클래스를 등록하는 방법.(기본적으로 설정이 꺼져있다)
    // threshold란 변수값을 호출할때 세팅하고 싶으면 아래와 같은 방법을 쓰고
    // threshold란 변수값이 항상 고정이라면 어노테이션으로 선언한다.
    // @ExtendWith(FindSlowTestExtension.class)
    @RegisterExtension static  FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @DisplayName("느린 테스트 찾아내기")
    @Test
    void create_new_study_24() throws InterruptedException {
        Thread.sleep(1005);
        System.out.println(this);
    }

}
