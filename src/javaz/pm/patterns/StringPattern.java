package javaz.pm.patterns;

import javaz.pm.Pattern;

import java.util.function.Function;

public class StringPattern<R> implements Pattern<String, R> {
    private final String pattern;
    private final Function<String, R> function;
    public StringPattern(String pattern, Function<String, R> function) {
        this.pattern = pattern;
        this.function = function;
    }
    @Override
    public boolean matches(String value) { return pattern.equals(value); }
    @Override
    public R apply(String value) { return function.apply(value); }

    public static <R> Pattern<String, R> inCaseOf(String pattern, Function<String, R> function) {
        return new StringPattern<>(pattern, function);
    }

}
