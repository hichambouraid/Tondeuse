package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Trimmer {
    private static final Logger logger = LoggerFactory.getLogger(Trimmer.class);

    public static final char G = 'G';// Turn left
    public static final char D = 'D';// Turn right
    public static final char A = 'A';// Proceed

    public static void main(String[] args) {
        // Run the program with the input file passed as argument
        String input = args[0];
        logger.info("Start Trimming with the file: {}", input);
        List<String> results = executeTrimmer(input);
        results.forEach(System.out::println);
    }

    public static List<String> executeTrimmer(String inputFile) {
        List<String> results = new ArrayList<>();
        Position coordonate = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            // Read grass dimensions
            String line = reader.readLine();
            if (line != null) {
                String[] grassDimensions = line.split(" ");
                coordonate = new Position(Integer.parseInt(grassDimensions[0]), Integer.parseInt(grassDimensions[1]));
                logger.info("Grass dimensions: width={}, height={}", coordonate.width, coordonate.height);
            }
            // Process each line of initial position and orders
            while ((line = reader.readLine()) != null) {
                String[] initialPosition = line.split(" ");
                Location location = new Location(Integer.parseInt(initialPosition[0]), Integer.parseInt(initialPosition[1]), Direction.fromChar(initialPosition[2].charAt(0)));
                String orders = reader.readLine();
                if (orders != null) {
                    // Apply the orders and add the new position to the results list
                    location = performTrimmerOrders(coordonate, location, orders);
                    results.add(location.toString());
                    logger.info("Processed location: {}", location);
                }
            }
        } catch (Exception e) {
            // Read exceptions
            logger.error("Error reading file '{}': {}", inputFile, e.getMessage(), e);
        }
        return results;
    }

    // Method for applying orders in position
    public static Location performTrimmerOrders(Position position, Location location, String orders) {
        for (char order : orders.toCharArray()) {
            switch (order) {
                case G -> {
                    location.direction = turnLeft(location.direction);
                    logger.debug("Turned left: {}", location.direction);
                }
                case D -> {
                    location.direction = turnRight(location.direction);
                    logger.debug("Turned right: {}", location.direction);
                }
                case A -> {
                    Location newLocation = proceed(location);
                    if (isWithinLimits(position, newLocation)) {
                        location = newLocation;
                        logger.debug("Moved forward: {}", location);
                    } else {
                        logger.warn("Move out of bounds: {}", newLocation);
                    }
                }
            }
        }
        return location;
    }

    public static Direction turnLeft(Direction direction) {
        return switch (direction) {
            case N -> Direction.W;
            case W -> Direction.S;
            case S -> Direction.E;
            case E -> Direction.N;
        };
    }

    public static Direction turnRight(Direction direction) {
        return switch (direction) {
            case N -> Direction.E;
            case E -> Direction.S;
            case S -> Direction.W;
            case W -> Direction.N;
        };
    }

    // Move in current direction
    public static Location proceed(Location location) {
        return switch (location.direction) {
            case N -> new Location(location.x, location.y + 1, location.direction);
            case E -> new Location(location.x + 1, location.y, location.direction);
            case S -> new Location(location.x, location.y - 1, location.direction);
            case W -> new Location(location.x - 1, location.y, location.direction);
        };
    }

    // Check if the new position is within limits
    public static boolean isWithinLimits(Position position, Location location) {
        boolean withinLimits =  location.x >= 0 && location.x <= position.width
                && location.y >= 0
                && location.y <= position.height;
        if (!withinLimits) {
            logger.warn("Location out of bounds: {}", location);
        }
        return withinLimits;
    }
}