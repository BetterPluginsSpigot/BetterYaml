package be.dezijwegel.betteryaml.formatting.formatters;

import be.dezijwegel.betteryaml.formatting.IFormatter;
import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListFormatter implements IFormatter
{

    @Override
    public @NotNull String format(final Object o, final @NotNull String serialised)
    {
        if ( !(o instanceof List))
            return serialised;

        var formatted = new StringBuilder("\n");

        var entries = serialised.split("\\r?\\n");
        for (var entry : entries)
        {
            formatted.append("  ");
            formatted.append(entry);
            formatted.append("\n");
        }

        return formatted.toString();
    }
}
