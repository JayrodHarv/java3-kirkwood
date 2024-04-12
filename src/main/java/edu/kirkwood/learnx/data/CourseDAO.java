package edu.kirkwood.learnx.data;

import com.sun.source.tree.Tree;
import edu.kirkwood.learnx.models.Course;
import edu.kirkwood.learnx.models.CourseCategory;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class CourseDAO extends Database {

    public static List<Course> get(int limit, int offset, String categories, String skillLevel) {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try(CallableStatement statement = connection.prepareCall("{CALL sp_get_courses(?, ?, ?, ?)}")) {
                    statement.setInt(1, limit);
                    statement.setInt(2, offset);
                    statement.setString(3, categories);
                    statement.setString(4, skillLevel);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String description = resultSet.getString("description");
                            String level = resultSet.getString("level");
                            String picture = resultSet.getString("picture");
                            String teacherFirstName = resultSet.getString("first_name");
                            String teacherLastName = resultSet.getString("last_name");
                            int categoryId = resultSet.getInt("category_id");
                            String categoryName = resultSet.getString("category_name");
                            Course course = new Course(id, name, description, level, picture, teacherFirstName, teacherLastName, categoryId, categoryName);
                            courses.add(course);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public static List<CourseCategory> getAllCategories() {
        List<CourseCategory> categories = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try(CallableStatement statement = connection.prepareCall("{CALL sp_get_all_course_categories()}");
                    ResultSet resultSet = statement.executeQuery()
                ) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        int numCourses = resultSet.getInt("num_courses");
                        categories.add(new CourseCategory(id, name, numCourses));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public static boolean enroll(int userId, int courseId) {
        boolean result = false;
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{CALL sp_add_enrollment(?, ?)}")
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static TreeMap<Course, Instant> getCourseEnrollments(int limit, int offset, int userId) {
        TreeMap<Course, Instant> enrollments = new TreeMap<>();

        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_courses_by_student(?,?,?)}")
        ) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.setInt(3, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String level = resultSet.getString("level");
                    String picture = resultSet.getString("picture");
                    String teacherFirstName = resultSet.getString("first_name");
                    String teacherLastName = resultSet.getString("last_name");
                    int categoryId = resultSet.getInt("category_id");
                    String categoryName = resultSet.getString("category_name");
                    Instant enrollment_date = resultSet.getTimestamp("enrollment_date").toInstant();
                    Course course = new Course(id, name, description, level, picture, teacherFirstName, teacherLastName, categoryId, categoryName);
                    enrollments.put(course, enrollment_date);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return enrollments;
    }
}