package javaz.demo;

import javaz.pm.CaseClass;
import javaz.pm.Pattern;
import javaz.pm.Product1;
import javaz.pm.Product2;

import java.util.function.BiFunction;
import java.util.function.Function;

import static javaz.Bind.let;
import static javaz.demo.ExpDemo.Exp.*;
import static javaz.pm.Extractors.*;
import static javaz.pm.Guard.guard;
import static javaz.pm.Patterns.caseOf;
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
