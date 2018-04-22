package javaz.pm;

//import javaz.pm.extractors.OptionalExtractor;
import javaz.Pair;
import javaz.Try;
import javaz.pm.patterns.*;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * DSL helper.
 */
public class Patterns {
    public static <T, R> Pattern<T, R> caseOf(Class<? super T> clazz, Function<T, R> function) {
        return ClassPattern.inCaseOf(clazz, function);
    }
//    public static <T, R> Pattern<Object, R> caseOf(Class<T> clazz, Function<T, R> function) {
//        return ClassPattern.inCaseOf(clazz, function);
//    }
    public static <R> Pattern<Integer, R> caseOf(int pattern, Function<Integer, R> function) {
        return IntegerPattern.inCaseOf(pattern, function);
    }
    public static <R> Pattern<String, R> caseOf(String pattern, Function<String, R> function) {
        return StringPattern.inCaseOf(pattern, function);
    }
    public static <R> Pattern<Boolean, R> caseOf(boolean pattern, Function<Boolean, R> function) {
        return BooleanPattern.inCaseOf(pattern, function);
    }
    public static <T, R> Pattern<Optional<T>, R> caseOf(Optional<T> pattern, Function<Optional<T>, R> function) {
        return OptionalPattern.inCaseOf(pattern, function);
    }

    public static <T, R> Pattern<Try<T>, R> failure(Function<RuntimeException, R> function) {
        return FailurePattern.inCaseOf(x -> function.apply(((Try.Failure<T>) x).exception));
    }

    public static <A, B, R> Pattern<Pair<A, B>, R> caseOf(Pair<A, B> pattern, BiFunction<A, B, R> function) {
        return PairPattern.inCaseOf(pattern, x -> function.apply(x.fst, x.snd));
    }

//    public static <A, B, R> Pattern<Pair<A, B>, R> caseOf(Pair.FstPair<A, B> pattern, Function<B, R> function) {
//        return PairPattern.inCaseOf(pattern, x -> function.apply(x.snd));
//    }
//
//    public static <A, B, R> Pattern<Pair<A, B>, R> caseOf(Pair.SndPair<A, B> pattern, Function<A, R> function) {
//        return PairPattern.inCaseOf(pattern, x -> function.apply(x.fst));
//    }

    //    public static <T, R> Pattern<Optional<T>, R> caseOf2(Optional<T> pattern, Function<T, R> function) {
//        return OptionalExtractor.inCaseOf(pattern, function);
//    }
    public static <T, R> Pattern<T, R> otherwise(Function<T, R> function) {
        return OtherwisePattern.otherwise(function);
    }
}
