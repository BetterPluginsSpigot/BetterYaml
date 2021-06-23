package be.dezijwegel.betteryaml.files;

import be.dezijwegel.betteryaml.validation.ValidationHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OptionalReader
{

    private final Map<String, Object> optionalContents;

    /**
     * Creates a YamlReader from a given File
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param file the file to be read
     * @param validationHandler the validationHandler, containing all optional paths
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public OptionalReader(final File file, final ValidationHandler validationHandler) throws IOException
    {
        this(new FileInputStream(file), validationHandler);
    }

    /**
     * Creates a YamlReader from a given InputStream
     * YamlReader supports nested keys
     * All key-value pairs will be added to a Map
     *
     * @param fis the InputStream to be read
     * @param validationHandler the validationHandler, containing all optional paths
     * @throws IOException when a stream cannot be created or the file does not exist
     */
    public OptionalReader(final InputStream fis, final ValidationHandler validationHandler) throws IOException
    {
        optionalContents = new HashMap<>();
        YamlReader reader = new YamlReader(fis);

        Map<String, Object> allContents = reader.getContents();
        for (String path : allContents.keySet())
        {
            if ( validationHandler.isOptionalPath( path ) )
            {
                this.optionalContents.put( path, allContents.get( path ) );
            }
        }
    }

    public Map<String, Object> getOptionalContents()
    {
        return optionalContents;
    }

}
