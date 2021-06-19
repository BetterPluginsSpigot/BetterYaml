package be.dezijwegel.betteryaml.testplugin;

import be.dezijwegel.betteryaml.OptionalBetterYaml;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        super.onEnable();
        OptionalBetterYaml betterYaml = new OptionalBetterYaml("config.yml", this);
    }
}
