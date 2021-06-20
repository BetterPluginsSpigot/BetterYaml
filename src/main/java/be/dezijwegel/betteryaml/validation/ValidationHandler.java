package be.dezijwegel.betteryaml.validation;

import be.dezijwegel.betteryaml.validation.validator.Validator;
import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ValidationHandler
{

    private final @NotNull Map<String, Validator> validationMap;

    public ValidationHandler()
    {
        this.validationMap = new HashMap<>();
    }

    public @NotNull ValidationHandler addValidator(String path, Validator validator)
    {
        this.validationMap.put( path, validator);
        return this;
    }

    public @NotNull Map<String, Object> validateConfiguration(@NotNull Map<String, Object> config)
    {
        // Not as efficient as reading the required validation fields, but this is more readable
        for (var path : config.keySet())
        {
            var value = config.get(path);
            if ( value != null )
            {
                Object validated = validationMap.get(path).validate(value);
                config.put(path, validated);
            }
        }
        return config;
    }

}
