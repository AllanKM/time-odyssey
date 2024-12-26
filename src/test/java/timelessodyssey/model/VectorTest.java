package timelessodyssey.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testVectorInitialization() {
        // Arrange
        double x = 10.5;
        double y = 20.5;

        // Act
        Vector vector = new Vector(x, y);

        // Assert
        assertNotNull(vector, "Vector should not be null");
        assertEquals(x, vector.x(), "X coordinate should match the initialized value");
        assertEquals(y, vector.y(), "Y coordinate should match the initialized value");
    }

    @Test
    void testVectorEquality() {
        // Arrange
        Vector vector1 = new Vector(10.5, 20.5);
        Vector vector2 = new Vector(10.5, 20.5);
        Vector vector3 = new Vector(5.0, 15.0);

        // Act & Assert
        assertEquals(vector1, vector2, "Vectors with same values should be equal");
        assertNotEquals(vector1, vector3, "Vectors with different values should not be equal");
    }

    @Test
    void testVectorHashCode() {
        // Arrange
        Vector vector1 = new Vector(10.5, 20.5);
        Vector vector2 = new Vector(10.5, 20.5);
        Vector vector3 = new Vector(5.0, 15.0);

        // Act & Assert
        assertEquals(vector1.hashCode(), vector2.hashCode(), "Hash codes should match for equal vectors");
        assertNotEquals(vector1.hashCode(), vector3.hashCode(), "Hash codes should differ for non-equal vectors");
    }

    @Test
    void testVectorToString() {
        // Arrange
        Vector vector = new Vector(10.5, 20.5);

        // Act
        String vectorString = vector.toString();

        // Assert
        assertEquals("Vector[x=10.5, y=20.5]", vectorString, "String representation should match the expected format");
    }
}
