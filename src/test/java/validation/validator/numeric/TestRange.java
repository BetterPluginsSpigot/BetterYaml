package validation.validator.numeric;

import be.dezijwegel.betteryaml.util.NumbersUtil;
import be.dezijwegel.betteryaml.validation.validator.numeric.Range;
import org.junit.Before;
import org.junit.Test;
import validation.util.EqualsUtil;

/**
 * The type Test range.
 */
public class TestRange
{

    private int minValue;
    private double maxValue;

    private Range range;

    /**
     * Sets .
     */
    @Before
    public void setup()
    {
        minValue = 5;
        maxValue = 10.2;
        range = new Range(minValue, maxValue);
    }

    /**
     * Test smaller.
     */
    @Test
    public void testSmaller()
    {
        Object value = range.validate(4.9);
        assert EqualsUtil.equals(value, 5);
    }

    /**
     * Test in range.
     */
    @Test
    public void testInRange()
    {
        Object value = range.validate(7f);
        assert EqualsUtil.equals(value, 7f);
    }

    /**
     * Test bigger.
     */
    @Test
    public void testBigger()
    {
        Object value = range.validate(13);
        assert EqualsUtil.equals(value, 10.2d);
    }


    /**
     * Test string.
     */
    @Test
    public void testString()
    {
        Object value = range.validate("This is illegal!");
        assert value instanceof Number;
        assert NumbersUtil.compare( (Number) value, minValue) >= 0;
        assert NumbersUtil.compare( (Number) value, maxValue) <= 0;
    }

}
