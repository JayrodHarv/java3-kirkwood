package edu.kirkwood.shared;

import edu.kirkwood.learnx.models.User;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Helpers {
    public static long ageInYears(String birthDay) {
        DateTimeFormatter formatter = null;
        LocalDate birthDate = null;
        try {
            formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.US);
            birthDate = LocalDate.parse(birthDay, formatter);
        } catch(DateTimeParseException e) {
            try {
                formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
                birthDate = LocalDate.parse(birthDay, formatter);
            } catch(DateTimeParseException e2) {
                return 0;
            }
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static User getLearnXUserFromSession(HttpSession session) {
        return (User)session.getAttribute("activeUser");
    }

    public static User getSMPUserFromSession(HttpSession session) {
        return (User)session.getAttribute("activeSMPUser");
    }

    public static boolean isActive(User user) {
        return user.getStatus().equals("active");
    }

    public static boolean isTeacher(User user) {
        return user.getPrivileges().equals("teach");
    }
    public static boolean isStudent(User user) {
        return user.getPrivileges().equals("student");
    }

    public static boolean isAdmin(User user) {
        return user.getPrivileges().equals("admin");
    }

}
