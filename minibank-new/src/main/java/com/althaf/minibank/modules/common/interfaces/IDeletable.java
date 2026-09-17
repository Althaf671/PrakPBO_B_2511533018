package com.althaf.minibank.modules.common.interfaces;

import java.time.Instant;

public interface IDeletable {
    public boolean isDeleted();
    public Instant getDeletedAt();
}
