package javaz.cat;

import java.time.MonthDay;
import java.util.function.BiFunction;

/**
 * Monad combinator.
 */
public class Cat {
//    public static <F extends Monad<R, F>, R, T1, T2> Monad<R, F> liftM2(Monad<T1, F> t1, Monad<T2, F> t2, BiFunction<T1, T2, R> fun) {
//        return t1.flatMap(tv1 -> t2.map(tv2 -> fun.apply(tv1, tv2)));
//    }

    public static <T1, T2, R, F extends Monad<?, ?>> F liftM2(Monad<T1, F> t1, Monad<T2, F> t2, BiFunction<T1, T2, R> fun) {
        return t1.flatMap((T1 tv1) ->
                t2.map((T2 tv2) -> fun.apply(tv1, tv2))
        );
    }
}
