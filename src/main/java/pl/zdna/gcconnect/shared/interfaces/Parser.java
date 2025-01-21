package pl.zdna.gcconnect.shared.interfaces;

public interface Parser<U, V> {
    V parse(U u) throws Exception;
}
