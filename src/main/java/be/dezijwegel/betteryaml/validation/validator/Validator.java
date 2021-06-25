package be.dezijwegel.betteryaml.validation.validator;

import org.jetbrains.annotations.NotNull;

/**
 * The type Validator.
 */
public abstract class Validator
{

    /**
     * Validate object.
     *
     * @param o the o
     * @return the object
     */
    public abstract Object validate(@NotNull Object o);

}
