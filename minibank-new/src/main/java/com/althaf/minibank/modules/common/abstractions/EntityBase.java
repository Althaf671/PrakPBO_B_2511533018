package com.althaf.minibank.modules.common.abstractions;

import java.time.Instant;

import com.althaf.minibank.modules.common.interfaces.IEntity;

public abstract class EntityBase implements IEntity {
    
    // attributes
    private final String id;
    private boolean isDeleted;
    private Instant deletedAt;
    private Instant updatedAt;
    private final Instant createdAt;

    // getter
    public String getId() { return id; }

    @Override 
    public boolean isDeleted() { return isDeleted; }

    @Override 
    public Instant getDeletedAt() { return deletedAt; }

    @Override 
    public Instant getUpdatedAt() { return updatedAt; }

    @Override 
    public Instant getCreatedAt() { return createdAt; }

    // constructor
    protected EntityBase(String i) {
        id = i;
        createdAt = Instant.now();
    }

    // behaviour
    protected void markAsUpdated() {
        updatedAt = Instant.now();
    }

    protected void markAsDeleted() {
        isDeleted = true;
        deletedAt = Instant.now();
    }
}
