package org.example;

public enum Direction {
    N, E, S, W;

    public static Direction fromChar(char c) {
        return switch (c) {
            case 'N' -> N;
            case 'E' -> E;
            case 'S' -> S;
            case 'W' -> W;
            default -> throw new IllegalArgumentException("Unknown direction: " + c);
        };
    }
}