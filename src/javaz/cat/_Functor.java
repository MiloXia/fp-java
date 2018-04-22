package javaz.cat;

import java.util.function.Function;

/**
 * HKT ver.
 */
public interface _Functor<WITNESS> {
    <T,R> Higher<WITNESS,R> map(Function<? super T, ? extends R> fn, Higher<WITNESS, T> ds);
}
