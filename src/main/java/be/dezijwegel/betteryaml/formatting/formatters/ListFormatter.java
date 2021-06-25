package be.dezijwegel.betteryaml.formatting.formatters;

import be.dezijwegel.betteryaml.formatting.IFormatter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The type List formatter.
 */
public class ListFormatter implements IFormatter
{

    /**
     * Format string.
     *
     * @param o          the o
     * @param serialised the serialised
     * @return the string
     */
    @Override
    public @NotNull String format(final Object o, final @NotNull String serialised)
    {
        if ( !(o instanceof List))
            return serialised;

        StringBuilder formatted = new StringBuilder("\n");

        String[] entries = serialised.split("\\r?\\n");
        for (String entry : entries)
        {
            formatted.append("  ");
            formatted.append(entry);
            formatted.append("\n");
        }

        return formatted.toString();
    }
}
