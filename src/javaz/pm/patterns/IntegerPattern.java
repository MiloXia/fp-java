package javaz.pm.patterns;

import javaz.pm.Pattern;

import java.util.function.Function;

public class IntegerPattern<R> implements Pattern<Integer, R> {
    private final Integer pattern;
    private final Function<Integer, R> function;

    public IntegerPattern(int pattern, Function<Integer, R> function) {
        this.pattern = pattern;
        this.function = function;
    }

    @Override
    public boolean matches(Integer value) {
        return pattern.equals(value);
    }

    @Override
    public R apply(Integer value) {
        return function.apply(value);
    }

    public static <R> Pattern<Integer, R> inCaseOf(int pattern, Function<Integer, R> function) {
        return new IntegerPattern<>(pattern, function);
    }

}