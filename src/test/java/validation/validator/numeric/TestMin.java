package validation.validator.numeric;


import be.dezijwegel.betteryaml.validation.validator.numeric.Min;
import org.junit.Before;
import org.junit.Test;
import validation.util.EqualsUtil;

/**
 * The type Test min.
 */
public class TestMin
{

    private Min min;

    /**
     * Sets .
     */
    @Before
    public void setup()
    {
        min = new Min(12);
    }

    /**
     * Test smaller.
     */
    @Test
    public void testSmaller()
    {
        Object value = min.validate(11);
        assert EqualsUtil.equals(value, 12);

        value = min.validate(11d);
        assert EqualsUtil.equals(value, 12);

        value = min.validate(11f);
        assert EqualsUtil.equals(value, 12);
    }

    /**
     * Test equal.
     */
    @Test
    public void testEqual()
    {
        Object value = min.validate(12);
        assert EqualsUtil.equals(value, 12);

        value = min.validate(12d);
        assert EqualsUtil.equals(value, 12d);

        value = min.validate(12f);
        assert EqualsUtil.equals(value, 12f);
    }

    /**
     * Test bigger.
     */
    @Test
    public void testBigger()
    {
        Object value = min.validate(13);
        assert EqualsUtil.equals(value, 13);

        value = min.validate(13d);
        assert EqualsUtil.equals(value, 13d);

        value = min.validate(13f);
        assert EqualsUtil.equals(value, 13f);
    }


    /**
     * Test string.
     */
    @Test
    public void testString()
    {
        Object value = min.validate("This is illegal!");
        assert EqualsUtil.equals(value, 12);

        value = min.validate("13");
        assert EqualsUtil.equals(value, 12);
    }

}
