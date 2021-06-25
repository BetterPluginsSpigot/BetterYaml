package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.string.ToLowerCase;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Test to lower case.
 */
public class TestToLowerCase
{

    private ToLowerCase toLowerCase;

    /**
     * Sets .
     */
    @Before
    public void setup()
    {
        this.toLowerCase = new ToLowerCase();
    }

    /**
     * Test string.
     */
    @Test
    public void testString()
    {
        Object result = this.toLowerCase.validate("I DoN't nEeD tO WrItE teSts bECauSe i Am SmArT");
        assert result instanceof String;
        assert result.equals("i don't need to write tests because i am smart");
    }

    /**
     * Test faulty.
     */
    @Test
    public void testFaulty()
    {
        Object result = this.toLowerCase.validate(28);
        assert result instanceof String;
        assert result.equals("");
    }

}
