package com.example.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class Study {

    @Id
    @GeneratedValue
    private Long id;
    StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    private String name;
    private LocalDateTime openedDateTime;
    private Long ownerId;
    private Member owner;

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

    public void setOwner(Member member){
        this.owner = member;
    }

    @Override
    public String toString() {
        return "Study{" +
                "studyStatus=" + studyStatus +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.studyStatus = StudyStatus.OPENED;

    }
}
