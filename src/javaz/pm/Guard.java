package javaz.pm;

import java.util.function.Predicate;

/**
 * Pattern Guard.
 */
public class Guard {
    public static <T, R> Pattern<T, R> guard(final Pattern<T, R> pattern, final Predicate<T> p) {
        return new Pattern<T, R>() {
            @Override
            public boolean matches(T value) {
                return pattern.matches(value) && p.test(value);
            }

            @Override
            public R apply(T value) {
                return pattern.apply(value);
            }
        };
    }

    public static <T, R, F> Extractor<T, R, F> guard(final Extractor<T, R, F> extractor, final Predicate<T> p) {
        return new AbsExtractor<T, R, F>() {
            @Override
            public Pattern<F, T> returnPId() {
                return new Pattern<F, T>() {
                    @Override
                    public boolean matches(F value) {
                        return extractor.returnPId().matches(value) && p.test(extractor.returnPId().apply(value));
                    }

                    @Override
                    public T apply(F value) {
                        return extractor.returnPId().apply(value);
                    }
                };
            }
        };
    }
}
