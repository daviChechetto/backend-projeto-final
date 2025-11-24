package com.example.chessapi.util;

public class LatencySimulator {

    public static void delay(long millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException ignored) {}
    }
}
