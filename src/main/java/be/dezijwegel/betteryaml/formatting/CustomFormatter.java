package be.dezijwegel.betteryaml.formatting;

import be.dezijwegel.betteryaml.formatting.formatters.ListFormatter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFormatter
{

    private final @NotNull Map<Class<?>, IFormatter> formatterMap;

    public CustomFormatter()
    {
        formatterMap = new HashMap<>();
        this.formatterMap.put(List.class, new ListFormatter());
        this.formatterMap.put(ArrayList.class, new ListFormatter());
    }

    /**
     * Allows public modification, will very likely remain unused
     *
     * @param type the class for which different formatting is desired
     * @param formatter the formatter we want to use for this type
     * @return the CustomFormatter object, this allows chaining to achieve a builder-pattern-like behaviour
     */
    @SuppressWarnings("unused")
    public @NotNull CustomFormatter addFormatter(final Class<?> type, final IFormatter formatter)
    {
        this.formatterMap.put(type, formatter);
        return this;
    }

    public String format(final @NotNull Object o, final String serialised)
    {
        if ( ! formatterMap.containsKey( o.getClass() ))
            return serialised;

        return formatterMap.get( o.getClass() ).format( o, serialised );
    }

}
