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
    Optional<File> getFile();
    Optional<YamlConfiguration> getYamlConfiguration();
}
