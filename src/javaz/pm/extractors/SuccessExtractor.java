package javaz.pm.extractors;

import javaz.Try;
import javaz.pm.AbsExtractor;
import javaz.pm.Pattern;

public class SuccessExtractor<T, R> extends AbsExtractor<T, R, Try<T>> {

//    public SuccessExtractor(Function<T, R> function) {
//        super(function);
//    }

    @Override
    public Pattern<Try<T>, T> returnPId() {
        return new Pattern<Try<T>, T>() {
            @Override
            public boolean matches(Try<T> value) {
                return value.isSuccess();
            }

            @Override
            public T apply(Try<T> value) {
                return ((Try.Success<T>) value).value;
            }
        };
    }
}
