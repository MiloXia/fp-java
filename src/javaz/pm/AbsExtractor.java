package javaz.pm;

import javaz.Pair;

import java.util.function.Function;

/**
 * Base impl.
 */
public abstract class AbsExtractor<T, R, F> implements Extractor<T, R, F> {
//    public final Function<T, R> function;

//    public AbsExtractor(Function<T, R> function) {
//        this.function = function;
//    }
//
//    @Override
//    public Function<T, R> returnF() {
//        return function;
//    }

//    public AbsExtractor() {
//        this.function = function;
//    }
//
//    public AbsExtractor(Function<T, R> function) {
//        this.function = function;
//    }

    @Override
    public Pattern<F, R> returnP(Function<T, R> function) {
        return new Pattern<F, R>() {
            @Override
            public boolean matches(F value) {
                return returnPId().matches(value);
            }

            @Override
            public R apply(F value) {
                return function.apply(returnPId().apply(value));
            }
        };
    }

    private Extractor<T, R, F> self = (Extractor<T, R, F>) this;

    @Override
    public <T2, R2> Extractor<T2, R2, F> compose(Extractor<T2, R2, T> e) {
        return new AbsExtractor<T2, R2, F>() {

            @Override
            public Pattern<F, T2> returnPId() {
                return new Pattern<F, T2>() {
                    @Override
                    public boolean matches(F value) {
                        if(self.returnPId().matches(value)) {
                            T r = self.returnPId().apply(value); // F2 = T
                            return e.returnPId().matches(r);
                        }
                        return false;
                    }

                    @Override
                    public T2 apply(F value) {
                        return e.returnPId().apply(self.returnPId().apply(value));
                    }
                };
            }
        };
    }

    @Override
    public <TT1, TT2, R2, F1, F2> Extractor<Pair<TT1, TT2>, R2, F> compose(Extractor<TT1, R2, F1> e1, Extractor<TT2, R2, F2> e2) {
        return new AbsExtractor<Pair<TT1, TT2>, R2, F>() {

            @Override
            public Pattern<F, Pair<TT1, TT2>> returnPId() {
                return new Pattern<F, Pair<TT1, TT2>>() {
                    @Override
                    public boolean matches(F value) {
                        if(self.returnPId().matches(value)) {
                            T r = self.returnPId().apply(value); // Pair<F1, F2> = T
                            Pair<F1, F2> p = (Pair<F1, F2>) r;
                            return e1.returnPId().matches(p.fst) && e2.returnPId().matches(p.snd);
                        }
                        return false;
                    }

                    @Override
                    public Pair<TT1, TT2> apply(F value) {
                        T r = self.returnPId().apply(value);
                        Pair<F1, F2> p = (Pair<F1, F2>) r;
                        return Pair.of(e1.returnPId().apply(p.fst), e2.returnPId().apply(p.snd));
                    }
                };
            }
        };
    }
}
