package javaz.pm;

public class PatternMatching <T, R> {
    private Pattern<? extends T, ? extends R>[] patterns;
    public PatternMatching(Pattern<? extends T, ? extends R>... patterns) { this.patterns = patterns; }
    @SuppressWarnings("unchecked")
    public R matchFor(T value) {
        for (Pattern<? extends T, ? extends R> pattern : patterns) {
            boolean res;
            try {
                res = ((Pattern<T, R>) pattern).matches(value);
            } catch (ClassCastException e) {
                res = false;
            }
            if (res) return ((Pattern<T, R>) pattern).apply(value);
        }
        throw new IllegalArgumentException("cannot match " + value);
    }

    @SafeVarargs
    public static <T, R> PatternMatching<T, R> create(Pattern<? extends T, ? extends R>... patterns) {
        return new PatternMatching<>(patterns);
    }
}

