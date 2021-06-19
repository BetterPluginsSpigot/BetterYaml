package be.dezijwegel.betteryaml.validation.validator.numeric;

import be.dezijwegel.betteryaml.util.NumbersUtil;
import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class Max extends Validator
{

    private final Number max;

    /**
     * Provide a maximum value for this setting
     * This will be handled as a double internally
     * Validate will return the max value for faulty types (non-numbers)
     *
     * @param max the highest allowed value (inclusive)
     */
    public Max(@NotNull Number max)
    {
        this.max = max;
    }

    @Override
    public Object validate(@NotNull Object o)
    {
        if ( !(o instanceof Number) )
            return max;

        Number num = (Number) o;
        return NumbersUtil.compare(num, max) > 0 ? max : num;
    }
}
