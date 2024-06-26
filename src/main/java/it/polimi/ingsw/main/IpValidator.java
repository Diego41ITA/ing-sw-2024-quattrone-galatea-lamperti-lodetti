package it.polimi.ingsw.main;

public class IpValidator {
    /**
     * @param ip the string of te ip address
     * @return  true if the ip is valid, false otherwise.
     */
    public static boolean verifyIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex) || ip.equals("localhost");
    }

    public static boolean containsOneOrTwoAndHasLengthOne(String input) {
        if (input == null) {
            return false;
        }
        return input.length() == 1 && (input.equals("1") || input.equals("2"));
    }
}