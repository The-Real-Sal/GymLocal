package com.endevex.gymlocal.utils;

/**
 * Used to easily validate emails using regex.
 * Referencing where Regex pattern and methods were obtained from.
 * https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 * Created by Sal on 6/10/17.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        boolean ans = matcher.matches();
        return ans;

    }
}