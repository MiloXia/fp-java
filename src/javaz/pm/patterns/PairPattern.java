package javaz.pm.patterns;

import javaz.Pair;
import javaz.pm.Pattern;

import java.util.Optional;
import java.util.function.Function;

public class PairPattern<A, B, R> implements Pattern<Pair<A, B>, R> {
    private final Pair<A, B> pattern;
    private final Function<Pair<A, B>, R> function;

    public PairPattern(Pair<A, B> pattern, Function<Pair<A, B>, R> function) {
        this.pattern = pattern;
        this.function = function;
    }

    @Override
    public boolean matches(Pair<A, B> value) {
//        if(pattern instanceof Pair.FstPair<?, ?>) {
//            return value.fst.equals(pattern.fst);
//        } else if(pattern instanceof Pair.SndPair<?, ?>) {
//            return value.snd.equals(pattern.snd);
//        } else {
            return pattern.equals(value);
//        }
    }

    @Override
    public R apply(Pair<A, B> value) {
        return function.apply(value);
    }

    public static <A, B, R> Pattern<Pair<A, B>, R> inCaseOf(Pair<A, B> pattern, Function<Pair<A, B>, R> function) {
        return new PairPattern<>(pattern, function);
    }
}
