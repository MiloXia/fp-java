package javaz;

import javaz.cat.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Try monad.
 */
public abstract class Try<V> implements Monad<V, Try<?>> {
    private Try() {
    }
    public abstract Boolean isSuccess();
    public abstract Boolean isFailure();
    public abstract V getOrElse(V def);

    public abstract <R> Try<R> map(Function<V, R> f);

    public abstract Try<?> flatMap(Function<V, Try<?>> f);

    public static <V> Try<V> delay(Supplier<V> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    public static <V> Try<V> failure(Exception e) {
        return new Failure<>(e);
    }
    public static <V> Try<V> success(V value) {
        return new Success<>(value);
    }

    public static class Failure<V> extends Try<V> {
        public final RuntimeException exception;

        public Failure(Exception e) {
            this.exception = new IllegalStateException(e);
        }
        @Override
        public Boolean isSuccess() {
            return false;
        }
        @Override
        public Boolean isFailure() {
            return true;
        }

        @Override
        public V getOrElse(V def) {
            return def;
        }

        @Override
        public <R> Try<R> map(Function<V, R> f) {
            return (Try<R>) this;
        }

        @Override
        public Try<?> flatMap(Function<V, Try<?>> f) {
            return this;
        }
    }
    public static class Success<V> extends Try<V> {
        public final V value;
        public Success(V value) {
            this.value = value;
        }
        @Override
        public Boolean isSuccess() {
            return true;
        }
        @Override
        public Boolean isFailure() {
            return false;
        }

        @Override
        public V getOrElse(V def) {
            return value;
        }

        @Override
        public <R> Try<R> map(Function<V, R> f) {
            return Try.success(f.apply(value));
        }

        @Override
        public Try<?> flatMap(Function<V, Try<?>> f) {
            return f.apply(value);
        }
    }
}
