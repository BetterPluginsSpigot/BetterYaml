package be.dezijwegel.betteryaml.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Yaml merger.
 */
public class YamlMerger
{


    private final Map<String, Object> defaultOptions;

    /**
     * Prepare default options to be merged with live options
     *
     * @param defaultOptions the default key-value pairs, to be used when no others are available
     */
    public YamlMerger(final Map<String, Object> defaultOptions)
    {
        this.defaultOptions = defaultOptions;
    }

    /**
     * Merge the given options with the stored defaults
     * The provided options will get priority over default values
     * Any missing values in the provided map will be filled in by the default values
     * All keys in the provided map, that are not in the defaultOptions (provided through constructor), will be ignored
     *
     * @param options the options to be merged with all default values
     * @return the merged Map
     */
    public @NotNull Map<String, Object> merge(final @NotNull Map<String, Object> options)
    {
        Map<String, Object> mergedMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : defaultOptions.entrySet())
        {
            String key = entry.getKey();
            Object value = options.containsKey( key ) ? options.get( key ) : entry.getValue();
            mergedMap.put( key, value );
        }

        return mergedMap;
    }
}
