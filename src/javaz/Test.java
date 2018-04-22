package javaz;

import javaz.pm.*;
import javaz.cat.Cat;

import java.util.Optional;
import java.util.function.BiFunction;

import static javaz.Bind.let;
import static javaz.pm.Extractors.*;
import static javaz.pm.Guard.guard;
import static javaz.pm.Patterns.caseOf;
import static javaz.pm.Patterns.failure;
import static javaz.pm.Patterns.otherwise;

/**
 * test demo.
 */
public class Test {
    public static class A {
        @Override
        public String toString() {
            return "A{}";
        }
    }
    public static class B extends A {
        @Override
        public String toString() {
            return "B{}";
        }
    }
    public static class C extends A implements Product2<Integer, String> {
        public final int i;
        public final String a;

        @CaseClass({"i", "a"})
        public C(int i, String a) {
            this.i = i;
            this.a = a;
        }

        @Override
        public String toString() {
            return "C(" + "i=" + i + ", a='" + a + '\'' + ')';
        }
        //TODO extractor compose
//        public <R> Pattern<C, R> returnP(BiFunction<Integer, String, R> f) { return pattern(f);}

        private static final C single = new C(0, "");
        public static <R> Pattern<C, R> c(BiFunction<Integer, String, R> f) {
            return single.pattern(f);
        }
    }

    public static class D extends A implements Product2<Optional<Integer>, String> {
        public final Optional<Integer> i;
        public final String a;

        @CaseClass({"i", "a"})
        public D(Optional<Integer> i, String a) {
            this.i = i;
            this.a = a;
        }

        @Override
        public String toString() {
            return "D(" + "i=" + i + ", a='" + a + '\'' + ')';
        }
        //TODO extractor compose
//        public <R> Pattern<C, R> returnP(BiFunction<Integer, String, R> f) { return pattern(f);}

        private static final D single = new D(Optional.empty(), "");
        public static <R> Pattern<D, R> d(BiFunction<Optional<Integer>, String, R> f) {
            return single.pattern(f);
        }

        public static <R2> Pattern<D, R2> d(Extractor<Integer, R2, Optional<Integer>> e1, Extractor<String, R2, String> e2,  BiFunction<Integer, String, R2> f) {
            return single.<R2, D>getExecutor().compose(e1, e2).returnP(p -> f.apply(p.fst, p.snd));
        }

        public static <R2> Pattern<D, R2> d2(Extractor<Optional<Integer>, R2, Optional<Integer>> e1, Extractor<String, R2, String> e2,  BiFunction<Optional<Integer>, String, R2> f) {
            return single.<R2, D>getExecutor().compose(e1, e2).returnP(p -> f.apply(p.fst, p.snd));
        }
    }

//    public static class C_Extractor<R> extends AbsExtractor2<Integer, String, R, C> {
//        @Override
//        public Pattern<C, Pair<Integer, String>> returnPId() {
//            return new Pattern<C, Pair<Integer, String>>() {
//                @Override
//                public boolean matches(C value) {
//                    return value != null;
//                }
//
//                @Override
//                public Pair<Integer, String> apply(C value) {
//                    return Pair.of(value.i, value.a);
//                }
//            };
//        }
//    }

//    public static <R> Pattern<C, R> c(BiFunction<Integer, String, R> f) { return new C_Extractor<R>().returnP(f); }

    public static int fact(int n) {
        return let(n).match(
                caseOf(0, __ -> 1),
                otherwise(__ -> n * fact(n - 1))
        ).var;
    }

    public static void main(String[] args) {
        System.out.println("=============pattern==============");
        let(1).match(
                caseOf(1, x -> x + 1),
                caseOf(2, x -> "ok")
        ).forEach(x -> System.out.println(x + "x")); //x : java.io.Serializable & Comparable<? extends java.io.Serializable & Comparable<?>>

        let(1).match(
                caseOf(1, x -> x + 1),
                caseOf(2, x -> 2),
                caseOf(3, x -> 3)
        ).forEach(x -> System.out.println(x + 1));//x : Integer

        let(1).match(
                caseOf(1, x -> new B()),
                caseOf(2, x -> new C(1, "a"))
        ).forEach(x -> System.out.println(x)); //x : A

        let(1).match(
                caseOf(1, x -> x + 1),
                caseOf(2, x -> 2),
                otherwise(x -> x)
        ).forEach(x -> System.out.println(x + 1));//x : Integer

        let((A) new B()).match(
                caseOf(B.class, B::toString),
                caseOf(C.class, C::toString),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));


        Pattern<Integer, Integer> c1 = caseOf(Integer.class, x -> x + 1);
        Pattern<? extends Number, ? extends Number> cc1 = c1;
        Pattern<Double, Number> c2 = caseOf(Double.class, x -> x + 1.0);
        Pattern<? extends Number, ? extends Number> cc2 = c2;
        PatternMatching.<Number, Number>create(c1, c2);
        Bind.<Number>let(1).match(
                caseOf(Integer.class, x -> x + 1),
                caseOf(Double.class, x -> x + 1.0),
                otherwise(x -> x)
        ).forEach(x -> System.out.println(x));

        let("3").match(
                //caseOf(Integer.class, x -> x + 1),
                caseOf("a", x -> x + "aa"),
                caseOf(String.class, x -> x + "a"),
                otherwise(x -> x)
        ).forEach(x -> System.out.println("x= " + x));

