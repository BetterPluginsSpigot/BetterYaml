package be.dezijwegel.betteryaml.testplugin;

import be.dezijwegel.betteryaml.OptionalBetterYaml;
import be.dezijwegel.betteryaml.validation.ValidationHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        super.onEnable();
        ValidationHandler validationHandler = new ValidationHandler()
            .addOptionalSection("optional")
            .setOptionalValue("optional.test", true)
            .setOptionalValue("optional.value", "Yes sir!");
        OptionalBetterYaml betterYaml = new OptionalBetterYaml("config.yml", validationHandler, this);
    }
}
