package javaz.pm.patterns;

import javaz.pm.Pattern;

import java.util.Optional;
import java.util.function.Function;

public class OptionalPattern<T, R> implements Pattern<Optional<T>, R> {
    private final Optional<T> pattern;
    private final Function<Optional<T>, R> function;

    public OptionalPattern(Optional<T> pattern, Function<Optional<T>, R> function) {
        this.pattern = pattern;
        this.function = function;
    }

    @Override
    public boolean matches(Optional<T> value) {
        return pattern.equals(value);
    }

    @Override
    public R apply(Optional<T> value) {
        return function.apply(value);
    }

    public static <T, R> Pattern<Optional<T>, R> inCaseOf(Optional<T> pattern, Function<Optional<T>, R> function) {
        return new OptionalPattern<>(pattern, function);
    }

//    public static <T, R> Pattern<Optional<T>, R> inCaseOf(Optional<T> pattern, Function<T, R> function) {
//        return new OptionalPattern<>(pattern, function);
//    }
}
