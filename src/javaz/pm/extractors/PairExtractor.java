package javaz.pm.extractors;

import javaz.Pair;
import javaz.pm.AbsExtractor;
import javaz.pm.Pattern;


public class PairExtractor<A, B, R> extends AbsExtractor<Pair<A, B>, R, Pair<A, B>> {

    @Override
    public Pattern<Pair<A, B>, Pair<A, B>> returnPId() {
        return new Pattern<Pair<A, B>, Pair<A, B>>() {
            @Override
            public boolean matches(Pair<A, B> value) {
                return true;
            }

            @Override
            public Pair<A, B> apply(Pair<A, B> value) {
                return value;
            }
        };
    }
}


//public class PairExtractor<A, B, R> extends AbsExtractor2<A, B, R, Pair<A, B>> {
//
//    @Override
//    public Pattern<Pair<A, B>, Pair<A, B>> returnPId() {
//        return new Pattern<Pair<A, B>, Pair<A, B>>() {
//            @Override
//            public boolean matches(Pair<A, B> value) {
//                return true;
//            }
//
//            @Override
//            public Pair<A, B> apply(Pair<A, B> value) {
//                return value;
//            }
//        };
//    }
//}