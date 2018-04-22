package javaz.curry;

import java.util.function.Function;

/**
 * a -> b -> c -> r
 */
public interface F3<A, B, C, R> extends Function<A, Function<B, Function<C, R>>> {}
