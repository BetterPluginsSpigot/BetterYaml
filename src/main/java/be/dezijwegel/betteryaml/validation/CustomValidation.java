package be.dezijwegel.betteryaml.validation;

import be.dezijwegel.betteryaml.validation.validator.Validator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CustomValidation
{

    private final Map<String, Validator> validationMap;

    public CustomValidation()
    {
        this.validationMap = new HashMap<>();
    }

    public CustomValidation addValidator(String path, Validator validator)
    {
        this.validationMap.put( path, validator);
        return this;
    }

    public YamlConfiguration validateConfiguration(YamlConfiguration config)
    {
        // Not as efficient as reading the required validation fields, but this is more readable
        for (String path : config.getKeys(true))
        {
            Object value = config.get( path );
            if ( value != null )
            {
                Object validated = validationMap.get( path ).validate( value );
                config.set(path, validated);
            }
        }
        return config;
    }

}
