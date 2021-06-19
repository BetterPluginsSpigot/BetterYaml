package be.dezijwegel.betteryaml.validation.validator.string;

import be.dezijwegel.betteryaml.validation.validator.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllowedValues extends Validator
{

    private final boolean ignoreCase;
    private final String defaultValue;
    private final Set<String> allowedValues;

    /**
     * Provide a list of strings that are allowed for this setting and a default value
     * The object is maintained if it is an allowed value
     * If the object is not allowed, the default value will be returned
     *
     * @param defaultValue the default value to fall back to for faulty inputs
     * @param ignoreCase whether upper/lowercase matters
     * @param allowedValue zero, one, or more allowed values (all other inputs are blacklisted)
     */
    public AllowedValues(String defaultValue, boolean ignoreCase, String... allowedValue)
    {
        this.defaultValue = defaultValue;
        this.ignoreCase = ignoreCase;
        if (ignoreCase)
        {
            this.allowedValues = new HashSet<>();
            for (String lowercaseValue : allowedValue)
            {
                this.allowedValues.add( lowercaseValue.toLowerCase() );
            }
        }
        else
        {
            this.allowedValues = new HashSet<>(Arrays.asList(allowedValue));
        }
    }

    @Override
    public Object validate(Object o)
    {
        if ( ! (o instanceof String) )
            return defaultValue;

        String value = (String) o;
        String returnValue;
        if (allowedValues.contains( value ))
        {
            return value;
        }
        else if (ignoreCase && allowedValues.contains( value.toLowerCase() ))
        {
            return value;
        }
        else
        {
            return defaultValue;
        }
    }
}
