package com.lostkin.mahoutsukaipatches.client;

public class ClientEyeData {
    private static boolean eyesTypeDefined = false;
    private static boolean eyesStatus = false;

    public static void setEyesType() {
        eyesTypeDefined = true;
    }

    public static boolean getEyesTypeDefined() {
        return eyesTypeDefined;
    }

    public static void setEyesStatus(boolean status) {
        eyesStatus = status;
    }

    public static boolean getEyesStatus() {
        return  eyesStatus;
    }

}
