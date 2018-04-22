package javaz.pm.patterns;

import javaz.pm.Pattern;

import java.util.function.Function;

public class ClassPattern<T, R> implements Pattern<T, R> {
    private final Class<? super T> clazz;
    private final Function<T, R> function;
    public ClassPattern(Class<? super T> clazz, Function<T, R> function) {
        this.clazz = clazz;
        this.function = function;
    }
    public boolean matches(T value) {
        return clazz.isInstance(value);
    }
    public R apply(T value) {
        return function.apply(value);
    }
    public static <T, R> Pattern<T, R> inCaseOf(Class<? super T> clazz, Function<T, R> function) {
        return new ClassPattern<>(clazz, function);
    }

}