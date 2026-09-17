package com.althaf.minibank.modules.common.interfaces;

import java.time.Instant;

public interface IAuditable {
    public Instant getUpdatedAt();
    public Instant getCreatedAt();
}
