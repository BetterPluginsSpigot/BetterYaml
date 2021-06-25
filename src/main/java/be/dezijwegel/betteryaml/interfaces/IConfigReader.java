package be.dezijwegel.betteryaml.interfaces;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * An interface for Config Reading
 */
public interface IConfigReader
{
    /**
     * Gets file.
     *
     * @return the file
     */
    File getFile();

    /**
     * Gets yaml configuration.
     *
     * @return the yaml configuration
     */
    YamlConfiguration getYamlConfiguration();
}
