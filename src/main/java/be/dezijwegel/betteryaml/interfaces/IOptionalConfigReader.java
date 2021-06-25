package be.dezijwegel.betteryaml.interfaces;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Optional;

/**
 * An improvement upon IConfigReader, where we now work with Optionals
 */
@SuppressWarnings({"unused"})
public interface IOptionalConfigReader
{
    /**
     * Gets file.
     *
     * @return the file
     */
    Optional<File> getFile();

    /**
     * Gets yaml configuration.
     *
     * @return the yaml configuration
     */
    Optional<YamlConfiguration> getYamlConfiguration();
}
