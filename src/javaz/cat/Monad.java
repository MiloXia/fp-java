package javaz.cat;

import java.util.function.Function;

/**
 * Monad type class
 */
public interface Monad<T,M extends Monad<?, ?>> extends Functor<T, M> {
    M flatMap(Function<T, M> f);
    //<R> Monad<R, M> flatMap(Function<T, Monad<R, M>> f);
}


//interface Monad<T> {
//    Monad<T> of(T value);
//    <R> Monad<R> flatMap(Function<T, Monad<R>> mapper);
//}
