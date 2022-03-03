package com.example.study;

public class Study {

    StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public Study()
    {
        this.limit = 0;
        this.name = "";
    }

    public Study(int limit, String name)
    {
        this.limit = limit;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "studyStatus=" + studyStatus +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
