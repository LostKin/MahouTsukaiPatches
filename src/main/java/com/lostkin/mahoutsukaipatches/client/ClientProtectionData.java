package com.lostkin.mahoutsukaipatches.client;

public class ClientProtectionData {

    private static boolean protectionUnlocked = false;
    private static boolean protectionStatus = false;

    public static void unlockProtection() {
        protectionUnlocked = true;
    }

    public static boolean getProtectionUnlocked() {
        return protectionUnlocked;
    }

    public static void setProtectionStatus(boolean status) {
        protectionStatus = status;
    }

    public static boolean getProtectionStatus() {
        return  protectionStatus;
    }

}
