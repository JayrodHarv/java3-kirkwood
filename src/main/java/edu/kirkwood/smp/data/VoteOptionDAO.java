package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.Vote;
import edu.kirkwood.smp.models.VoteListItemVM;
import edu.kirkwood.smp.models.VoteOption;
import edu.kirkwood.smp.models.VoteVM;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class VoteOptionDAO {
//    public static List<VoteOption> getAll() {
//        List<VoteOption> options = new ArrayList<>();
//        try(Connection connection = getConnection();
//            CallableStatement statement = connection.prepareCall("{CALL sp_get_votes()}");
//            ResultSet resultSet = statement.executeQuery()
//        ) {
//            while(resultSet.next()) {
//                VoteListItemVM vote = new VoteListItemVM();
//                vote.setVoteID(resultSet.getString("VoteID"));
//                vote.setUserID(resultSet.getString("UserID"));
//                vote.setDescription(resultSet.getString("Description"));
//                if(resultSet.getTimestamp("StartTime") != null)
//                    vote.setStartTime(resultSet.getTimestamp("StartTime").toInstant());
//                if(resultSet.getTimestamp("EndTime") != null)
//                    vote.setEndTime(resultSet.getTimestamp("EndTime").toInstant());
//                vote.setNumberOfVotes(resultSet.getInt("num_of_votes"));
//                votes.add(vote);
//            }
//        } catch (SQLException e) {
//            System.out.println("Likely bad SQL query");
//            System.out.println(e.getMessage());
//        }
//        return votes;
//    }

//    public static VoteOption get(String optionID) {
//        VoteOption vote = null;
//        try(Connection connection = getConnection();
//            CallableStatement statement = connection.prepareCall("{CALL sp_get_vote(?)}")
//        ) {
//            statement.setString(1, voteID);
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()) {
//                vote = new VoteVM();
//                vote.setVoteID(resultSet.getString("VoteID"));
//                vote.setUserID(resultSet.getString("UserID"));
//                vote.setDescription(resultSet.getString("Description"));
//                if(resultSet.getTimestamp("StartTime") != null)
//                    vote.setStartTime(resultSet.getTimestamp("StartTime").toInstant());
//                if(resultSet.getTimestamp("EndTime") != null)
//                    vote.setEndTime(resultSet.getTimestamp("EndTime").toInstant());
//                vote.setNumberOfOptions(resultSet.getInt("num_of_options"));
//                try(CallableStatement statement2 = connection.prepareCall("{CALL sp_get_voteoptions(?)}")) {
//                    statement2.setString(1, voteID);
//                    ResultSet resultSet2 = statement2.executeQuery();
//                    List<VoteOption> options = new ArrayList<>();
//                    while(resultSet2.next()) {
//                        VoteOption option = new VoteOption();
//                        option.setOptionID(resultSet2.getInt("OptionID"));
//                        option.setTitle(resultSet2.getString("Title"));
//                        option.setDescription(resultSet2.getString("Description"));
//                        Blob blob = resultSet2.getBlob("Image");
//                        InputStream inputStream = blob.getBinaryStream();
//
//                        // Source: https://www.codejava.net/coding/how-to-display-images-from-database-in-jsp-page-with-java-servlet
//                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                        byte[] buffer = new byte[4096];
//                        int bytesRead;
//                        while ((bytesRead = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                        option.setImage(outputStream.toByteArray());
//                        inputStream.close();
//                        outputStream.close();
//
//                        options.add(option);
//                    }
//                    vote.setOptions(options);
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            resultSet.close();
//        } catch (SQLException e) {
//            System.out.println("Likely bad SQL query");
//            System.out.println(e.getMessage());
//        }
//        return vote;
//    }

    public static boolean add(VoteOption option) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_voteoption(?,?,?,?)}")) {
                    statement.setString(1, option.getVoteID());
                    statement.setString(2, option.getTitle());
                    statement.setString(3, option.getDescription());
                    statement.setBlob(4, option.getImage() == null ? null : new SerialBlob(option.getImage()));
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

    public static boolean edit() {
        return false;
    }

    public static boolean delete() {
        return false;
    }
}
