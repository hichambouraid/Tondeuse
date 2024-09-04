import org.example.Direction;
import org.example.Location;
import org.example.Position;
import org.example.Trimmer;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrimmerTest {

    @Test
    public void testExecuteTrimmer() throws Exception {
        Path tempFile = Files.createTempFile("input", ".txt");
        Files.writeString(tempFile, "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA");

        List<String> results = Trimmer.executeTrimmer(tempFile.toString());
        assertEquals(2, results.size());
        assertEquals("1 3 N", results.get(0));
        assertEquals("5 1 E", results.get(1));

        Files.delete(tempFile);
    }

    @Test
    public void testIsWithinCoordinate() {
        Position position = new Position(5, 5);
        Location locationTrue = new Location(2, 2, Direction.N);
        Location locationFalse = new Location(8, 9, Direction.N);

        assertTrue(Trimmer.isWithinLimits(position, locationTrue));
        assertFalse(Trimmer.isWithinLimits(position, locationFalse));
    }

    @Test
    public void testTurnLeft() {
        assertEquals(Direction.N, Trimmer.turnLeft(Direction.E));
        assertEquals(Direction.E, Trimmer.turnLeft(Direction.S));
        assertEquals(Direction.S, Trimmer.turnLeft(Direction.W));
        assertEquals(Direction.W, Trimmer.turnLeft(Direction.N));

    }

    @Test
    public void testTurnRight() {
        assertEquals(Direction.S, Trimmer.turnRight(Direction.E));
        assertEquals(Direction.N, Trimmer.turnRight(Direction.W));
        assertEquals(Direction.W, Trimmer.turnRight(Direction.S));
        assertEquals(Direction.E, Trimmer.turnRight(Direction.N));
    }

    @Test
    public void testFromChar() {
        assertEquals(Direction.S, Direction.fromChar('S'));
        assertEquals(Direction.N, Direction.fromChar('N'));
        assertEquals(Direction.W, Direction.fromChar('W'));
        assertEquals(Direction.E, Direction.fromChar('E'));
        assertThrows(IllegalArgumentException.class, () -> {
            Direction.fromChar('A');
        });
    }
}
