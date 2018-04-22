package javaz;

/**
 * (A, B).
 */
public class Pair<A, B> {
    public final A fst;
    public final B snd;

    public Pair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "(" + fst + ", " + snd + ')';
    }

    public boolean equals(Pair<A, B> pair) {
        return pair.fst.equals(fst) && pair.snd.equals(snd);
    }

    public static <A, B> Pair<A, B> of(A fst, B snd) { return new Pair<>(fst, snd); }

//    public static class FstPair<A, B> extends Pair<A, B> {
//        public FstPair(A fst) {
//            super(fst, null);
//        }
//    }
//
//    public static <A, B> FstPair<A, B> fst(A fst) { return new FstPair<>(fst); }
//
//    public static class SndPair<A, B> extends Pair<A, B> {
//        public SndPair(B snd) {
//            super(null, snd);
//        }
//    }
//
//    public static <A, B> SndPair<A, B> snd(B snd) { return new SndPair<>(snd); }
}
