import be.dezijwegel.betteryaml.validation.CustomValidation;
import be.dezijwegel.betteryaml.validation.validator.numeric.Max;
import be.dezijwegel.betteryaml.validation.validator.numeric.Min;
import be.dezijwegel.betteryaml.validation.validator.numeric.Range;
import be.dezijwegel.betteryaml.validation.validator.string.AllowedValues;
import be.dezijwegel.betteryaml.validation.validator.string.LowerCase;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class TestCustomValidator
{

    private CustomValidation customValidation;

    @Before
    public void setup()
    {
        customValidation = new CustomValidation()
            .addValidator("min", new Min(5))
            .addValidator("max", new Max(10))
            .addValidator("range", new Range(10, 20))
            .addValidator("allowed_values", new AllowedValues("default", false, "AlLoWed", "GoOd Too"))
            .addValidator("lowercase", new LowerCase());
    }

    @Test
    public void testValidation()
    {
        YamlConfiguration config = new YamlConfiguration();
        config.set("min", 2);
        config.set("max", 12);
        config.set("range", 35);
        config.set("allowed_values", "allowEd");
        config.set("lowercase", "LOWERCASE");

        customValidation.validateConfiguration( config );

        assert config.getInt("min") == 5;
        assert config.getInt("max") == 10;
        assert config.getInt("range") == 20;
        assert Objects.equals(config.getString("allowed_values"), "default");
        assert Objects.equals(config.getString("lowercase"), "lowercase");
    }

}
