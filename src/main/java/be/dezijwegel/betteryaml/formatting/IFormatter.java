package be.dezijwegel.betteryaml.formatting;

import org.jetbrains.annotations.NotNull;

public interface IFormatter
{

    @NotNull String format(Object o, String serialised);

}
