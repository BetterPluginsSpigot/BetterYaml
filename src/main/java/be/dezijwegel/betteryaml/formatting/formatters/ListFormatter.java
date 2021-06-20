package be.dezijwegel.betteryaml.formatting.formatters;

import be.dezijwegel.betteryaml.formatting.IFormatter;

import java.util.List;

public class ListFormatter implements IFormatter
{

    @Override
    public String format(final Object o, final String serialised)
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
