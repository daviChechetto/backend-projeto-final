package com.example.chessapi.util;

public class EloCalculator {

    private static int kFactor(int rating) {
        if (rating < 1600) return 40;
        if (rating < 2000) return 20;
        return 10;
    }

    private static double expectedScore(int ratingA, int ratingB) {
        return 1.0 / (1.0 + Math.pow(10.0, (ratingB - ratingA) / 400.0));
    }

    public static int newRating(int current, int opponent, double score) {
        double expected = expectedScore(current, opponent);
        int k = kFactor(current);
        return (int) Math.round(current + k * (score - expected));
    }
}