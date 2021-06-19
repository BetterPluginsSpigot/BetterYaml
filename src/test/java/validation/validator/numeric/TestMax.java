package validation.validator.numeric;


import be.dezijwegel.betteryaml.validation.validator.numeric.Max;
import org.junit.Before;
import org.junit.Test;
import validation.util.EqualsUtil;

public class TestMax
{

    private Max max;

    @Before
    public void setup()
    {
        this.max = new Max(10d);
    }

    @Test
    public void testSmaller()
    {
        Object value = max.validate(9);
        assert EqualsUtil.equals(value, 9);

        value = max.validate(9d);
        assert EqualsUtil.equals(value, 9d);

        value = max.validate(9f);
        assert EqualsUtil.equals(value, 9f);
    }

    @Test
    public void testEqual()
    {
        Object value = max.validate(10);
        assert EqualsUtil.equals(value, 10);

        value = max.validate(10d);
        assert EqualsUtil.equals(value, 10d);

        value = max.validate(10f);
        assert EqualsUtil.equals(value, 10f);
    }

    @Test
    public void testBigger()
    {
        Object value = max.validate(11);
        assert EqualsUtil.equals(value, 10d);

        value = max.validate(11d);
        assert EqualsUtil.equals(value, 10d);

        value = max.validate(11f);
        assert EqualsUtil.equals(value, 10d);
    }

    @Test
    public void testString()
    {
        Object value = max.validate("This is illegal!");
        assert EqualsUtil.equals(value, 10d);

        value = max.validate("13");
        assert EqualsUtil.equals(value, 10d);
    }
}
