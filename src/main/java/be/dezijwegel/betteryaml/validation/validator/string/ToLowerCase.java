package be.dezijwegel.betteryaml.validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.jetbrains.annotations.NotNull;

/**
 * The type To lower case.
 */
public class ToLowerCase extends Validator
{
    /**
     * Makes any validated input lowercase
     * Any non-String will be converted to an empty String
     */
    public ToLowerCase() {}

    /**
     * Validate object.
     *
     * @param o the o
     * @return the object
     */
    @Override
    public @NotNull Object validate(@NotNull Object o)
    {
        if ( !(o instanceof String) )
            return "";

        String str = (String) o;
        return str.toLowerCase();
    }
}
