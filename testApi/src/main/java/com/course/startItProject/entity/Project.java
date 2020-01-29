package com.course.startItProject.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;


@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String projectName;
    private int goal;
    private double reached;
    private String videoLink;
    private Date durationDate;

    @Column(length = 100)
    private String shortDisc;

    @Column(length = 1000000)
    private String fullDisc;

    @CreationTimestamp
    private Date creationDate;

    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User author;

    @ElementCollection(targetClass = Categories.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "project_category", joinColumns = @JoinColumn(name = "project"))
    @Enumerated(EnumType.STRING)
    private Set<Categories> categories;

    @Transient
    private List<String> imageUrls;

    public Project() {

    }

    public Project(String projectName, String shortDisc, String fullDisc, User author) {
        this.projectName = projectName;
        this.shortDisc = shortDisc;
        this.fullDisc = fullDisc;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(Date durationDate) {
        this.durationDate = durationDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getShortDisc() {
        return shortDisc;
    }

    public void setShortDisc(String shortDisc) {
        this.shortDisc = shortDisc;
    }

    public String getFullDisc() {
        return fullDisc;
    }

    public void setFullDisc(String fullDisc) {
        this.fullDisc = fullDisc;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public double getReached() {
        return reached;
    }

    public void setReached(double reached) {
        this.reached = reached;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
