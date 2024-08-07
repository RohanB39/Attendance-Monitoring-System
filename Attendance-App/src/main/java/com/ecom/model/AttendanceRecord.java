package com.ecom.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    private LocalDate date;
    private LocalTime signInTime;
    private LocalTime signOutTime;
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(LocalTime signInTime) {
        this.signInTime = signInTime;
    }

    public LocalTime getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(LocalTime signOutTime) {
        this.signOutTime = signOutTime;
    }
}
