package be.dezijwegel.betteryaml.interfaces;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * An interface for Config Reading
 */
public interface IConfigReader
{
    File getFile();
    YamlConfiguration getYamlConfiguration();
}
