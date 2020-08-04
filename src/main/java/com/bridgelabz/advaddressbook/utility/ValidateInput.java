package com.bridgelabz.advaddressbook.utility;

public class ValidateInput {
    public String getCOMMON_PATTERN() {
        return "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{2,}$";
    }

    public String getZIP_PATTERN() {
        return "^[1-9][0-9]{5}$";
    }

    public String getPHONE_NUMBER_PATTERN() {
        return "^[1-9][0-9]{9}$";
    }
}