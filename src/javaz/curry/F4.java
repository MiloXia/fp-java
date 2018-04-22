package javaz.curry;

import java.util.function.Function;

/**
 * a -> b -> c -> d -> r
 */
public interface F4<A, B, C, D, R> extends Function<A, Function<B, Function<C, Function<D, R>>>> {}
