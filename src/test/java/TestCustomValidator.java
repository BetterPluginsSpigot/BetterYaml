import be.dezijwegel.betteryaml.validation.ValidationHandler;
import be.dezijwegel.betteryaml.validation.validator.numeric.Max;
import be.dezijwegel.betteryaml.validation.validator.numeric.Min;
import be.dezijwegel.betteryaml.validation.validator.numeric.Range;
import be.dezijwegel.betteryaml.validation.validator.string.StringWhiteList;
import be.dezijwegel.betteryaml.validation.validator.string.ToLowerCase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestCustomValidator
{

    private ValidationHandler validationHandler;

    @Before
    public void setup()
    {
        validationHandler = new ValidationHandler()
            .addValidator("min", new Min(5))
            .addValidator("max", new Max(10))
            .addValidator("range", new Range(10, 20))
            .addValidator("allowed_values", new StringWhiteList("default", false, "AlLoWed", "GoOd Too"))
            .addValidator("lowercase", new ToLowerCase());
    }

    @Test
    public void testValidation()
    {
        Map<String, Object> config = new HashMap<>();
        config.put("min", 2);
        config.put("max", 12);
        config.put("range", 35);
        config.put("allowed_values", "allowEd");
        config.put("lowercase", "LOWERCASE");

        validationHandler.validateConfiguration( config );

        assert (int) config.get("min") == 5;
        assert (int) config.get("max") == 10;
        assert (int) config.get("range") == 20;
        assert Objects.equals(config.get("allowed_values"), "default");
        assert Objects.equals(config.get("lowercase"), "lowercase");
    }

}
