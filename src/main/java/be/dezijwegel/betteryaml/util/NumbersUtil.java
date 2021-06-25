package be.dezijwegel.betteryaml.util;

import org.jetbrains.annotations.NotNull;

/**
 * Numbers util.
 */
public final class NumbersUtil
{

    /**
     * Instantiates a new Numbers util.
     */
    private NumbersUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Taken from https://stackoverflow.com/questions/12561485/how-to-compare-two-numbers-in-java
     *
     * @param n1 the number one long value.
     * @param n2 the number two long value.
     * @return the int
     */
    public static int compare(final @NotNull Number n1, final @NotNull Number n2)
    {
        long l1 = n1.longValue();
        long l2 = n2.longValue();
        if (l1 == l2)
            return Double.compare(n1.doubleValue(), n2.doubleValue());
        return (l1 < l2 ? -1 : 1);
    }

}
