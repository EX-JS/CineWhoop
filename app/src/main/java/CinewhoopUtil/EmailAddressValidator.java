package CinewhoopUtil;

import java.util.regex.Pattern;

/**
 * Created by jagteshwar on 17-02-2016.
 */
public class EmailAddressValidator {

    private static final String domainChars = "a-z0-9\\-";
    private static final String atomChars = "a-z0-9\\Q!#$%&'*+-/=?^_`{|}~\\E";
    private static final String emailRegex = "^" + dot(atomChars) + "@" + dot(domainChars) + "$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);

    private static String dot(String chars) {
        return "[" + chars + "]+(?:\\.[" + chars + "]+)*";
    }

    public  boolean isValidEmailAddress(String address) {
        return address != null && emailPattern.matcher(address).matches();
    }

}
