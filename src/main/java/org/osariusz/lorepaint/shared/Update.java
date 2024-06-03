package org.osariusz.lorepaint.shared;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public interface Update {
    public LocalDateTime getLore_date();

    public LocalDateTime getCreated_at();

}
