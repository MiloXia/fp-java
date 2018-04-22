package javaz.pm.patterns;

import javaz.pm.Pattern;

import java.util.function.Function;

public class OtherwisePattern<T, R> implements Pattern<T, R> {
    private final Function<T, R> function;
    public OtherwisePattern(Function<T, R> function) {
        this.function = function;
    }
    @Override
    public boolean matches(T value) { return true; }
    @Override
    public R apply(T value) { return function.apply(value); }

    public static <T, R> Pattern<T, R> otherwise(Function<T, R> function) {
        return new OtherwisePattern<>(function);
    }
}

