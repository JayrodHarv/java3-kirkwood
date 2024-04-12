package edu.kirkwood.learnx.models;

import org.jetbrains.annotations.NotNull;

public class Course implements Comparable<Course>{
    private int id;
    private String name;
    private String description;
    private String level;
    private String picture;
    private String teacherFirstName;
    private String teacherLastName;
    private int categoryId;
    private String categoryName;

    public Course() {
    }

    public Course(int id, String name, String description, String level, String picture, String teacherFirstName, String teacherLastName, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.picture = picture;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int compareTo(@NotNull Course o) {
       return this.name.compareTo(o.getName());
    }
}