package hexlet.code.domain;

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

    public Url() {
    }

    public Url(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

}
