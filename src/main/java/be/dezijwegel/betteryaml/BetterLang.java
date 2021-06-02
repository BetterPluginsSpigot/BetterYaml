package be.dezijwegel.betteryaml;

import be.dezijwegel.betteryaml.interfaces.IOptionalConfigReader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class BetterLang implements IOptionalConfigReader
{

    private final File file;
    private final YamlConfiguration yamlConfiguration;

    /**
     * Easily manage localised language files
     * The language template must be located in the /templates folder
     * Any default language contents must be located in the /lang folder
     * Logging is disabled
     *
     * @param name The name of the language (/lang) file, which is equal to the name of the template in the templates folder
     * @param plugin The relevant JavaPlugin
     */
    public BetterLang(String name, JavaPlugin plugin)
    {
        this(name, name, plugin, false);
    }


    /**
     * Easily manage localised language files
     * The language template must be located in the /templates folder
     * Any default language contents must be located in the /lang folder
     * Logging is disabled
     *
     * @param template The name of the template in the /templates folder
     * @param localised The name of the language file, located in the /lang folder
     * @param plugin The relevant JavaPlugin
     */
    public BetterLang(String template, String localised, JavaPlugin plugin)
    {
        this(template, localised, plugin, false);
    }


    /**
     * Easily manage localised language files
     * The language template must be located in the /templates folder
     * Any default language contents must be located in the /lang folder
     *
     * @param template The name of the template in the /templates folder
     * @param localised The name of the language file, located in the /lang folder
     * @param plugin The relevant JavaPlugin
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     */
    public BetterLang(String template, String localised, JavaPlugin plugin, boolean doLogging)
    {
        BetterYaml betterYaml = null;
        try {
            betterYaml = new BetterYaml(template, localised, "lang/", plugin, doLogging);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Any of these is null? Something went wrong -> empty!
        if (betterYaml == null || betterYaml.getFile() == null || betterYaml.getYamlConfiguration() == null)
        {
            this.file = null;
            this.yamlConfiguration = null;
        }
        else
        {
            this.file = betterYaml.getFile();
            this.yamlConfiguration = betterYaml.getYamlConfiguration();
        }
    }

    /**
     * Get the loaded File. You can check if any error has occurred with Optional#isEmpty().
     * First, see if it is loaded correctly with Optional#isPresent(), this ensures a non-null value
     * Then, you can get the File instance with Optional#get()
     *
     * @return an optional file, never null. But no value will be present if an Exception was thrown while reading the configuration
     */
    public Optional<File> getFile()
    {
        return file != null ? Optional.of( file ) : Optional.empty();
    }


    /**
     * Get the loaded YamlConfiguration. You can check if any error has occurred with Optional#isEmpty().
     * First, see if it is loaded correctly with Optional#isPresent(), this ensures a non-null value
     * Then, you can get the YamlConfiguration instance with Optional#get()
     *
     * @return an optional YamlConfiguration, never null. But no value will be present if an Exception was thrown while reading the configuration
     */
    public Optional<YamlConfiguration> getYamlConfiguration()
    {
        return yamlConfiguration != null ? Optional.of( yamlConfiguration ) : Optional.empty();
    }
}
