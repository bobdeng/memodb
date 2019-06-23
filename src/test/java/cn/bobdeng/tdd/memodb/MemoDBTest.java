package cn.bobdeng.tdd.memodb;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MemoDBTest {

    private MemoDB<TestEntity> memoDB;

    @Before
    public void setup() {
        memoDB = new MemoDB(new Mapper<TestEntity>() {
            public Map<String, String> toMap(TestEntity o) {
                Map<String,String> values=new HashMap<>();
                values.put("id",o.id);
                values.put("name",o.name);
                return values;
            }

            public TestEntity from(Map<String,String> params) {
                TestEntity entity=new TestEntity(params.get("id"),params.get("name"));
                return entity;
            }

            @Override
            public boolean equals(TestEntity a, TestEntity b) {
                return a.id.equals(b.id);
            }
        });
    }

    @Test
    public void test_crud() {
        TestEntity entity=new TestEntity("123","name");
        memoDB.insert(entity);
        assertThat(memoDB.all().size()).isEqualTo(1);
        Optional<TestEntity> find = memoDB.findBy("id", "123");
        assertThat(find.isPresent()).isEqualTo(true);
        memoDB.deleteBy("id", "123");
        assertThat(memoDB.findBy("id", "123").isPresent()).isEqualTo(false);
        //entity.name="name2";
        //memoDB.save(entity);
        //TestEntity entity1 = memoDB.findBy("id", "123").get();
        //assertThat(entity1.name).isEqualTo("name2");
    }
    @Test
    public void test_find() {
        memoDB.insert(new TestEntity("123","name"));
        memoDB.insert(new TestEntity("123","name"));
        assertThat(memoDB.all().size()).isEqualTo(1);

    }
    @Test
    public void test_search() {
        memoDB.insert(new TestEntity("123","name1"));
        memoDB.insert(new TestEntity("112","name2"));
        memoDB.insert(new TestEntity("113","hello"));
        assertThat(memoDB.search((entity)->entity.name.contains("name")).size()).isEqualTo(2);
        memoDB.delete(new TestEntity("112","name2"));
        assertThat(memoDB.search((entity)->entity.name.contains("name")).size()).isEqualTo(1);

    }
}
