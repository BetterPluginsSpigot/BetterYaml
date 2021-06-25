package validation.util;

/**
 * The type Equals util.
 */
public class EqualsUtil
{

    /**
     * Equals boolean.
     *
     * @param o     the o
     * @param value the value
     * @return the boolean
     */
    public static boolean equals(Object o, float value)
    {
        if (o instanceof Float)
            return (Float) o == value;
        return false;
    }

    /**
     * Equals boolean.
     *
     * @param o     the o
     * @param value the value
     * @return the boolean
     */
    public static boolean equals(Object o, double value)
    {
        if (o instanceof Double)
            return (Double) o == value;
        return false;
    }

    /**
     * Equals boolean.
     *
     * @param o     the o
     * @param value the value
     * @return the boolean
     */
    public static boolean equals(Object o, int value)
    {
        if (o instanceof Integer)
            return (Integer) o == value;
        return false;
    }

}
