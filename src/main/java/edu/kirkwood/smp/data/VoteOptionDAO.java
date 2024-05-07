package edu.kirkwood.smp.data;

import edu.kirkwood.shared.ImageHelper;
import edu.kirkwood.smp.models.VoteOption;
import edu.kirkwood.smp.models.World;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.*;
import java.util.Date;

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

    public static VoteOption get(int optionID) {
        VoteOption option = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_voteoption(?)}")
        ) {
            statement.setInt(1, optionID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                option = new VoteOption();
                option.setOptionID(resultSet.getInt("OptionID"));
                option.setVoteID(resultSet.getString("VoteID"));
                option.setTitle(resultSet.getString("Title"));
                option.setDescription(resultSet.getString("Description"));

                Blob blob = resultSet.getBlob("Image");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    String imageType = URLConnection.guessContentTypeFromStream(inputStream);

                    byte[] Image = ImageHelper.getImageBytesFromInputStream(inputStream);
                    option.setImage(Image);
                    option.setBase64Image(ImageHelper.getBase64Image(imageType, Image));
                }
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return option;
    }

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

    public static boolean edit(VoteOption option) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_voteoption(?,?,?,?)}")) {
                    statement.setInt(1, option.getOptionID());
                    statement.setString(2, option.getTitle());
                    statement.setString(3, option.getDescription());
                    statement.setBlob(4, option.getImage() == null ? null : new SerialBlob(option.getImage()));
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int optionID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_voteoption(?)}")) {
                    statement.setInt(1, optionID);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
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
