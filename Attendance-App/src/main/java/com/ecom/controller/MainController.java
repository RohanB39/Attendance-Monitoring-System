package com.ecom.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.AttendanceRecord;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if ("admin".equals(username) && "123".equals(password)) {
            model.addAttribute("username", username); //Asch add kely
            return "adminPage";
        }
        boolean Valid = userService.validateUser(username, password);
        if (Valid == true) {
            model.addAttribute("username", username);
            return "home";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials");
            return "login";
        }
    }
    
    @PostMapping("/signIn")
    public String signIn(@RequestParam("username") String username, Model model) {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        boolean signIn = userService.updateSignInTime(username, now, today);
        if (signIn == true) {
            model.addAttribute("username", username);
            return "SignOut";
        } else {
            model.addAttribute("errorMessage", "Failed to update sign-in time");
            return "home"; 
        }
    }
    
    @PostMapping("/signOut")
    public String signOut(@RequestParam("username") String username, Model model) {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        boolean signOut = userService.updateSignOutTime(now, today);
        if (signOut == true) {
            model.addAttribute("username", username);
            return "login";
        } else {
            model.addAttribute("errorMessage", "Failed to update sign-out time");
            return "home";
        }
    }
    
    @GetMapping("/userAttendanceReport")
    public String getAttendanceReport(@RequestParam("username") String username, Model model) {
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        int lengthOfMonth = currentMonth.length(today.isLeapYear());

        List<LocalDate> allDatesInMonth = IntStream.rangeClosed(1, lengthOfMonth)
                .mapToObj(day -> LocalDate.of(today.getYear(), currentMonth, day))
                .collect(Collectors.toList());

        List<AttendanceRecord> attendanceRecords = userService.getAttendanceByUsername(username);
        Map<String, Map<String, String>> recordsMap = new HashMap<>();

        for (AttendanceRecord record : attendanceRecords) {
            Map<String, String> timeData = new HashMap<>();
            timeData.put("signInTime",UserRepository.formatTo12Hour(record.getSignInTime()));
            timeData.put("signOutTime",UserRepository.formatTo12Hour(record.getSignOutTime()));
            recordsMap.put(record.getDate().toString(), timeData);
        }

        model.addAttribute("username", username);
        model.addAttribute("dates", allDatesInMonth);
        model.addAttribute("records", recordsMap);

        return "attendanceReport";
    }

    @GetMapping("/adminPage")
    public String showAdminPage(Model model) {
        List<String> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "adminPage"; 
    }


}
