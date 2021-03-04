package be.dezijwegel.betteryaml.files;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlReader {

    private final Map<String, Object> contents = new HashMap<>();

    /**
     * Creates a YamlReader from a given File
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param file the file to be read
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public YamlReader(File file) throws IOException
    {
        this( new FileInputStream( file ) );
    }

    /**
     * Creates a YamlReader from a given InputStream
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param fis the InputStream to be read
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public YamlReader(InputStream fis) throws IOException
    {
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
    private void addRecursiveContents(Map.Entry<String, Object> entry, String path)
    {
        String key = entry.getKey();
        Object value = entry.getValue();

        final String newPath = path.equals("") ? key : path + "." + key;

        if (value instanceof Map)
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;
            for (Map.Entry<String, Object> subEntry : map.entrySet())
                addRecursiveContents( subEntry, newPath);
        }
        else
        {
            contents.put(newPath, value);
        }
    }
}