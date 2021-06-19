package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.string.AllowedValues;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class TestAllowedValues
{

    private AllowedValues allowedValues;

    @Before
    public void setup()
    {
        String defaultValue = "default";
        allowedValues = new AllowedValues(defaultValue, true, "default", "allowed", "string");
    }

    private String validateString(String validate) throws Exception
    {
        Object result = allowedValues.validate( validate );
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
        Object validated = allowedValues.validate(48);
        assert validated instanceof String;
        assert validated.equals("default");
    }
}
