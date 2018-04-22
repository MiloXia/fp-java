package javaz;

/**
 * my define.
 */
public abstract class Option<V> {
    private Option() {}

    public abstract boolean isEmpty();
    public abstract V orElse(V def);


    private static final Option<Object> NONE = new None<>();
    public static <V> Option<V> none() {
        return (Option<V>) NONE;
    }
    public static <V> Option<V> some(V value) {
        return new Some<>(value);
    }

    private static class Some<V> extends Option<V> {
        public final V value;

        public Some(V value) {
            this.value = value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public V orElse(V def) {
            return value;
        }
    }

    private static class None<V> extends Option<V> {
        public None() {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public V orElse(V def) {
            return def;
        }
    }



}
