package javaz.pm;

import javaz.Pair;

import java.util.function.BiFunction;

/**
 * No use.
 */
@Deprecated
public abstract class AbsExtractor2<T1, T2, R, F> implements Extractor2<T1, T2, R, F> {
    //public final BiFunction<T1, T2, R> function;

//    public AbsExtractor2(BiFunction<T1, T2, R> function) {
//        this.function = function;
//    }

//    @Override
//    public Function<Pair<T1, T2>, R> returnF() {
//        return p -> function.apply(p.fst, p.snd);
//    }

    @Override
    public Pattern<F, R> returnP(BiFunction<T1, T2, R> function) {
        return new Pattern<F, R>() {
            @Override
            public boolean matches(F value) {
                return returnPId().matches(value);
            }

            @Override
            public R apply(F value) {
                Pair<T1, T2> p = returnPId().apply(value);
                return function.apply(p.fst, p.snd);
            }
        };
    }

    private Extractor2<T1, T2, R, F> self = (Extractor2<T1, T2, R, F>) this;

    @Override
    public <TT1, TT2, R2> Extractor2<TT1, TT2, R2, F> compose(Extractor<TT1, R2, T1> e1, Extractor<TT2, R2, T2> e2) {
        return new AbsExtractor2<TT1, TT2, R2, F>() {

            @Override
            public Pattern<F, Pair<TT1, TT2>> returnPId() {
                return new Pattern<F, Pair<TT1, TT2>>() {
                    @Override
                    public boolean matches(F value) {
                        if(self.returnPId().matches(value)) {
                            Pair<T1, T2> r = self.returnPId().apply(value);
                            return e1.returnPId().matches(r.fst) && e2.returnPId().matches(r.snd);
                        }
                        return false;
                    }

                    @Override
                    public Pair<TT1, TT2> apply(F value) {
                        Pair<T1, T2> r = self.returnPId().apply(value);
                        return Pair.of(e1.returnPId().apply(r.fst), e2.returnPId().apply(r.snd));
                    }
                };
            }
        };
    }
}
