package com.ecom.repository;

import com.ecom.model.AttendanceRecord;
import com.ecom.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/attendance";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "25082018@Rt";

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, email, phone_number) VALUES (?, ?, ?, ?)";
    private static final String CHECK_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String UPDATE_SIGN_IN_TIME_SQL = "INSERT INTO attendance (username, sign_in_time, date) VALUES (?, ?, ?)";
    private static final String UPDATE_SIGN_OUT_TIME_SQL = "UPDATE attendance SET sign_out_time = ? where date = ?";
    private static final String GET_USERNAME_BY_ID_SQL = "SELECT username FROM users WHERE id = ?";
    private static final String GET_DATES_BY_USERNAME_SQL = "SELECT Date FROM attendance WHERE username = ?";
    private static final String GET_ATTENDANCE_BY_USERNAME_SQL = "SELECT date, sign_in_time, sign_out_time FROM attendance WHERE username = ?";
    
    public boolean saveNewUser(User user) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getPhoneNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean checkUserCredentials(String username, String password) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateSignInTime(String username, LocalTime signInTime, LocalDate signInDate) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SIGN_IN_TIME_SQL)) {
        	preparedStatement.setString(1, username);
            preparedStatement.setTime(2, Time.valueOf(signInTime));
            preparedStatement.setDate(3, Date.valueOf(signInDate));
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Sign-in time updated successfully.");
                return true;
            } else {
                System.out.println("No record found with the given username and date.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean updateSignOutTime(LocalTime signOutTime, LocalDate signOutDate) {
       try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SIGN_OUT_TIME_SQL)) {
            preparedStatement.setTime(1, Time.valueOf(signOutTime));
            preparedStatement.setDate(2, Date.valueOf(signOutDate));
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsernameById(Integer userId) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERNAME_BY_ID_SQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Date> getDatesByUsername(String username) {
        List<Date> dates = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DATES_BY_USERNAME_SQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dates.add(resultSet.getDate("U_Date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }
    
    public List<AttendanceRecord> getAttendanceByUsername(String username) {
        List<AttendanceRecord> records = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ATTENDANCE_BY_USERNAME_SQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AttendanceRecord record = new AttendanceRecord();
                record.setDate(resultSet.getDate("date").toLocalDate());
                record.setSignInTime(resultSet.getTime("sign_in_time").toLocalTime());
                Time signOutTime = resultSet.getTime("sign_out_time");
                if (signOutTime != null) {
                    record.setSignOutTime(signOutTime.toLocalTime());
                } else {
                	record.setSignOutTime(LocalTime.MIDNIGHT);
                }

                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    
    public static String formatTo12Hour(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return localTime.format(formatter);
    }
    
    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT username FROM users")) {
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

}
