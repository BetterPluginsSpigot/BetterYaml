package be.dezijwegel.betteryaml.logging;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class BetterYamlLogger
{

    private static Level LOG_LEVEL = Level.OFF;
    private static String PREFIX = personalisePrefix(null);

    /**
     * Set the minimum log level for all messages
     * Only messages with this severity (or higher) are logged
     * @param newLevel the new logging level
     */
    public static void setLogLevel(Level newLevel)
    {
        BetterYamlLogger.LOG_LEVEL = newLevel;
    }

    /**
     * Set the minimum log level for all messages, does not overwrite the log level if it currently shows more information
     * Messages with this severity (or higher) are always logged, lower severities may be logged depending on the previous setting
     * @param newLevel the new logging level
     */
    public static void includeLogLevel(Level newLevel)
    {
        if (newLevel.intValue() < LOG_LEVEL.intValue())
        {
            setLogLevel(newLevel);
        }
    }

    /**
     * Personalise the logging prefix based on a specific plugin
     * This adds the given plugin name to the logging messages
     * The prefix is automatically personalised when initialising BetterYaml. Remove by calling personalisePrefix(null)
     * @param plugin the plugin whose name should be mentioned when logging
     */
    public static String personalisePrefix(@Nullable JavaPlugin plugin)
    {
        if (plugin != null)
            PREFIX = "[BetterYaml ~ " + plugin.getName() + "] ";
        else
            PREFIX = "[BetterYaml] ";
        return PREFIX;
    }

    public static void log (Level severity, String message) {
        if (severity.intValue() >= LOG_LEVEL.intValue() && LOG_LEVEL != Level.OFF)
        {
            message = PREFIX + message;
            if (Bukkit.getLogger().isLoggable(severity))
                Bukkit.getLogger().log(severity, message);
            else
                Bukkit.getLogger().log(Level.INFO, "[" + severity + "] " + message);
        }
    }

    public static void info(String message)
    {
        log(Level.INFO, message);
    }

}
