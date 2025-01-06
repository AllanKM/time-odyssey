package timelessodyssey.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    public void testVectorInitialization() {
        double x = 10.5;
        double y = 20.5;
        Vector vector = new Vector(x, y);
        assertNotNull(vector, "The Vector should not be null");
        assertEquals(x, vector.x(), "The X coordinate should match the initialized value");
        assertEquals(y, vector.y(), "The Y coordinate should match the initialized value");
    }

    @Test
    public void testVectorEquality() {
        Vector vector1 = new Vector(10.5, 20.5);
        Vector vector2 = new Vector(10.5, 20.5);
        Vector vector3 = new Vector(5.0, 15.0);
        assertEquals(vector1, vector2, "The Vectors with same values should be equal");
        assertNotEquals(vector1, vector3, "The Vectors with different values should not be equal");
    }

    @Test
    public void testVectorHashCode() {
        Vector vector1 = new Vector(10.5, 20.5);
        Vector vector2 = new Vector(10.5, 20.5);
        Vector vector3 = new Vector(5.0, 15.0);

        assertEquals(vector1.hashCode(), vector2.hashCode(), "The Hash codes should match for equal vectors");
        assertNotEquals(vector1.hashCode(), vector3.hashCode(), "The Hash codes should differ for non-equal vectors");
    }

    @Test
    public void testVectorToString() {
        Vector vector = new Vector(10.5, 20.5);
        String vectorString = vector.toString();
        assertEquals("Vector[x=10.5, y=20.5]", vectorString, "The String representation should match the expected format");
    }
}
