package be.dezijwegel.betteryaml;

import be.dezijwegel.betteryaml.interfaces.IOptionalConfigReader;
import be.dezijwegel.betteryaml.validation.ValidationHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * A wrapper around BetterYaml, handling all Exceptions and working with Optional to show where errors have happened
 */
@SuppressWarnings({"unused", "deprecation"})
public class OptionalBetterYaml implements IOptionalConfigReader
{


    private final File file;
    private final YamlConfiguration yamlConfiguration;


    /**
     * Handles IOException internally for those who want to avoid handling try-catches
     * A File and YamlConfiguration can later be retrieved as an Optional
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * Will not log any messages to the console
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param plugin the JavaPlugin for which a file is copied
     */
    public OptionalBetterYaml(final String name, final JavaPlugin plugin)
    {
        this(name, plugin, false);
    }


    /**
     * Handles IOException internally for those who want to avoid handling try-catches
     * A File and YamlConfiguration can later be retrieved as an Optional
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * Will not log any messages to the console
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param validationHandler the validator that can autocorrect options, based on your provided settings
     * @param plugin the JavaPlugin for which a file is copied
     */
    public OptionalBetterYaml(final String name, final ValidationHandler validationHandler, final JavaPlugin plugin)
    {
        this(name, validationHandler, plugin, false);
    }


    /**
     * Handles IOException internally for those who want to avoid handling try-catches
     * A File and YamlConfiguration can later be retrieved as an Optional
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     */
    public OptionalBetterYaml(final String name, final JavaPlugin plugin, final boolean doLogging)
    {
        this(name, new ValidationHandler(), plugin, doLogging);
    }


    /**
     * Handles IOException internally for those who want to avoid handling try-catches
     * A File and YamlConfiguration can later be retrieved as an Optional
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param validationHandler the validator that can autocorrect options, based on your provided settings
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     */
    public OptionalBetterYaml(final String name, final ValidationHandler validationHandler, final JavaPlugin plugin, final boolean doLogging)
    {
        BetterYaml betterYaml = null;
        try {
            betterYaml = new BetterYaml(name, validationHandler, plugin, doLogging);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Any of these is null? Something went wrong -> empty!
        if (betterYaml == null || betterYaml.getFile() == null || betterYaml.getYamlConfiguration() == null) {
            this.file = null;
            this.yamlConfiguration = null;
        } else {
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
