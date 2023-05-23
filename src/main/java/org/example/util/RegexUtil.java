package org.example.util;

public class RegexUtil {
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidName(String str) {
        String nameRegex = "^[А-Яа-яЁё]+ [А-Яа-яЁё]\\.[А-Яа-яЁё]\\.$";
        return str.matches(nameRegex);
    }

    public static boolean isValidPosition(String str) {
        String positionRegex = "^[A-Za-zА-Яа-я\\s]+$";
        return str.matches(positionRegex);
    }

    public static boolean isValidOrganization(String str) {
        String organizationRegex = "^[A-Za-zА-Яа-я\\s]+$";
        return str.matches(organizationRegex);
    }

    public static boolean isValidEmail(String str) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return str.matches(emailRegex);
    }

    public static boolean isValidPhone(String str) {
        String phoneRegex = "^(\\d{1,3}-\\d{1,3}-\\d{1,3}-\\d{1,3}-\\d{1,3}|\\d{1,3}-\\d{1,3}-\\d{1,3})$";
        return str.matches(phoneRegex);
    }
}
