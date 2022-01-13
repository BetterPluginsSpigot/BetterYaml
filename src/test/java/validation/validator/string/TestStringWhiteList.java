package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.ChainedValidator;
import be.dezijwegel.betteryaml.validation.validator.Validator;
import be.dezijwegel.betteryaml.validation.validator.string.StringWhiteList;
import be.dezijwegel.betteryaml.validation.validator.string.ToLowerCase;
import org.junit.Before;
import org.junit.Test;

public class TestStringWhiteList
{

    private StringWhiteList stringWhiteList;

    @Before
    public void setup()
    {
        String defaultValue = "default";
        stringWhiteList = new StringWhiteList(defaultValue, true, "default", "allowed", "string");
    }

    private String validateString(String validate) throws Exception
    {
        Object result = stringWhiteList.validate( validate );
        if (result instanceof String)
            return (String) result;
        else throw new Exception("The value : " + validate + " could not be validated to String. Result: " + result);
    }

    @Test
    public void testValid() throws Exception
    {
        assert validateString("dEfault").equals("dEfault");
        assert validateString("allowEd").equals("allowEd");
        assert validateString("strIng").equals("strIng");
    }

    @Test
    public void testInvalid() throws Exception
    {
        assert validateString("This is not an option").equals("default");
    }

    @Test
    public void testFaultyInput()
    {
        Object validated = stringWhiteList.validate(48);
        assert validated instanceof String;
        assert validated.equals("default");
    }

    @Test
    public void testChainedValidator() {
        Validator validator = new ChainedValidator(
            new StringWhiteList("percentage", true, "percentage", "absolute"),
            new ToLowerCase()
        );

        assert validator.validate("percentage").equals("percentage");
        assert validator.validate("Percentage").equals("percentage");
        assert validator.validate("obsolute").equals("percentage");

        assert validator.validate("absolute").equals("absolute");
        assert validator.validate("absoLute").equals("absolute");
    }
}
