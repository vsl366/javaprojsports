import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private static final String URL = "jdbc:postgresql://localhost:5432/yourdbname"; //replace
    private static final String USER = "postgres";  // Replace with your PostgreSQL username
    private static final String PASSWORD = "";  // Replace with your PostgreSQL password

    public int validateUser(String usermail, String userpass) {
        String selectQuery = "SELECT COUNT(*) FROM usercred WHERE usermail = ? AND userpass = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, usermail);
            preparedStatement.setString(2, userpass);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return 1; // Validated
            } else {
                return -1; // Not validated
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return -1; // Indicate error
        }
    }
    
    public List<Map<String, Object>> getAvailableCoaches(String usermail) {
        List<Map<String, Object>> availableCoaches = new ArrayList<>();
        String query = "SELECT c.* FROM coaches c " +
                       "LEFT JOIN usercoaches u ON c.coachname = ANY(string_to_array(u.coachnames, ',')) " +
                       "AND u.usermail = ? " +
                       "WHERE u.coachnames IS NULL";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usermail);
            ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                Map<String, Object> coach = new HashMap<>();
                coach.put("coachname", rs.getString("coachname"));
                coach.put("coachexperience", rs.getInt("coachexperience"));
                coach.put("cost", rs.getBigDecimal("cost"));
                coach.put("imagepath", rs.getString("imagepath"));
                availableCoaches.add(coach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return availableCoaches;
    }

    public List<Map<String, Object>> getPurchasedCoaches(String usermail) {
        List<Map<String, Object>> purchasedCoaches = new ArrayList<>();
        String query = "SELECT c.coachname, c.coachexperience, c.cost, c.imagepath, c.coachmail " +
                       "FROM coaches c " +
                       "JOIN usercoaches u ON c.coachname = ANY(string_to_array(u.coachnames, ',')) " +
                       "WHERE u.usermail = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usermail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> coach = new HashMap<>();
                coach.put("coachname", rs.getString("coachname"));
                coach.put("coachexperience", rs.getInt("coachexperience"));
                coach.put("cost", rs.getBigDecimal("cost"));
                coach.put("imagepath", rs.getString("imagepath"));
                coach.put("coachmail", rs.getString("coachmail"));
                purchasedCoaches.add(coach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchasedCoaches;
    }

    
    // Verifies bank account credentials and checks balance
   // Verifies bank account credentials and checks balance
   public boolean canAffordCoach(String accntno, String accntpass, BigDecimal cost) {
    String query = "SELECT bank_balance FROM bank WHERE CAST(accntno AS VARCHAR) = ? AND accntpass = ?";
    
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, accntno);
        stmt.setString(2, accntpass);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            BigDecimal balance = rs.getBigDecimal("bank_balance");

            // Compare balance with cost
            if (balance.compareTo(cost) >= 0) {
                // Deduct the cost from bank_balance
                stmt.close(); // Close the previous statement
                
                String updateQuery = "UPDATE bank SET bank_balance = bank_balance - ? WHERE CAST(accntno AS VARCHAR) = ? AND accntpass = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(updateQuery)) {
                    stmt2.setBigDecimal(1, cost);
                    stmt2.setString(2, accntno);
                    stmt2.setString(3, accntpass);
                    stmt2.executeUpdate();
                }
                return true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}




    // Adds a coach to the user's list of purchased coaches
    public void addCoachToUser(String usermail, String coachName) {
        String query = "UPDATE usercoaches SET coachnames = CASE " +
                       "WHEN coachnames IS NULL THEN ? ELSE CONCAT(coachnames, ',', ?) END " +
                       "WHERE usermail = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, coachName);
            stmt.setString(2, coachName);
            stmt.setString(3, usermail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getAllCoaches() {
        List<Map<String, Object>> coaches = new ArrayList<>();
        String selectQuery = "SELECT coachname, coachexperience, coachmail, cost, imagepath FROM coaches";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> coachDetails = new HashMap<>();
                coachDetails.put("coachname", resultSet.getString("coachname"));
                coachDetails.put("coachexperience", resultSet.getInt("coachexperience"));
                coachDetails.put("coachmail", resultSet.getString("coachmail"));
                coachDetails.put("cost", resultSet.getBigDecimal("cost"));
                coachDetails.put("imagepath", resultSet.getString("imagepath"));
                
                coaches.add(coachDetails);
            }

        } catch (SQLException e) {
            System.out.println("Failed to fetch coach details.");
            e.printStackTrace();
        }

        return coaches;
    }
    
    public void updateLastKm(String usermail, String gearname, double km) {
        String updateQuery = "UPDATE usergear SET lastkm = lastkm + ? WHERE usermail = ? AND gearname = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
    
            preparedStatement.setDouble(1, km);
            preparedStatement.setString(2, usermail);
            preparedStatement.setString(3, gearname);
    
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Last KM updated successfully for gear: " + gearname);
            } else {
                System.out.println("No matching gear found to update.");
            }
    
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
    
    public int fetchUserExperience(String usermail) {
        String query = "SELECT experience FROM usercred WHERE usermail = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, usermail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("experience");
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch user experience.");
            e.printStackTrace();
        }
        return 1; // Default experience if not found
    }

    public void updateHeight(String usermail, double newHeight) {
        String updateQuery = "UPDATE usercred SET height = ? WHERE usermail = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, newHeight);
            preparedStatement.setString(2, usermail);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Height updated successfully for user: " + usermail);
            } else {
                System.out.println("No matching user found to update height.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update user height.");
            e.printStackTrace();
        }
    }

    public void updateWeight(String usermail, double newWeight) {
        String updateQuery = "UPDATE usercred SET weight = ? WHERE usermail = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, newWeight);
            preparedStatement.setString(2, usermail);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Weight updated successfully for user: " + usermail);
            } else {
                System.out.println("No matching user found to update weight.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update user weight.");
            e.printStackTrace();
        }
    }

    public List<String[]> fetchGearList(String usermail) {
        String selectQuery = "SELECT gearname, geartype, lastkm FROM usergear WHERE usermail = ?";
        List<String[]> gearList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, usermail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String gearname = resultSet.getString("gearname");
                String geartype = resultSet.getString("geartype");
                String lastKm = resultSet.getString("lastkm");
                gearList.add(new String[]{gearname, geartype, lastKm, "Remove"});
            }

        } catch (SQLException e) {
            System.out.println("Failed to fetch user gear list.");
            e.printStackTrace();
        }

        return gearList;
    }

    // Method to add a new gear for the user
    public void addGear(String usermail, String gearname, String geartype, double kmUsed) {
        String insertQuery = "INSERT INTO usergear (usermail, gearname, geartype, lastkm) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, usermail);
            preparedStatement.setString(2, gearname);
            preparedStatement.setString(3, geartype);
            preparedStatement.setDouble(4, kmUsed);

            preparedStatement.executeUpdate();
            System.out.println("Gear added successfully for user: " + usermail);

        } catch (SQLException e) {
            System.out.println("Failed to add gear.");
            e.printStackTrace();
        }
    }

    // Method to remove a gear for the user
    public void removeGear(String usermail, String gearname) {
        String deleteQuery = "DELETE FROM usergear WHERE usermail = ? AND gearname = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, usermail);
            preparedStatement.setString(2, gearname);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Gear removed successfully for user: " + usermail);
            } else {
                System.out.println("No matching gear found to delete.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to remove gear.");
            e.printStackTrace();
        }
    }

    
    public int updatePointsAndExperience(String usermail, int pt) {
        int up = 0;
        String selectQuery = "SELECT points, experience FROM usercred WHERE usermail = ?";
        String updateQuery = "UPDATE usercred SET points = ?, experience = ? WHERE usermail = ?";
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
    
            // Step 1: Fetch current points and experience
            selectStmt.setString(1, usermail);
            ResultSet resultSet = selectStmt.executeQuery();
    
            if (resultSet.next()) {
                int points = resultSet.getInt("points");
                int experience = resultSet.getInt("experience");
    
                // Step 2: Add pt to points
                points += pt;
    
                // Step 3: Update experience if points exceed the threshold
                while (points >= 500 * (experience * experience + experience)) {
                    up = 1;
                    experience++;
                }
    
                // Step 4: Update the database with new points and experience
                updateStmt.setInt(1, points);
                updateStmt.setInt(2, experience);
                updateStmt.setString(3, usermail);
    
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Points and experience updated successfully for user: " + usermail);
                } else {
                    System.out.println("No matching user found to update.");
                }
            } else {
                System.out.println("User not found with email: " + usermail);
            }
    
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        return up;
    }
    
    
    public String[] fetchRelGear(String usermail, String geartype) {
    String selectQuery = "SELECT gearname FROM usergear WHERE usermail = ? AND geartype = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

        preparedStatement.setString(1, usermail);
        preparedStatement.setString(2, geartype);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Use a list to collect results and then convert it to an array
        ArrayList<String> gearList = new ArrayList<>();
        while (resultSet.next()) {
            gearList.add(resultSet.getString("gearname"));
        }

        // Convert the ArrayList to a String array
        return gearList.toArray(new String[0]);

    } catch (SQLException e) {
        System.out.println("Database connection failed.");
        e.printStackTrace();
        return new String[0]; // Return empty array in case of error
    }
}

     public Map<String, Object> getUserDetails(String usermail) {
        Map<String, Object> userDetails = new HashMap<>();

        String query = "SELECT age, height, weight, experience FROM usercred WHERE usermail = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usermail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userDetails.put("age", resultSet.getInt("age"));
                userDetails.put("height", resultSet.getDouble("height"));
                userDetails.put("weight", resultSet.getDouble("weight"));
                userDetails.put("experience", resultSet.getInt("experience"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userDetails;
    }
    
    public void insertUser(String usermail, String userpass, int age, Integer experience, String gender, double weight, double height) {
        String insertQuery = "INSERT INTO usercred (usermail, userpass, age, experience, gender, weight, height, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int points = 1000; // Default points

        if (experience != null) {
            points = 500 * (experience * experience + experience); // Calculate points based on experience
        } else {
            experience = 1; // Default experience
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, usermail);
            preparedStatement.setString(2, userpass);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, experience);
            preparedStatement.setString(5, gender);
            preparedStatement.setDouble(6, weight);
            preparedStatement.setDouble(7, height);
            preparedStatement.setInt(8, points);

            preparedStatement.executeUpdate();
            System.out.println("User successfully inserted into the database.");

        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}
