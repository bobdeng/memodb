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
        });
    }

    @Test
    public void test() {
        TestEntity entity=new TestEntity("123","name");
        memoDB.insert(entity);
        assertThat(memoDB.all().size()).isEqualTo(1);
        Optional<TestEntity> find = memoDB.findBy("id", "123");
        assertThat(find.isPresent()).isEqualTo(true);
    }
}
