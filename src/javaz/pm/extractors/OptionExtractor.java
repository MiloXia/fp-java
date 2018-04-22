package javaz.pm.extractors;

import javaz.pm.AbsExtractor;
import javaz.pm.Pattern;

import java.util.Optional;

public class OptionExtractor<T, R> extends AbsExtractor<T, R, Optional<T>> {

//    public OptionExtractor(Function<T, R> function) {
//        super(function);
//    }

//    @Override
//    public Pattern<Optional<T>, R> returnP() {
//        return new Pattern<Optional<T>, R>() {
//            @Override
//            public boolean matches(Optional<T> value) {
//                return returnPId().matches(value);
//            }
//
//            @Override
//            public R apply(Optional<T> value) {
//                return function.apply(returnPId().apply(value));
//            }
//        };
//    }

    @Override
    public Pattern<Optional<T>, T> returnPId() {
        return new Pattern<Optional<T>, T>() {
            @Override
            public boolean matches(Optional<T> value) {
                return value.isPresent();
            }

            @Override
            public T apply(Optional<T> value) {
                return value.get();
            }
        };
    }


}
