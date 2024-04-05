package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.*;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class VoteDAO {
    public static List<VoteListItemVM> getActive() {
        List<VoteListItemVM> votes = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_active_votes()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                VoteListItemVM vote = new VoteListItemVM();
                vote.setVoteID(resultSet.getString("VoteID"));
                vote.setUserID(resultSet.getString("UserID"));
                vote.setDescription(resultSet.getString("Description"));
                if(resultSet.getTimestamp("StartTime") != null)
                    vote.setStartTime(resultSet.getTimestamp("StartTime").toInstant());
                if(resultSet.getTimestamp("EndTime") != null)
                    vote.setEndTime(resultSet.getTimestamp("EndTime").toInstant());
                vote.setNumberOfVotes(resultSet.getInt("num_of_votes"));
                votes.add(vote);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return votes;
    }

    public static List<VoteListItemVM> getMyVotes(String userID) {
        List<VoteListItemVM> votes = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_myvotes(?)}")
        ) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                VoteListItemVM vote = new VoteListItemVM();
                vote.setVoteID(resultSet.getString("VoteID"));
                vote.setUserID(resultSet.getString("UserID"));
                vote.setDescription(resultSet.getString("Description"));
                if(resultSet.getTimestamp("StartTime") != null)
                    vote.setStartTime(resultSet.getTimestamp("StartTime").toInstant());
                if(resultSet.getTimestamp("EndTime") != null)
                    vote.setEndTime(resultSet.getTimestamp("EndTime").toInstant());
                vote.setNumberOfVotes(resultSet.getInt("num_of_votes"));
                votes.add(vote);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return votes;
    }

    public static VoteVM get(String voteID) {
        VoteVM vote = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_vote(?)}")
        ) {
            statement.setString(1, voteID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                vote = new VoteVM();
                vote.setVoteID(resultSet.getString("VoteID"));
                vote.setUserID(resultSet.getString("UserID"));
                vote.setDescription(resultSet.getString("Description"));
                if(resultSet.getTimestamp("StartTime") != null)
                    vote.setStartTime(resultSet.getTimestamp("StartTime").toInstant());
                if(resultSet.getTimestamp("EndTime") != null)
                    vote.setEndTime(resultSet.getTimestamp("EndTime").toInstant());
                vote.setNumberOfOptions(resultSet.getInt("num_of_options"));
                try(CallableStatement statement2 = connection.prepareCall("{CALL sp_get_voteoptions(?)}")) {
                    statement2.setString(1, voteID);
                    ResultSet resultSet2 = statement2.executeQuery();
                    List<VoteOption> options = new ArrayList<>();
                    while(resultSet2.next()) {
                        VoteOption option = new VoteOption();
                        option.setOptionID(resultSet2.getInt("OptionID"));
                        option.setTitle(resultSet2.getString("Title"));
                        option.setDescription(resultSet2.getString("Description"));
                        Blob blob = resultSet2.getBlob("Image");
                        if(blob != null) {
                            InputStream inputStream = blob.getBinaryStream();
                            // Source: https://www.codejava.net/coding/how-to-display-images-from-database-in-jsp-page-with-java-servlet
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            option.setImage(outputStream.toByteArray());
                            inputStream.close();
                            outputStream.close();
                        }
                        options.add(option);
                    }
                    vote.setOptions(options);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return vote;
    }

    public static boolean add(Vote vote) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_vote(?,?,?,?,?)}")) {
                    statement.setString(1, vote.getVoteID());
                    statement.setString(2, vote.getUserID());
                    statement.setString(3, vote.getDescription());
                    statement.setTimestamp(4, vote.getStartTime() == null ? null : Timestamp.from(vote.getStartTime()));
                    statement.setTimestamp(5, vote.getEndTime() == null ? null : Timestamp.from(vote.getEndTime()));
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static boolean edit(Vote vote) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_vote(?,?,?,?)}")) {
                    statement.setString(1, vote.getVoteID());
                    statement.setString(2, vote.getDescription());
                    statement.setTimestamp(3, vote.getStartTime() == null ? null : Timestamp.from(vote.getStartTime()));
                    statement.setTimestamp(4, vote.getEndTime() == null ? null : Timestamp.from(vote.getEndTime()));
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
                        result = true;
                    }
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static boolean delete(String voteID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_vote(?)}")) {
                    statement.setString(1, voteID);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
