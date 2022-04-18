package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
public class UrlCheck extends Model {
    @Id
    private long id;

    private int statusCode;

    private String title;

    private String h1;

    @Lob
    private String description;

    @WhenCreated
    private Instant createdAt;

    @ManyToOne
    private Url url;

    public UrlCheck(int statusCode, String title, String h1, String description, Url url) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.url = url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getH1() {
        return h1;
    }

    public String getTitle() {
        return title;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getId() {
        return id;
    }
}