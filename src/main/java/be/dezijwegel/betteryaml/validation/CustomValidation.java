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
        for (String path : config.getKeys(true))
        {
            if (validationMap.containsKey( path ))
            {
                Object validated = validationMap.get( path ).validate( config.get( path ) );
                config.set(path, validated);
            }
        }
        return config;
    }

}