        let(true).match(
                caseOf(true, x -> "T"),
                caseOf(false, x -> "F")
        ).forEach(x -> System.out.println(x));

//        let(Optional.of(1)).match(
//                Patterns.caseOf2(some(), x -> x + 1),
//                caseOf(Optional.of(1), x -> x.get()),
//                caseOf(Optional.empty(), __ -> 0),
//                otherwise(x -> Integer.MAX_VALUE)
//        ).forEach(x -> System.out.println(x));

        let(Optional.of(1)).match(
                some(x -> x + 1),
                caseOf(Optional.of(1), x -> x.get()),
                caseOf(Optional.empty(), __ -> 0),
                otherwise(x -> Integer.MAX_VALUE)
        ).forEach(x -> System.out.println(x));

        let(Try.delay(() -> 1)).match(
                success(x -> "res = " + x),
                failure(e -> e.getMessage())
        ).forEach(x -> System.out.println(x));

        //test compose

        Extractor<Optional<Integer>, Integer, Try<Optional<Integer>>> successEx = success();
        Extractor<Integer, String, Optional<Integer>> someEx = some();
        successEx.compose(someEx);
        let(Try.delay(() -> Optional.of(1))).match(
                //Extractors.<Optional<Integer>, String>successEx().compose(someEx()).returnP(x -> x + "a"),
                success(some(), x -> x + "a"),
                //successEx.compose(someEx).returnP(x -> x + "a"),
                success(x -> "res = " + x),
                failure(e -> e.getMessage())
        ).forEach(x -> System.out.println(x));

        //pair
//        let(Pair.of(1, "a")).match(
//                caseOf(Pair.snd("a"), x -> x + 1),
//                caseOf(Pair.of(1, "b"), (x, y) -> x + 1),
//                pair((x, y) -> x),
//                otherwise(x -> Integer.MAX_VALUE)
//        ).forEach(x -> System.out.println(x));

        let(Pair.of(Optional.of(1), Try.delay(() -> "a"))).match(
                pair(some(), success(), (i, a) -> i + a),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(1, "a")).match(
                guard(pair((x, y) -> y), p -> p.fst == 1),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Optional.of(Pair.of(1, "a"))).match(
                some(pair(), p -> "res = " + p.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(Pair.of(1, "a"), Pair.of(1, "b"))).match(
                pair(pair(), pair(), (p1, p2) -> "res = " + p1.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

//        let(new C(1, "a")).match(
//                guard(c((i, a) -> i + a), c -> c.i == 1),
//                caseOf(C.class, x -> x.a),
//                otherwise(x -> "other")
//        ).forEach(x -> System.out.println(x));

        let((A) new C(2, "a")).match(
                guard(C.c((i, a) -> i + a), c -> c.i == 2),
                caseOf(C.class, x -> x.a),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        C cc = new C(3, "a");
        let(cc).match(
                guard(cc.pattern((i, a) -> i + a), c -> c.i == 3),
                caseOf(C.class, x -> x.a),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(Pair.of(1, "a"), 2)).match(
                pair(pair(), _const(2), (p1, p2) -> "res = " + p2),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(Pair.of(1, "a"), 2)).match(
                pair(pair(), _type(Integer.class), (p1, p2) -> "res = " + p2),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(Pair.of(1, "a"), Pair.of(1, "b"))).match(
                pair(pair(), guard(pair(), p2 -> p2.fst == 1), (p1, p2) -> "res = " + p1.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Pair.of(1, "a")).match(
                pair(_const(1), _const("a"), (f, s) -> s),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Optional.of(Pair.of(1, "a"))).match(
                some(toEx(pair(_const(1), _type(String.class), __ -> __)), p -> p.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Optional.of(Pair.of(1, "a"))).match(
                some(pair(_const(1), _type(String.class)), p -> p.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(Optional.of(Pair.of(1, "a"))).match(
                some(guard(pair(), p -> p.fst == 1), x -> x.snd),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        let(1).match(
                caseOf(1, x -> 1),
                caseOf(2, x -> 1.0)
        ).forEach(x -> System.out.println(x + "x"));

        let(new D(Optional.of(1), "a")).match(
                D.d2(_const(Optional.of(1)), _const("a"), (i, a) -> a + "aa"),
                D.d(some(), _const("a"), (i, a) -> a + "a"),
                D.d((iopt, a) -> a),
                otherwise(x -> "other")
        ).forEach(x -> System.out.println(x));

        System.out.println("=============monad==============");
        Bind<Integer> br1 = Bind.narrowK(Bind.functor().map(x -> x + 1, let(1)));
        System.out.println(br1);
        Bind<String> br2 = Bind.narrowK(Bind.monad().flatMap(x -> let(x + "a"), let(1)));
        System.out.println(br2);
        //monad
        Bind<Integer> b1 = let(1).map(x -> x + 1);
        Try<Integer> t1 = Try.delay(() -> 1);
        Try<Integer> t2 = Try.delay(() -> 2);
        Try<Integer> tt1 = t1.map((Integer tv1) -> tv1 + 2);
        Try<?> t3 = t1.flatMap(tv1 -> t2.map(tv2 -> tv1 + tv2));
        let((Try<Integer>) t3).forEach(x -> System.out.println(x.getOrElse(0)));
        Try<?> t4 = Cat.liftM2(t1, t2, (_t1, _t2) -> _t1 + _t2);

        System.out.println(t1.isSuccess());
        System.out.println(t4.isSuccess());
    }
}
