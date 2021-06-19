package be.dezijwegel.betteryaml.validation.validator.numeric;

import be.dezijwegel.betteryaml.util.NumbersUtil;
import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class Min extends Validator
{
    private final Number min;

    /**
     * Provide a minimum value for this setting
     * This will be handled as a double internally
     * Validate will return the min value for faulty types (non-numbers)
     *
     * @param min the lowest allowed value (inclusive)
     */
    public Min(@NotNull Number min)
    {
        this.min = min;
    }

    @Override
    public Object validate(@NotNull Object o)
    {
        if ( !(o instanceof Number) )
            return min;

        Number num = (Number) o;
        return NumbersUtil.compare(num, min) < 0 ? min : num;
    }
}
