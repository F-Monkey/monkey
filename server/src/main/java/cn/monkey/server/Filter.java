package cn.monkey.server;

public interface Filter<Pkg> {
    boolean filter(Session session, Pkg pkg);
}
