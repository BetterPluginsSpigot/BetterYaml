package be.dezijwegel.betteryaml.validation.validator;

import org.jetbrains.annotations.NotNull;

public abstract class Validator
{

    public abstract Object validate(@NotNull Object o);

}
