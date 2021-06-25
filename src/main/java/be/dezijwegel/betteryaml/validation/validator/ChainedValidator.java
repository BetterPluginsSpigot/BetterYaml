package be.dezijwegel.betteryaml.validation.validator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Chained validator.
 */
public class ChainedValidator extends Validator
{

    private final @NotNull List<Validator> validators;

    /**
     * This allows multiple validators to be used on one field
     * They are executed in the given order
     *
     * @param validator one or more validators you want to use (can be left blank but no validation will take place)
     */
    public ChainedValidator(@NotNull Validator... validator)
    {
        this.validators = new ArrayList<>(Arrays.asList(validator));
    }

    /**
     * Validate object.
     *
     * @param o the o
     * @return the object
     */
    @Override
    public @NotNull Object validate(@NotNull Object o)
    {
        for (Validator validator : validators)
            o = validator.validate(o);
        return o;
    }
}
