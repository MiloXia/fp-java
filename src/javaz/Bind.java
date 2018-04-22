package javaz;

import javaz.cat.Functor;
import javaz.cat.Higher;
import javaz.pm.Pattern;
import javaz.pm.PatternMatching;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ID monad.
 */
public class Bind<T> implements Functor<T, Bind<?>> , Higher<Bind.µ, T> {
    public final T var;
    public Bind(T _var) {
        this.var = _var;
    }
    public <R> R to(Function<? super T, ? extends R> f) {
        return f.apply(var);
    }

    @Override
    public <R> Bind<R> map(Function<T, R> f) {
        return Bind.let(to(f));
    }

    public void forEach(Consumer<T> f) {
        f.accept(var);
    }


    public <R> R match(PatternMatching<T, R> pm) {
        return pm.matchFor(var);
    }

    @SafeVarargs
    final public <R> Bind<R> match(Pattern<? extends T, ? extends R>... patterns) {
        return Bind.let(PatternMatching.create(patterns).matchFor(var));
    }

    @Override
    public String toString() {
        return "Bind(" + var + ')';
    }

    public static <T> Bind<T> let(T var) {
        return new Bind<>(var);
    }


    //cast for Higher
    public static <T> Bind<T> narrowK(Higher<Bind.µ, T> hkt){
        return (Bind<T>)hkt;
    }
    //Higher
    public static class µ { }
    //functor
    static public class BindFunctor implements javaz.cat._Functor<Bind.µ> {
        @Override
        public <T, R> Higher<Bind.µ, R> map(Function<? super T, ? extends R> fn, Higher<Bind.µ, T> ds) {
            return Bind.let(((Bind<T>) ds).to(fn)); //((Bind<T>) ds) is narrowK : (Higher<Bind.µ, T>) -> (Bind<T>)
        }
    }
    public static BindFunctor functor() {return new BindFunctor();}
    //monad
    static public class BindMonad extends BindFunctor implements javaz.cat._Monad<Bind.µ> {
        //return type Higher<µ, R> means need cast to Bind<R>
        @Override
        public <T, R> Higher<µ, R> flatMap(Function<? super T, ? extends Higher<µ, R>> fn, Higher<µ, T> ds) {
            return ((Bind<T>) ds).to(fn); //((Bind<T>) ds) is narrowK : (Higher<Bind.µ, T>) -> (Bind<T>)
        }
    }
    public static BindMonad monad() {return new BindMonad();}
}

//__Bind<T> implements Bind<T>, Higher<__Bind.µ, T>
//Bind<T> = __Bind<T>
//Higher<__Bind.µ, T> = __Bind<T>
//narrowK : (Higher<Bind.µ, T>) -> (Bind<T>)
//Higher<__Bind.µ, T> -> __Bind<T> -> Bind<T> , ((Bind<T>) ds)
//省略了__Bind<T>，自定义类型不需要warp，库类型（Optional）需要

