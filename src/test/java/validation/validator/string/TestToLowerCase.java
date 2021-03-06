package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.string.ToLowerCase;
import org.junit.Before;
import org.junit.Test;

public class TestToLowerCase
{

    private ToLowerCase toLowerCase;

    @Before
    public void setup()
    {
        this.toLowerCase = new ToLowerCase();
    }

    @Test
    public void testString()
    {
        Object result = this.toLowerCase.validate("I DoN't nEeD tO WrItE teSts bECauSe i Am SmArT");
        assert result instanceof String;
        assert result.equals("i don't need to write tests because i am smart");
    }

    @Test
    public void testFaulty()
    {
        Object result = this.toLowerCase.validate(28);
        assert result instanceof String;
        assert result.equals("");
    }

}
