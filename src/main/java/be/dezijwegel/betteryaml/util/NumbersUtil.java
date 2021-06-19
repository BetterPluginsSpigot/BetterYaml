package be.dezijwegel.betteryaml.util;

public class NumbersUtil
{

    /**
     * Taken from https://stackoverflow.com/questions/12561485/how-to-compare-two-numbers-in-java
     */
    public static int compare(Number n1, Number n2)
    {
        long l1 = n1.longValue();
        long l2 = n2.longValue();
        if (l1 == l2)
            return Double.compare(n1.doubleValue(), n2.doubleValue());
        return (l1 < l2 ? -1 : 1);
    }

}
