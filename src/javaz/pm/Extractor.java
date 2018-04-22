package javaz.pm;

import javaz.Pair;

import java.util.function.Function;


/**
 * Builder of Pattern.
 */
public interface Extractor<T, R, F> {
    Pattern<F, R> returnP(Function<T, R> function);
    Pattern<F, T> returnPId();
    <T2, R2> Extractor<T2, R2, F> compose(Extractor<T2, R2, T> e);
    <TT1, TT2, R2, F1, F2> Extractor<Pair<TT1, TT2>, R2, F> compose(Extractor<TT1, R2, F1> e1, Extractor<TT2, R2, F2> e2);
    //TODO more arg compose
}
