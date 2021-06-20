package be.dezijwegel.betteryaml;

import be.dezijwegel.betteryaml.files.TempFileCopier;
import be.dezijwegel.betteryaml.files.YamlReader;
import be.dezijwegel.betteryaml.formatting.CustomFormatter;
import be.dezijwegel.betteryaml.interfaces.IConfigReader;
import be.dezijwegel.betteryaml.representer.CustomRepresenter;
import be.dezijwegel.betteryaml.util.YamlMerger;
import be.dezijwegel.betteryaml.validation.ValidationHandler;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A class aimed at internal use only. It is recommended to use OptionalBetterYaml
 * However, when you want to handle Exceptions yourself, feel free to use this class
 */
@SuppressWarnings({"unused"})
public class BetterYaml implements IConfigReader
{

    private final @NotNull File file;
    private final @NotNull YamlConfiguration yamlConfiguration;

    /**
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * Will not log any messages to the console
     * @deprecated We advise using OptionalBetterYaml, this causes less clutter in your code.
     * However, this class will not be removed in future versions as some people may want to handle exceptions themselves.
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param plugin the JavaPlugin for which a file is copied
     * @throws IOException when your configuration is incorrect
     */
    @Deprecated
    public BetterYaml(final @NotNull String name, final @NotNull JavaPlugin plugin) throws IOException
    {
        this(name, plugin, false);
    }

    /**
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * @deprecated We advise using OptionalBetterYaml, this causes less clutter in your code.
     * However, this class will not be removed in future versions as some people may want to handle exceptions themselves.
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     * @throws IOException when your configuration is incorrect
     */
    @Deprecated
    public BetterYaml(final @NotNull String name, final @NotNull JavaPlugin plugin, final boolean doLogging) throws IOException
    {
        this(name, name, "", plugin, doLogging);
    }


    /**
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * @deprecated We advise using OptionalBetterYaml, this causes less clutter in your code.
     * However, this class will not be removed in future versions as some people may want to handle exceptions themselves.
     *
     * @param name the name of the config file eg. "ourConfig.yml"
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     * @throws IOException when your configuration is incorrect
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public BetterYaml(final @NotNull String name, final @NotNull ValidationHandler handler, final @NotNull JavaPlugin plugin, final boolean doLogging) throws IOException
    {
        this(name, name, "", handler, plugin, doLogging);
    }


    /**
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * @deprecated We advise against using this constructor as it is meant for internal use only.
     * This constructor enables you to alter the desired path structure, so only use it when you know what you are doing!
     *
     * @param template the name of the template/live config file eg. "ourConfig.yml"
     * @param values the name of the config file that contains the default values
     * @param valuesPath the path to the default resource (ending on a /)
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     * @throws IOException when your configuration is incorrect
     */
    @Deprecated
    public BetterYaml(final @NotNull String template, final @NotNull String values, final String valuesPath, final @NotNull JavaPlugin plugin, final boolean doLogging) throws IOException
    {
        this(template, values, valuesPath, new ValidationHandler(), plugin, doLogging);
    }

