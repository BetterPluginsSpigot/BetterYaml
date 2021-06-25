package validation;

import be.dezijwegel.betteryaml.validation.ValidationHandler;
import org.junit.Test;

/**
 * The type Validation handler test.
 */
public class ValidationHandlerTest
{

    /**
     * Test is optional.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testIsOptional()
    {
        @SuppressWarnings("deprecation") ValidationHandler validationHandler = new ValidationHandler()
                .addOptionalSection("this.is.optional")
                .addOptionalSection("optional");

        assert validationHandler.isOptionalPath( "this.is.optional" );
        assert validationHandler.isOptionalPath( "optional" );
        assert validationHandler.isOptionalPath( "this.is.optional.path" );
        assert validationHandler.isOptionalPath( "this.is.optional.path.true" );
        assert !validationHandler.isOptionalPath( "this.is" );
        assert !validationHandler.isOptionalPath( "random.path" );
    }

}
