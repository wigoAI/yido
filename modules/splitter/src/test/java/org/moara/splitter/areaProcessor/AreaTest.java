package org.moara.splitter.areaProcessor;

import org.junit.jupiter.api.Test;
import org.moara.splitter.utils.Area;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class AreaTest {

    @Test
    public void TestNegativeIndex() {
        assertThrows(IllegalArgumentException.class, () -> new Area(-1, 4));

    }

    @Test
    public void testInputInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> new Area(6, 4));
    }

    @Test
    public void testCreateAreaWithString() {
        Area area = new Area("5", "9");
        assertEquals(area.getBegin(), 5);
        assertEquals(area.getEnd(), 9);
    }

    @Test
    public void testCreateAreaWithMatcher() {
        String text = "Hello world 25304 !!!";
        String numberPatternString = "[0-9]+";
        Pattern numberPattern = Pattern.compile(numberPatternString);
        Matcher matcher = numberPattern.matcher(text);
        while (matcher.find()) {
            Area area = new Area(matcher);

            assertEquals(area.getBegin(), 12);
            assertEquals(area.getEnd(), 17);
        }


    }

    @Test
    public void testOverlap() {
        Area leftArea = new Area(1,5);
        Area centerArea = new Area(2,7);
        Area smallCenterArea = new Area(3,6);
        Area rightArea = new Area(6,9);
        Area otherArea = new Area(9,13);

        assertTrue(leftArea.isOverlap(centerArea));
        assertTrue(rightArea.isOverlap(centerArea));
        assertTrue(centerArea.isOverlap(smallCenterArea));
        assertTrue(smallCenterArea.isOverlap(centerArea));
        assertFalse(rightArea.isOverlap(otherArea));

    }

    @Test
    public void testEquals() {
        Area a1 = new Area(1,3);
        Area a2 = new Area(1,3);

        Area a3 = new Area(1,2);

        assertEquals(a1, a1);
        assertEquals(a2, a2);
        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
    }

    @Test
    public void testContains() {
        Area area = new Area(5, 8);
        int point1 = 6;
        int point2 = 5;
        int point3 = 2;
        int point4 = 9;

        assertTrue(area.contains(point1));
        assertTrue(area.contains(point2));
        assertFalse(area.contains(point3));
        assertFalse(area.contains(point4));

    }

    @Test
    public void testHash() {
        Area area1 = new Area(5, 8);
        Area area2 = new Area(5, 8);
        Area area3 = new Area(8, 9);

        assertEquals(area1.hashCode(), area2.hashCode());
        assertNotEquals(area3.hashCode(), area2.hashCode());
    }

}