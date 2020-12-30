package org.moara.splitter.areaProcessor;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.utils.Area;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaTest {

    @Test(expected = IllegalArgumentException.class)
    public void TestNegativeIndex() {
        new Area(-1 , 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputInvalidIndex() {
        new Area(6, 4);
    }

    @Test
    public void testCreateAreaWithString() {
        Area area = new Area("5", "9");
        Assert.assertEquals(area.getBegin(), 5);
        Assert.assertEquals(area.getEnd(), 9);
    }

    @Test
    public void testCreateAreaWithMatcher() {
        String text = "Hello world 25304 !!!";
        String numberPatternString = "[0-9]+";
        Pattern numberPattern = Pattern.compile(numberPatternString);
        Matcher matcher = numberPattern.matcher(text);
        while (matcher.find()) {
            Area area = new Area(matcher);

            Assert.assertEquals(area.getBegin(), 12);
            Assert.assertEquals(area.getEnd(), 17);
        }


    }

    @Test
    public void testOverlap() {
        Area leftArea = new Area(1,5);
        Area centerArea = new Area(2,7);
        Area smallCenterArea = new Area(3,6);
        Area rightArea = new Area(6,9);
        Area otherArea = new Area(9,13);

        Assert.assertTrue(leftArea.isOverlap(centerArea));
        Assert.assertTrue(rightArea.isOverlap(centerArea));
        Assert.assertTrue(centerArea.isOverlap(smallCenterArea));
        Assert.assertTrue(smallCenterArea.isOverlap(centerArea));
        Assert.assertFalse(rightArea.isOverlap(otherArea));

    }

    @Test
    public void testEquals() {
        Area a1 = new Area(1,3);
        Area a2 = new Area(1,3);

        Area a3 = new Area(1,2);

        Assert.assertEquals(a1, a1);
        Assert.assertEquals(a2, a2);
        Assert.assertEquals(a1, a2);
        Assert.assertNotEquals(a1, a3);
    }

    @Test
    public void testContains() {
        Area area = new Area(5, 8);
        int point1 = 6;
        int point2 = 5;
        int point3 = 2;
        int point4 = 9;

        Assert.assertTrue(area.contains(point1));
        Assert.assertTrue(area.contains(point2));
        Assert.assertFalse(area.contains(point3));
        Assert.assertFalse(area.contains(point4));

    }

    @Test
    public void testHash() {
        Area area1 = new Area(5, 8);
        Area area2 = new Area(5, 8);
        Area area3 = new Area(8, 9);

        Assert.assertEquals(area1.hashCode(), area2.hashCode());
        Assert.assertNotEquals(area3.hashCode(), area2.hashCode());
    }

}