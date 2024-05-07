package edu.kirkwood.learnx.data;

import edu.kirkwood.learnx.models.JobListing;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JobListingDAO extends Database {

    public static void main(String[] args) {
        getAll(100, 0, "", "").forEach(System.out::println);
    }

    public static List<JobListing> getAll(int limit, int offset, String departmentName, String location) {
        List<JobListing> jobListings = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_job_listings(?,?,?,?)}")
        ) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.setString(3, departmentName);
            statement.setString(4, location);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("job_id");
                int departmentId = resultSet.getInt("department_id");
                String departmentname = resultSet.getString("department_name");
                boolean featured = resultSet.getBoolean("featured");
                String position = resultSet.getString("position");
                Instant postedAt = resultSet.getTimestamp("posted_at").toInstant();
                String contract = resultSet.getString("contract");
                String locationE = resultSet.getString("location");
                String description = resultSet.getString("description");
                JobListing jobListing = new JobListing(id, departmentId, departmentname, featured, position, postedAt, contract, locationE, description);
                jobListings.add(jobListing);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return jobListings;
    }

}
