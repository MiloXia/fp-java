package javaz.pm.patterns;

import javaz.Try;
import javaz.pm.Pattern;

import java.util.Optional;
import java.util.function.Function;

public class FailurePattern<T, R> implements Pattern<Try<T>, R> {
    private final Function<Try<T>, R> function;

    public FailurePattern(Function<Try<T>, R> function) {
        this.function = function;
    }

    @Override
    public boolean matches(Try<T> value) {
        return value.isFailure();
    }

    @Override
    public R apply(Try<T> value) {
        return function.apply(value);
    }

    public static <T, R> Pattern<Try<T>, R> inCaseOf(Function<Try<T>, R> function) {
        return new FailurePattern<>(function);
    }
}
