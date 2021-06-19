package be.dezijwegel.betteryaml.validation.validator.numeric;

import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class Range extends Validator
{

    private final Min min;
    private final Max max;

    /**
     * Provide a range [min, max] for this setting
     * This will be handled as a double internally
     *
     * @param min the lowest allowed value (inclusive)
     * @param max the highest allowed value (inclusive)
     */
    public Range(@NotNull Number min, @NotNull Number max)
    {
        this.min = new Min(min);
        this.max = new Max(max);
    }

    @Override
    public Object validate(@NotNull Object o)
    {
        Object maxLimited = max.validate(o);
        return min.validate( maxLimited );
    }
}