    /**
     * Creates a BetterYaml instance that will handle your config files
     * For the server's file: Missing options will be autocompleted and comments will be updated based on the template
     * No settings will be changed
     * @deprecated We advise against using this constructor as it is meant for internal use only.
     * This constructor enables you to alter the desired path structure, so only use it when you know what you are doing!
     *
     * @param template the name of the template/live config file eg. "ourConfig.yml"
     * @param values the name of the config file that contains the default values
     * @param valuesPath the path to the default resource (ending on a /)
     * @param handler the validator that can autocorrect options, based on your provided settings
     * @param plugin the JavaPlugin for which a file is copied
     * @param doLogging whether or not basic logging is done in your plugin's name. (Only logs on copying a new file and when missing options are found)
     * @throws IOException when your configuration is incorrect
     */
    @Deprecated
    @SuppressWarnings({"ResultOfMethodCallIgnored", "DeprecatedIsStillUsed"})
    public BetterYaml(final @NotNull String template, final @NotNull String values, final String valuesPath, final @NotNull ValidationHandler handler, final @NotNull JavaPlugin plugin, final boolean doLogging) throws IOException
    {

        // Create plugin folder if it does not exist
        var folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();

        //
        // Copy temp files
        //

        var defaultCopy = new TempFileCopier(plugin, valuesPath, values, "temp" + File.separator);
        var templateCopy = new TempFileCopier(plugin, "templates/", template, "temp" + File.separator + "templates" + File.separator);


        //
        // Get a MAP that contains all key-values pairs: Existing values where possible, defaults otherwise
        //


        // Get config options on the live server
        var liveConfig = new File(plugin.getDataFolder(), template);
        Map<String, Object> liveContents;
        if (liveConfig.exists())
            liveContents = new YamlReader(liveConfig).getContents();
        else {
            if (doLogging)
                plugin.getLogger().info(ChatColor.GREEN + "Copying a new " + template + "...");
            liveContents = new HashMap<>();
        }

        // Get config with default values
        var defaultFile = new File(plugin.getDataFolder() + File.separator + "temp", values);
        var defaultContents = new YamlReader(defaultFile).getContents();

        // Merge live and default values
        var merger = new YamlMerger( defaultContents );
        var newContents = merger.merge(liveContents);

        // Validate the configuration
        newContents = handler.validateConfiguration(newContents);

        if (doLogging)
        {
            // Do a missing options estimation
            // May be inaccurate when the user has key-value pairs that are not in the default file
            // Accurate readings require iterating over the map which is not worth it for a simple logging feature
            int difference = defaultContents.size() - liveContents.size();
            if (difference > 0)
                plugin.getLogger().info("Estimated " + difference + " missing options in " + template + ". Autocompleting your config file...");
        }


        //
        // Write the template and replace all tags by their corresponding values in the map if available
        //


        // Prepare file writer and template reader
        var writer = Files.newBufferedWriter(Paths.get( plugin.getDataFolder() + File.separator + template ));
        var reader = new BufferedReader( new FileReader(plugin.getDataFolder() + File.separator + "temp/templates/" + template) );

        // Prepare YAMLSnake
        var options = new DumperOptions();
        options.setWidth(750);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        var yaml = new Yaml(new CustomRepresenter(), options);

        // This formatter fixes resulting strings that could break YAML
        var formatter = new CustomFormatter();

        // Go through the template and replace all placeholders
        String line;
        while ((line = reader.readLine()) != null) {

            // Replace placeholders
            var replaceThis = StringUtils.substringsBetween(line, "{", "}");
            if (replaceThis != null)
            {
                for (var tag : replaceThis)
                {
                    if (newContents.containsKey(tag))
                    {
                        var placeholder = "{" + tag + "}";

                        // Parse the value
                        var value = newContents.get(tag);
                        var dumped = yaml.dump(value);

                        // Remove newline
                        dumped = dumped.substring(0, dumped.length() - 1);

                        // Fix faulty formatting. eg. for lists
                        dumped = formatter.format( value, dumped );

                        // Write the new value
                        line = line.replace( placeholder, dumped );
                    }
                }
            }

            // Write to file
            writer.append(line);
            if (!line.endsWith("\n"))
                writer.newLine();
        }

        reader.close();
        writer.close();

        //
        //  Cleanup: Delete temp folders
        //

        templateCopy.deleteCopiedFile();
        defaultCopy.deleteCopiedFile();

        new File(plugin.getDataFolder() + File.separator + "temp" + File.separator + "templates" + File.separator).delete();
        new File(plugin.getDataFolder() + File.separator + "temp" + File.separator).delete();

        //
        // Read final config contents
        //

        var file = new File(plugin.getDataFolder() + File.separator + template);
        this.file = file;
        this.yamlConfiguration = YamlConfiguration.loadConfiguration( file );
    }


    public @NotNull File getFile()
    {
        return this.file;
    }

    public @NotNull YamlConfiguration getYamlConfiguration()
    {
        return this.yamlConfiguration;
    }

}
