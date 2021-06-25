package be.dezijwegel.betteryaml.testplugin;

import be.dezijwegel.betteryaml.OptionalBetterYaml;
import be.dezijwegel.betteryaml.validation.ValidationHandler;
import be.dezijwegel.betteryaml.validation.validator.numeric.Range;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Test plugin.
 */
public class TestPlugin extends JavaPlugin
{

    /**
     * On enable.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onEnable()
    {
        super.onEnable();
        ValidationHandler validationHandler = new ValidationHandler()
            .addOptionalSection("optional")
            .setOptionalValue("optional.test", true)
            .setOptionalValue("optional.value", "Yes sir!")
            .addValidator( "number", new Range(10, 21));
        OptionalBetterYaml betterYaml = new OptionalBetterYaml("config.yml", validationHandler, this);
    }
}
