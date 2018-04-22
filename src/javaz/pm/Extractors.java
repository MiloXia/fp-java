package javaz.pm;

import javaz.Pair;
import javaz.Try;
import javaz.pm.extractors.*;
//import javaz.pm.extractors.OptionalExtractor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * DSL helper.
 */
public class Extractors {
//    public static <T> Optional<T> some() {
//        return OptionalExtractor.some();
//    }

    public static <T, R> Extractor<T, R, T> _const(T t) {
        return  new AbsExtractor<T, R, T>() {
            @Override
            public Pattern<T, T> returnPId() {
                return new Pattern<T, T>() {
                    @Override
                    public boolean matches(T value) {
                        return value != null && t.equals(value);
                    }

                    @Override
                    public T apply(T value) {
                        return t;
                    }
                };
            }
        };
    }

    public static <T, R> Extractor<T, R, T> _type(Class<? super T> clazz) {
        return  new AbsExtractor<T, R, T>() {
            @Override
            public Pattern<T, T> returnPId() {
                return new Pattern<T, T>() {
                    @Override
                    public boolean matches(T value) {
                        return value != null && clazz.isInstance(value);
                    }

                    @Override
                    public T apply(T value) {
                        return value;
                    }
                };
            }
        };
    }

    public static <T, R> Extractor<T, R, T> toEx(Pattern<T, R> pattern) {
        return  new AbsExtractor<T, R, T>() {
            @Override
            public Pattern<T, T> returnPId() {
                return new Pattern<T, T>() {
                    @Override
                    public boolean matches(T value) {
                        return pattern.matches(value);
                    }

                    @Override
                    public T apply(T value) {
                        return value;
                    }
                };
            }
        };
    }

    public static <T> Function<T, T> id() {
        return (T x) -> x;
    }

    public static <T, R> Extractor<T, R, Optional<T>> some() { return new OptionExtractor<>(); }

    public static <T, R, T2, R2> Pattern<Optional<T>, R2> some(Extractor<T2, R2, T> e, Function<T2, R2> f) {
        return new OptionExtractor<T, R>().compose(e).returnP(f);
    }

    public static <T, R, T2, R2> Extractor<T2, R2, Optional<T>> some(Extractor<T2, R2, T> e) {
        return new OptionExtractor<T, R>().compose(e);
    }

    public static <T, R> Pattern<Optional<T>, R> some(Function<T, R> f) { return new OptionExtractor<T, R>().returnP(f); }

    public static <T, R> Pattern<Try<T>, R> success(Function<T, R> f) { return new SuccessExtractor<T, R>().returnP(f); }

    public static <T, R> Extractor<T, R, Try<T>> success() { return new SuccessExtractor<>(); }

    public static <T, R, T2, R2> Pattern<Try<T>, R2> success(Extractor<T2, R2, T> e, Function<T2, R2> f) {
        return new SuccessExtractor<T, R>().compose(e).returnP(f);
    }

    public static <T, R, T2, R2> Extractor<T2, R2, Try<T>> success(Extractor<T2, R2, T> e) {
        return new SuccessExtractor<T, R>().compose(e);
    }

    //public static <A, B, R> Extractor2<A, B, R, Pair<A, B>> pair() { return new PairExtractor<>(); }
    public static <A, B, R> Extractor<Pair<A, B>, R, Pair<A, B>> pair() { return new PairExtractor<>(); }

    public static <A, B, R, TT1, TT2, R2> Pattern<Pair<A, B>, R2> pair(Extractor<TT1, R2, A> e1,
                                                                               Extractor<TT2, R2, B> e2,
                                                                               BiFunction<TT1, TT2, R2> f) {
        return pair(e1, e2, p -> f.apply(p.fst, p.snd));
    }

    public static <A, B, R, TT1, TT2, R2> Pattern<Pair<A, B>, R2> pair(Extractor<TT1, R2, A> e1,
                                                                       Extractor<TT2, R2, B> e2,
                                                                       Function<Pair<TT1, TT2>, R2> f) {
        return new PairExtractor<A, B, R>().compose(e1, e2).returnP(f);
    }

    public static <A, B, R, TT1, TT2, R2> Extractor<Pair<TT1, TT2>, R2, Pair<A, B>> pair(Extractor<TT1, R2, A> e1,
                                                                       Extractor<TT2, R2, B> e2) {
        return new PairExtractor<A, B, R>().compose(e1, e2);
    }

    public static <A, B, R> Pattern<Pair<A, B>, R> pair(Function<Pair<A, B>, R> f) {
        return new PairExtractor<A, B, R>().returnP(f);
    }

    public static <A, B, R> Pattern<Pair<A, B>, R> pair(BiFunction<A, B, R> f) {
        return pair(p -> f.apply(p.fst, p.snd));
    }
}
