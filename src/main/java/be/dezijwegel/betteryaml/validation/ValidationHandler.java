package be.dezijwegel.betteryaml.validation;

import be.dezijwegel.betteryaml.validation.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ValidationHandler
{

    private final Map<String, Validator> validationMap;

    public ValidationHandler()
    {
        this.validationMap = new HashMap<>();
    }

    public ValidationHandler addValidator(String path, Validator validator)
    {
        this.validationMap.put( path, validator);
        return this;
    }

    public Map<String, Object> validateConfiguration(Map<String, Object> config)
    {
        // Not as efficient as reading the required validation fields, but this is more readable
        for (String path : config.keySet())
        {
            Object value = config.get( path );
            if ( value != null )
            {
                Object validated = validationMap.get( path ).validate( value );
                config.put(path, validated);
            }
        }
        return config;
    }

}
