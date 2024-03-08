package edu.kirkwood.shared;

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
}
