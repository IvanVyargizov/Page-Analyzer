package hexlet.code;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.annotation.WhenCreated;
import java.time.Instant;

@Entity
public final class Url extends Model {

    @Id
    private long id;

    @WhenCreated
    private Instant createdAt;

    private String name;

}
