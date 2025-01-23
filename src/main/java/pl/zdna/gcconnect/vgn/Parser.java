package pl.zdna.gcconnect.vgn;

public interface Parser<U, V> {
    V parse(U u) throws Exception;
}
