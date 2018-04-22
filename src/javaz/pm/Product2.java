package javaz.pm;

import javaz.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Product2.
 */
public interface Product2<A, B> {
    default <R, F extends Product2<A, B>> Extractor<Pair<A, B>, R, F> getExecutor() {
        final F self = (F) this;
        return new AbsExtractor<Pair<A, B>, R, F>() {
            @Override
            public Pattern<F, Pair<A, B>> returnPId() {
                return new Pattern<F, Pair<A, B>>() {
                    @Override
                    public boolean matches(F value) {
                        return value != null && self.getClass().isInstance(value);
                    }

                    @Override
                    public Pair<A, B> apply(F value) {
                        return value.toPair();
                    }
                };
            }
        };
    }

    default <R, F extends Product2<A, B>> Pattern<F, R> pattern(BiFunction<A, B, R> f) {
        return this.<R, F>getExecutor().returnP(p -> f.apply(p.fst, p.snd));
    }

    default Pair<A, B> toPair() {
        Class<?> clazz = this.getClass();
        List<Constructor<?>> constructors = Arrays.stream(clazz.getConstructors()).filter(__ -> __.isAnnotationPresent(CaseClass.class)).collect(Collectors.toList());
        List<Field> fields = new ArrayList<>(2);
        if(!constructors.isEmpty()) {
            Constructor<?> con = constructors.get(0);
            String[] fieldNames = con.getAnnotation(CaseClass.class).value();
            try {
                for(String fieldName : fieldNames) {
                    Field field = clazz.getField(fieldName);
                    field.setAccessible(true);
                    fields.add(field);
                }
                return new Pair<>((A) fields.get(0).get(this), (B) fields.get(1).get(this));
            } catch (Exception e) {
                throw new RuntimeException("toPair error", e);
            }
        } else {
            throw new RuntimeException("toPair error not find constructor with 2 args");
        }
    }
}
