package org.example;

class CheckingUtil {
    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isValidName(String str) {
        String nameRegex = "^[А-Яа-яЁё]+ [А-Яа-яЁё]\\.[А-Яа-яЁё]\\.$";
        return str.matches(nameRegex);
    }

    static boolean isValidPosition(String str) {
        String organizationRegex = "^[A-Za-zА-Яа-я\\s]+$";
        return str.matches(organizationRegex);
    }

    static boolean isValidOrganization(String str) {
        String organizationRegex = "^[A-Za-zА-Яа-я\\s]+$";
        return str.matches(organizationRegex);
    }

    static boolean isValidEmail(String str) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return str.matches(emailRegex);
    }

    static boolean isValidPhone(String str) {
        String phoneRegex = "^(\\d{1,3}-\\d{1,3}-\\d{1,3}-\\d{1,3}-\\d{1,3}|\\d{1,3}-\\d{1,3}-\\d{1,3})$";
        return str.matches(phoneRegex);
    }
}
