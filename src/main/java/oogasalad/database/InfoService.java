package oogasalad.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to interact with the database.
 */
public class InfoService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://106.15.233.200:3306/CS308";
  private static final String USER = "CS308";
  private static final String PASS = "CS308Farm";

  /**
   * Get a connection to the database.
   *
   * @return the connection
   * @throws SQLException           if the connection fails
   * @throws ClassNotFoundException if the driver is not found
   */

  public static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName(JDBC_DRIVER);
    return DriverManager.getConnection(DB_URL, USER, PASS);
  }

  /**
   * Check if a user exists in the database.
   *
   * @param username the username to check
   * @return true if the user exists, false otherwise
   */
  public static boolean userExists(String username) {
    String sql = "SELECT * FROM user WHERE username=?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Check if a user is valid.
   *
   * @param username the username
   * @param password the password
   * @return true if the user is valid, false otherwise
   */
  public static boolean isValidUser(String username, String password) {
    String sql = "SELECT * FROM user WHERE username=? AND password=?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.setString(2, password);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Get the user id.
   *
   * @param username the username
   * @return the user id
   */

  public static int getUserId(String username) {
    String sql = "SELECT id FROM user WHERE username=?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("id");
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * Add a user to the database.
   *
   * @param username the username
   * @param email    the email
   * @param password the password
   * @return true if the user is added, false otherwise
   */
  public static boolean addUser(String username, String email, String password) {
    if (userExists(username)) {
      return false;
    }
    String sql = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.setString(2, email);
      stmt.setString(3, password);
      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Select all thoughts sorted by time given a user_id.
   *
   * @param userId the user ID
   * @return a list of thoughts sorted by time
   */
  public static List<String> getAllThoughtsByUserId(int userId) {
    List<String> thoughts = new ArrayList<>();
    String sql = "SELECT thought_text FROM thoughts WHERE user_id=? ORDER BY created_at DESC";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          thoughts.add(rs.getString("thought_text"));
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return thoughts;
  }

  /**
   * Select all accepted friends given a user_id.
   *
   * @param userId the user ID
   * @return a list of user IDs representing accepted friends
   */
  public static List<Integer> getAllAcceptedFriendsByUserId(int userId) {
    List<Integer> friends = new ArrayList<>();
    String sql = "SELECT user2_id FROM friendships WHERE user1_id=? AND status='accepted'";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          friends.add(rs.getInt("user2_id"));
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return friends;
  }

  /**
   * Select all received messages grouped by sender id, each sorted by time.
   *
   * @param userId the user ID
   * @return a list of sender IDs and their corresponding messages sorted by time
   */
  public static List<Message> getAllReceivedMessagesByUserId(int userId) {
    List<Message> messages = new ArrayList<>();
    String sql =
        "SELECT sender_id, message_text FROM messages WHERE receiver_id=? ORDER BY created_at DESC";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          int senderId = rs.getInt("sender_id");
          String messageText = rs.getString("message_text");
          messages.add(new Message(senderId, messageText));
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return messages;
  }

  /**
   * Select all thoughts from the database and return a list of Thought objects.
   *
   * @return a list of all thoughts
   */
  public static List<Thought> getAllThoughts() {
    List<Thought> thoughts = new ArrayList<>();
    String sql = "SELECT thoughts.id, user.username, thoughts.thought_text, thoughts.created_at "
        + "FROM thoughts " + "INNER JOIN user ON thoughts.user_id = user.id "
        + "ORDER BY thoughts.created_at DESC";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String text = rs.getString("thought_text");
        String time = rs.getString("created_at");
        thoughts.add(new Thought(id, username, text, time));
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return thoughts;
  }

  /**
   * Add a new thought to the database.
   *
   * @param userId      the user ID associated with the thought
   * @param thoughtText the text of the thought
   * @param time        the time the thought was created
   * @return true if the thought is added successfully, false otherwise
   */
  public static boolean addThought(int userId, String thoughtText, String time) {
    String sql = "INSERT INTO thoughts (user_id, thought_text, created_at) VALUES (?, ?, ?)";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setString(2, thoughtText);
      stmt.setString(3, time);
      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }


  /**
   * Save game data to the database.
   *
   * @param userId         the user ID
   * @param gameSaveJson   the game save JSON
   * @param gameConfigJson the game configuration JSON
   * @param storeJson      the store JSON
   * @return true if the game data is saved, false otherwise
   */
  public static boolean saveGameData(int userId, String gameSaveJson, String gameConfigJson,
      String storeJson) {
    String sql =
        "INSERT INTO game_saves(user_id, gamesave, gameconfiguration, configurablestores) VALUES "
            + "(?, ?, ?, ?) ON DUPLICATE KEY UPDATE gamesave = VALUES(gamesave), "
            + "gameconfiguration = VALUES(gameconfiguration), configurablestores = VALUES"
            + "(configurablestores), save_time = CURRENT_TIMESTAMP";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setString(2, gameSaveJson);
      stmt.setString(3, gameConfigJson);
      stmt.setString(4, storeJson);
      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }


  /**
   * Get the game data from the database.
   *
   * @param userId the user ID
   * @return the game data
   */
  public static GameSaveData loadLatestGameData(int userId) {
    String sql =
        "SELECT gamesave, gameconfiguration, configurablestores FROM game_saves WHERE user_id=? "
            + "ORDER BY save_time DESC LIMIT 1";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          String gameSaveJson = rs.getString("gamesave");
          String gameConfigJson = rs.getString("gameconfiguration");
          String storeJson = rs.getString("configurablestores");
          return new GameSaveData(userId, gameSaveJson, gameConfigJson, storeJson);
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }


}
