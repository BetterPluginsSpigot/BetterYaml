package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.string.LowerCase;
import org.junit.Before;
import org.junit.Test;

public class TestLowerCase
{

    private LowerCase lowerCase;

    @Before
    public void setup()
    {
        this.lowerCase = new LowerCase();
    }

    @Test
    public void testString()
    {
        Object result = this.lowerCase.validate("I DoN't nEeD tO WrItE teSts bECauSe i Am SmArT");
        assert result instanceof String;
        assert result.equals("i don't need to write tests because i am smart");
    }

    @Test
    public void testFaulty()
    {
        Object result = this.lowerCase.validate(28);
        assert result instanceof String;
        assert result.equals("");
    }

}
