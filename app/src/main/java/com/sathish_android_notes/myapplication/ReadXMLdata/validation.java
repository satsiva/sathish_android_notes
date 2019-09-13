package com.sathish_android_notes.myapplication.ReadXMLdata;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by hai on 6/19/2015.
 */
public class validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "^[+]?[0-9]{4,13}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9+_.]{4,15}";
    private static final String NUMBER_REGEX = "^[0-9]*$";

    // Error Messages
    private static final String REQUIRED_MSG = "This field is required";
    private static final String EMAIL_MSG = "Invalid E-mail";
    private static final String PHONE_MSG = "Invalid Phone number";
    private static final String PASSWORD_MSG = "Password contains minimum 4 letters";
    private static final String CONFIRMPASS_MSG = "Password doesn't match";
    private static final String NUMBER_MSG = "Please enter digits";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isPassword(EditText editText, boolean required) {
        return isValid(editText, PASSWORD_REGEX, PASSWORD_MSG, required);
    }


    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean isNumber(EditText editText, boolean required) {
        return isValid(editText, NUMBER_REGEX, NUMBER_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean isValidPassword(EditText pass, EditText repeat) {
        boolean pstatus = false;
        String pass1 = pass.getText().toString().trim();
        String pass2 = repeat.getText().toString().trim();
        if (pass2 != null && pass1 != null) {
            if (pass1.equals(pass2)) {
                pstatus = true;
            } else {
                pass.setError("Password Does Not Match");
            }
        }
        return pstatus;
    }

}