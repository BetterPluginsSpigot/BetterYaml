package validation;

import be.dezijwegel.betteryaml.validation.ValidationHandler;
import org.junit.Test;

public class ValidationHandlerTest
{

    @Test
    public void testIsOptional()
    {
        ValidationHandler validationHandler = new ValidationHandler()
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
