package com.angular.startItProject.entity;

import javax.persistence.*;

@Entity
public class Donate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int sum;
//user_Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user")
    private User user;
    //project_id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    private Project project;

    public Donate(int sum, User userId, Project projectId) {
        this.sum = sum;
        this.user = userId;
        this.project = projectId;
    }

    public Donate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSumma(int sum) {
        this.sum = sum;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    public Project getProjectId() {
        return project;
    }

    public void setProjectId(Project projectId) {
        this.project = projectId;
    }
}
