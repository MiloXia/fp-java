package javaz.cat;

import java.util.function.Function;

/**
 * Functor type class
 */
public interface Functor<T,F extends Functor<?, ?>> {
    <R> F map(Function<T,R> f);
}

//public interface Functor<F extends Functor<?>> { //wildcard can be used in type bound
//    <T, R> F map(Function<T,R> fn, F c);
//}