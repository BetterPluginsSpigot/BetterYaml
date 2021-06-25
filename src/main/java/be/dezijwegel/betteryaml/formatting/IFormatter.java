package be.dezijwegel.betteryaml.formatting;

import org.jetbrains.annotations.NotNull;

/**
 * The interface Formatter.
 */
public interface IFormatter
{

    /**
     * Format string.
     *
     * @param o          the o
     * @param serialised the serialised
     * @return the string
     */
    @NotNull String format(Object o, String serialised);

}
