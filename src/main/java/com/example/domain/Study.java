package com.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
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
