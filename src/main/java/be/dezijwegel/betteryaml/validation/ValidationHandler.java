package be.dezijwegel.betteryaml.validation;

import be.dezijwegel.betteryaml.logging.BetterYamlLogger;
import be.dezijwegel.betteryaml.validation.validator.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class ValidationHandler
{


    private final Map<String, Validator> validationMap;

    private final Set<String> optionalPaths;
    private final Map<String, Object> defaultOptionalMap;


    /**
     * A validation object that handles validation for a specific Map.
     * Add any validator to a path with ValidationHandler#addValidator(String path, Validator validator).
     */
    public ValidationHandler()
    {
        this.validationMap = new HashMap<>();
        this.optionalPaths = new HashSet<>();
        this.defaultOptionalMap = new HashMap<>();
    }


    /**
     * Specify a validator for the given path. Only ONE validator is allowed per path.
     * If you need multiple validators in a row, please use ChainedValidator which allows multiple validators to be used on one path.
     * Specifying a different validator for the same path will overwrite the previous value.
     *
     * @param path the path for which you want to specify a validator.
     * @param validator the validator you want to use for this path.
     * @return the ValidationHandler instance, to allow use of the builder pattern.
     */
    public ValidationHandler addValidator(final String path, final Validator validator)
    {
        this.validationMap.put( path, validator);
        return this;
    }


    /**
     * Add a path that does not have to be replaced by the defaults when it is missing.
     * This affects all child paths as well!
     * Paths marked as optional will still be validated.
     * @deprecated This is an experimental feature and may be changed or removed in any future update!
     *
     * @param path the path for which no default replacement must be made.
     * @return the ValidationHandler instance, to allow use of the builder pattern.
     */
    @Deprecated
    public ValidationHandler addOptionalSection(final String path)
    {
        this.optionalPaths.add( path );
        return this;
    }


    /**
     * Set the default value for a specific optional path. Will only be written to the config if the section does not exist.
     * Will fail silently if the path does not belong to an optional section.
     * @deprecated This is an experimental feature and may be changed or removed in any future update!
     *
     * @param path the path for which you want to set a default value.
     * @param value the default value for this path.
     * @return the ValidationHandler instance, to allow use of the builder pattern.
     */
    @Deprecated
    public ValidationHandler setOptionalValue(final String path, final Object value)
    {
        if ( this.isOptionalPath( path ) )
            this.defaultOptionalMap.put(path, value);
        return this;
    }

    /**
     * Get the default values for the optional paths.
     * @deprecated This is an experimental feature and may be changed or removed in any future update!
     *
     * @return the Map with all default (optional) values.
     */
    @Deprecated
    public Map<String, Object> getDefaultOptionalMap()
    {
        return this.defaultOptionalMap;
    }


    /**
     * Returns whether or not the given path is an optional section.
     * @deprecated This is an experimental feature and may be changed or removed in any future update.
     *
     * @param path the section's path.
     * @return true if it, and its children, are optional. False otherwise.
     */
    @Deprecated
    public boolean isOptionalSection(final String path)
    {
        return this.optionalPaths.contains( path );
    }

    /**
     * Check whether a given path is set to be optional.
     * @deprecated This is an experimental feature and may be changed or removed in any future update.
     *
     * @param path the path to be checked.
     * @return true if the path is optional, false otherwise.
     */
    @Deprecated
    public boolean isOptionalPath(final String path)
    {
        String[] parts = path.split("\\.");
        String pathPart = null;
        for (String part : parts)
        {
            pathPart = pathPart == null ? part : pathPart + "." + part;
            if (this.optionalPaths.contains( pathPart ))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Enforce all provided validators for the specified paths.
     *
     * @param config a map of paths & their values.
     * @return the corrected map.
     */
    public Map<String, Object> validateConfiguration(final Map<String, Object> config)
    {
        // Not as efficient as reading the required validation fields, but this is more readable
        for (Map.Entry<String, Object> entry : config.entrySet())
        {
            String path = entry.getKey();
            Object value = entry.getValue();

            Object validatedValue = validationMap.containsKey( path ) ? validationMap.get( path ).validate( value ) : value;
            config.put(path, validatedValue);

            BetterYamlLogger.log(Level.FINER, "Validation at " + path + ": " + value + " becomes " + validatedValue);
        }
        return config;
    }
}
