package be.dezijwegel.betteryaml.validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class LowerCase extends Validator
{
    /**
     * Makes any validated input lowercase
     * Any non-String will be converted to an empty String
     */
    public LowerCase() {}

    @Override
    public Object validate(@NotNull Object o)
    {
        if ( !(o instanceof String) )
            return "";

        String str = (String) o;
        return str.toLowerCase();
    }
}
