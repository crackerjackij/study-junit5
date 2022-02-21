package com.example.study;

public class Study {

    StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    public Study(){
        this.limit = 0;
    }

    public Study(int limit){
        if(limit < 0){
            throw new IllegalArgumentException("limit는 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.studyStatus;
    }

    public int getLimit(){
        return this.limit;
    }
}
