package cn.monkey.order.handler;

public abstract class OrderIdGeneratorSupport implements OrderIdGenerator {
    protected  String concat(String prefix, String suffix){
        return prefix + suffix;
    }

    protected abstract String genSuffix();
}
