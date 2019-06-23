package cn.bobdeng.tdd.memodb;

import java.util.*;
import java.util.stream.Collectors;

public class MemoDB<T> {

    private final Mapper<T> mapper;
    private Map<String, Map<String, String>> datas;

    public MemoDB(Mapper<T> mapper) {
        this.mapper = mapper;
        datas = new HashMap<String, Map<String, String>>();
    }

    public void insert(T entity) {
        if (findEntity(entity).isPresent()) {
            return;
        }
        datas.put(UUID.randomUUID().toString(), mapper.toMap(entity));
    }

    public List<T> all() {
        return datas.values()
                .stream()
                .map(data -> mapper.from(data))
                .collect(Collectors.toList());
    }

    public Optional<T> findBy(String key, String value) {
        return datas.values()
                .stream()
                .filter(data->value.equals(data.get(key)))
                .map(mapper::from)
                .findFirst();
    }

    public void deleteBy(String key, String value) {
        datas.entrySet().stream()
                .filter(stringMapEntry -> stringMapEntry.getValue().get(key).equals(value))
                .findFirst()
                .ifPresent(stringMapEntry -> datas.remove(stringMapEntry.getKey()));
    }

    public void save(T entity) {
        findEntity(entity)
                .ifPresent(data->data.putAll(mapper.toMap(entity)));
    }

    private Optional<Map<String, String>> findEntity(T entity) {
        return datas.values().stream()
                .filter(data->mapper.from(data).equals(entity))
                .findFirst();
    }
}
