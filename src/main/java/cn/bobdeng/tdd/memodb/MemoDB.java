package cn.bobdeng.tdd.memodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemoDB<T> {

    private final Mapper<T> mapper;
    private Map<String, Map<String, String>> datas;

    public MemoDB(Mapper<T> mapper) {
        this.mapper = mapper;
        datas = new HashMap<String, Map<String, String>>();
    }

    public void insert(T entity) {
        datas.put(UUID.randomUUID().toString(), mapper.toMap(entity));
    }

    public List<T> all() {
        return datas.values()
                .stream()
                .map(data -> mapper.from(data))
                .collect(Collectors.toList());
    }
}
