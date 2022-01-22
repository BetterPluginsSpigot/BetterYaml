package be.dezijwegel.betteryaml.testplugin;

import be.dezijwegel.betteryaml.OptionalBetterYaml;
import be.dezijwegel.betteryaml.logging.BetterYamlLogger;
import be.dezijwegel.betteryaml.validation.ValidationHandler;
import be.dezijwegel.betteryaml.validation.validator.ChainedValidator;
import be.dezijwegel.betteryaml.validation.validator.numeric.Range;
import be.dezijwegel.betteryaml.validation.validator.string.StringWhiteList;
import be.dezijwegel.betteryaml.validation.validator.string.ToLowerCase;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class TestPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        super.onEnable();
//        BetterYamlLogger.setLogLevel(Level.FINER);
        ValidationHandler validationHandler = new ValidationHandler()
            .addValidator( "number", new Range(10, 21))
            .addValidator("validatedString", new ChainedValidator(new StringWhiteList("one", true, "one", "two", "three"), new ToLowerCase()))
            .addOptionalSection("optional")
            .setOptionalValue("optional.test", true)
            .setOptionalValue("optional.value", "Yes sir!");
        OptionalBetterYaml betterYaml = new OptionalBetterYaml("config.yml", validationHandler, this, true);
    }
}
