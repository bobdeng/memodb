package cn.bobdeng.tdd.memodb;

import java.util.Map;

public interface Mapper<T> {
    Map<String, String> toMap(T t);

    T from(Map<String, String> params);

    boolean equals(T a,T b);
}
