# A Functional Programming Library For Java

It just provided some monads, and pattern matching like Scala. Now, it just is a baby, if I have time, I will finish it.

### Pattern Matching
You can use it just like Scala:
```
//factorial
public static int fact(int n) {
    return let(n).match(
            caseOf(0, __ -> 1),
            otherwise(__ -> n * fact(n - 1))
    ).var;
}

//some constent match
let(1).match(
        caseOf(1, x -> 1),
        caseOf(2, x -> 1.0)
).forEach(x -> System.out.println(x + "x"));

//guard
let((A) new C(2, "a")).match(
        guard(C.c((i, a) -> i + a), c -> c.i == 2),
        caseOf(C.class, x -> x.a),
        otherwise(x -> "other")
).forEach(x -> System.out.println(x));

//pair & some & try
let(Optional.of(Pair.of(1, "a"))).match(
        some(pair(_const(1), _type(String.class)), p -> p.snd),
        otherwise(x -> "other")
).forEach(x -> System.out.println(x));

let(Optional.of(Pair.of(1, "a"))).match(
        some(pair(), p -> "res = " + p.snd),
        otherwise(x -> "other")
).forEach(x -> System.out.println(x));


let(Pair.of(Optional.of(1), Try.delay(() -> "a"))).match(
        pair(some(), success(), (i, a) -> i + a),
        otherwise(x -> "other")
).forEach(x -> System.out.println(x));
```

### A Classic FP Demo
```
import javaz.pm.CaseClass;
import javaz.pm.Pattern;
import javaz.pm.Product1;
import javaz.pm.Product2;

import java.util.function.BiFunction;
import java.util.function.Function;

import static javaz.Bind.let;
import static javaz.demo.ExpDemo.Exp.*;
/**
 * Classic FP
 */
public class ExpDemo {

    //algebraic data type
    static abstract class Exp {
        public static Literal literal(int v) {
            return new Literal(v);
        }
        private static final Literal S_Literal = literal(0);
        public static <R> Pattern<Literal, R> literal(Function<Integer, R> f) {
            return S_Literal.pattern(f);
        }

        public static Add add(Exp a, Exp b) {
            return new Add(a, b);
        }
        private static final Add S_Add = add(S_Literal, S_Literal);
        public static <R> Pattern<Add, R> add(BiFunction<Exp, Exp, R> f) {
            return S_Add.pattern(f);
        }

        public static Subtract subtract(Exp a, Exp b) {
            return new Subtract(a, b);
        }
        private static final Subtract S_Subtract = subtract(S_Literal, S_Literal);
        public static <R> Pattern<Subtract, R> subtract(BiFunction<Exp, Exp, R> f) {
            return S_Subtract.pattern(f);
        }

        public static Multiply multiply(Exp a, Exp b) {
            return new Multiply(a, b);
        }
        private static final Multiply S_Multiply = multiply(S_Literal, S_Literal);
        public static <R> Pattern<Multiply, R> multiply(BiFunction<Exp, Exp, R> f) {
            return S_Multiply.pattern(f);
        }
    }

    final static class Literal extends Exp implements Product1<Integer> {
        public final int val;

        @CaseClass({"val"})
        public Literal(int val) {
            this.val = val;
        }
    }

    final static class Add extends Exp implements Product2<Exp, Exp> {
        public final Exp a;
        public final Exp b;

        @CaseClass({"a", "b"})
        public Add(Exp a, Exp b) {
            this.a = a;
            this.b = b;
        }
    }

    final static class Subtract extends Exp implements Product2<Exp, Exp> {
        public final Exp a;
        public final Exp b;

        @CaseClass({"a", "b"})
        public Subtract(Exp a, Exp b) {
            this.a = a;
            this.b = b;
        }
    }

    final static class Multiply extends Exp implements Product2<Exp, Exp> {
        public final Exp a;
        public final Exp b;

        @CaseClass({"a", "b"})
        public Multiply(Exp a, Exp b) {
            this.a = a;
            this.b = b;
        }
    }

    //interpreter
    static int eval(Exp e) {
        return let(e).match(
                literal(v       -> v),
                subtract((a, b) -> eval(a) - eval(b)),
                add((a, b)      -> eval(a) + eval(b)),
                multiply((a, b) -> eval(a) * eval(b))
        ).var;
    }

    static String prettyPrint(Exp e) {
        return let(e).match(
                literal(v       -> v + ""),
                subtract((a, b) -> "(" + prettyPrint(a) + " - " + prettyPrint(b) + ")"),
                add((a, b)      -> "(" + prettyPrint(a) + " + " + prettyPrint(b) + ")"),
                multiply((a, b) -> "(" + prettyPrint(a) + " * " +  prettyPrint(b) + ")")
        ).var;
    }

    public static void main(String[] args) {
        Exp exp1 = subtract(add(Exp.literal(1), literal(2)), multiply(literal(3), literal(1)));
        int res = eval(exp1);
        System.out.println(res); // 0

        String str = prettyPrint(exp1);
        System.out.println(str); //((1 + 2) - (3 * 1))
    }
}
```


