package com.example.eventsourcing.aggregate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Aggregate implements Persistable<String> {
    @Id
    String id;
    Integer version;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public void incrementVersion() {
        version++;
    }

    @Override
    public boolean isNew() {
        return version == 1;
    }
}
