package javaz.pm;

import javaz.Pair;

import java.util.function.BiFunction;

/**
 * No use.
 */
@Deprecated
public interface Extractor2<T1, T2, R, F> {
    Pattern<F, R> returnP(BiFunction<T1, T2, R> function);
    Pattern<F, Pair<T1, T2>> returnPId();
    <TT1, TT2, R2> Extractor2<TT1, TT2, R2, F> compose(Extractor<TT1, R2, T1> e1, Extractor<TT2, R2, T2> e2);
}
