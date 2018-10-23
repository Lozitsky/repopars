package com.kirilo.faynoe.repopars.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Persistable<Integer> {
    public static final int START_SEQ = 1000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof AbstractEntity)) return false;
        AbstractEntity that = (AbstractEntity) obj;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
