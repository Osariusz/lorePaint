package org.osariusz.lorepaint.shared;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public interface Update {
    LocalDateTime getLore_date();

    LocalDateTime getCreated_at();

}
