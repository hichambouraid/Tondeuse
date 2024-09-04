package org.example;

public class Location {
        public int x, y;
        public Direction direction;

        public Location(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return x + " " + y + " " + direction;
        }
    }