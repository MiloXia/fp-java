package javaz.cat;

import java.util.function.Function;

/**
 * HKT ver.
 */
public interface _Monad<WITNESS> extends _Functor<WITNESS> {
    <T,R> Higher<WITNESS, R> flatMap(Function<? super T, ? extends Higher<WITNESS, R>> fn, Higher<WITNESS, T> ds);
}
