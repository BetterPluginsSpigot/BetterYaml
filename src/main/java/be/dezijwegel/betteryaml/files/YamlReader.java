package be.dezijwegel.betteryaml.files;

import be.dezijwegel.betteryaml.logging.BetterYamlLogger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class YamlReader
{

    private final Map<String, Object> contents = new HashMap<>();

    /**
     * Creates a YamlReader from a given File
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param file the file to be read
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public YamlReader(final File file) throws IOException
    {
        this(new FileInputStream(file));
    }

    /**
     * Creates a YamlReader from a given InputStream
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param fis the InputStream to be read
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public YamlReader(final InputStream fis) throws IOException
    {
        BetterYamlLogger.log(Level.FINER, "Starting YAML reader");

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowRecursiveKeys(true);
        Yaml yaml = new Yaml(loaderOptions);
        Map<String, Object> temp = yaml.load(fis);
        Map<String, Object> yamlContent = temp != null ? temp : new HashMap<>();

        for (Map.Entry<String, Object> entry : yamlContent.entrySet())
        {
            addRecursiveContents(entry, "");
        }

        fis.close();

        BetterYamlLogger.log(Level.FINER, "Stopped YAML reader");
    }

    /**
     * Get the contents from the provided File or InputStream
     * ConfigurationSections are not added separately
     * Nested keys are saved as: section.subsection.option
     *
     * @return all keys and their mapped values from within the file
     */
    public Map<String, Object> getContents()
    {
        return this.contents;
    }

    /**
     * Recursively read a config option
     * Calls itself when a option has subsections
     * Adds a key-value pair to the contents Map if the path does not have subsections
     *
     * @param entry the key-value pair under consideration
     * @param path the previous path, to keep track of nested keys
     */
    private void addRecursiveContents(final Map.Entry<String, Object> entry, final String path)
    {
        String key = entry.getKey();
        Object value = entry.getValue();
        BetterYamlLogger.log(Level.FINEST, "Adding recursive contents for " + key);

        final String newPath = path.equals("") ? key : path + "." + key;

        if (value instanceof Map)
        {
            BetterYamlLogger.log(Level.FINEST, "Value at " + key + " contains a deeper configuration level");
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;
            for (Map.Entry<String, Object> subEntry : map.entrySet())
                // Recursive call.
                addRecursiveContents( subEntry, newPath);
        }
        else
        {
            BetterYamlLogger.log(Level.FINEST, "Found " + value + " at " + newPath);
            contents.put(newPath, value);
        }
    }
}
