package com.example.study;

import org.junit.Before;
import org.junit.Test;

/*
    junit-vintage-engine 라이브러리를 사용하면
    JUnit 5버전 내에서 JUnit 4버전으로 테스트를 작성해서 실행할 수 있다.
 */
public class StudyJUnit4Test {

    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void createTest(){
        System.out.println("test");
    }

    @Test
    public void createTest2(){
        System.out.println("test2");
    }
}
