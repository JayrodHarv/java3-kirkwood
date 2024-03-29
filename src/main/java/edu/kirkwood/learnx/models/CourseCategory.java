package edu.kirkwood.learnx.models;

public class CourseCategory {
    private int id;
    private String name;
    private int numCourses;

    public CourseCategory() {
    }

    public CourseCategory(int id, String name, int numCourses) {
        this.id = id;
        this.name = name;
        this.numCourses = numCourses;
    }

    @Override
    public String toString() {
        return "CourseCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numCourses=" + numCourses +
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

    public int getNumCourses() {
        return numCourses;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }
}