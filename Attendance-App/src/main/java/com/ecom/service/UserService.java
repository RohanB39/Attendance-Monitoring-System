package com.ecom.service;

import com.ecom.model.AttendanceRecord;
import com.ecom.model.User;
import com.ecom.repository.UserRepo;
import com.ecom.repository.UserRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private UserRepo userrepo;

    public boolean saveUser(User user) {
        return userRepository.saveNewUser(user);
    }
    
    public boolean validateUser(String username, String password) {
        return userRepository.checkUserCredentials(username, password);
    }

    public boolean updateSignInTime(String username, LocalTime signInTime, LocalDate signInDate) {
        return userRepository.updateSignInTime(username, signInTime, signInDate);
    }
    
    public boolean updateSignOutTime(LocalTime signOutTime, LocalDate signOutDate) {
        return userRepository.updateSignOutTime(signOutTime, signOutDate);
    }

    public String getUsernameById(Integer userId) {
        return userRepository.getUsernameById(userId);
    }
    
    public List<Date> getDatesByUsername(String username) {
        return userRepository.getDatesByUsername(username);
    }
    
    public List<AttendanceRecord> getAttendanceByUsername(String username) {
        return userRepository.getAttendanceByUsername(username);
    }
    
    public List<String> getAllUsers() {
        return userRepository.getUsers();
    }
}
