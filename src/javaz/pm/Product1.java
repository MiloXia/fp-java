package javaz.pm;

import javaz.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
public interface Product1<A> {
    default <R, F extends Product1<A>> Extractor<A, R, F> getExecutor() {
        final F self = (F) this;
        return new AbsExtractor<A, R, F>() {
            @Override
            public Pattern<F, A> returnPId() {
                return new Pattern<F, A>() {
                    @Override
                    public boolean matches(F value) {
                        return value != null && self.getClass().isInstance(value);
                    }

                    @Override
                    public A apply(F value) {
                        return value.getA();
                    }
                };
            }
        };
    }

    default <R, F extends Product1<A>> Pattern<F, R> pattern(Function<A, R> f) {
        return this.<R, F>getExecutor().returnP(f);
    }

    default A getA() {
        Class<?> clazz = this.getClass();
        List<Constructor<?>> constructors = Arrays.stream(clazz.getConstructors()).filter(__ -> __.isAnnotationPresent(CaseClass.class)).collect(Collectors.toList());
        if(!constructors.isEmpty()) {
            Constructor<?> con = constructors.get(0);
            String[] fieldNames = con.getAnnotation(CaseClass.class).value();
            try {
                Field field = clazz.getField(fieldNames[0]);
                field.setAccessible(true);
                return (A) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException("getA error", e);
            }
        } else {
            throw new RuntimeException("getA error not find constructor with 1 args");
        }
    }
}
