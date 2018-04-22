package javaz.pm;

import java.util.function.Predicate;

/**
 * Abs of pattern.
 */
public interface Pattern<T, R> {
    boolean matches(T value);
    R apply(T value);
}
