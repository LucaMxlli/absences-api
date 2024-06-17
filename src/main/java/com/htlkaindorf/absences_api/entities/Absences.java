package com.htlkaindorf.absences_api.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "absences")
@IdClass(Absences.AbsenceId.class)
public class Absences {

    @Id
    @Column(name = "absence_date", nullable = false)
    private LocalDate date;

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private int hoursAbsent;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public static class AbsenceId implements Serializable {
        private LocalDate date;
        private Integer userId;

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHoursAbsent() {
        return hoursAbsent;
    }

    public void setHoursAbsent(int hoursAbsent) {
        this.hoursAbsent = hoursAbsent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
