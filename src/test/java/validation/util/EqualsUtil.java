package validation.util;

public class EqualsUtil
{

    public static boolean equals(Object o, float value)
    {
        if (o instanceof Float)
            return (Float) o == value;
        return false;
    }

    public static boolean equals(Object o, double value)
    {
        if (o instanceof Double)
            return (Double) o == value;
        return false;
    }

    public static boolean equals(Object o, int value)
    {
        if (o instanceof Integer)
            return (Integer) o == value;
        return false;
    }

}
