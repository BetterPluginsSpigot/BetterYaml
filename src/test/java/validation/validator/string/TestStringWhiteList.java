package validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.string.StringWhiteList;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Test string white list.
 */
public class TestStringWhiteList
{

    private StringWhiteList stringWhiteList;

    /**
     * Sets .
     */
    @Before
    public void setup()
    {
        String defaultValue = "default";
        stringWhiteList = new StringWhiteList(defaultValue, true, "default", "allowed", "string");
    }

    /**
     * Validate string string.
     *
     * @param validate the validate
     * @return the string
     * @throws Exception the exception
     */
    private @NotNull String validateString(@NotNull String validate) throws Exception
    {
        Object result = stringWhiteList.validate( validate );
        if (result instanceof String)
            return (String) result;
        else throw new Exception("The value : " + validate + " could not be validated to String. Result: " + result);
    }

    /**
     * Test valid.
     *
     * @throws Exception the exception
     */
    @Test
    public void testValid() throws Exception
    {
        assert validateString("dEfault").equals("dEfault");
        assert validateString("allowEd").equals("allowEd");
        assert validateString("strIng").equals("strIng");
    }

    /**
     * Test invalid.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInvalid() throws Exception
    {
        assert validateString("This is not an option").equals("default");
    }

    /**
     * Test faulty input.
     */
    @Test
    public void testFaultyInput()
    {
        Object validated = stringWhiteList.validate(48);
        assert validated instanceof String;
        assert validated.equals("default");
    }
}
