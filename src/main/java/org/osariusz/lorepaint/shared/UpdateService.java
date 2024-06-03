package org.osariusz.lorepaint.shared;

import java.time.LocalDateTime;
import java.util.List;

public abstract class UpdateService<T, U extends Update> {

    public abstract List<U> getAllUpdates(T updatedObject);

    public List<U> getAllUpdatesUpTo(T updatedObject, LocalDateTime localDateTime) {
        List<U> allUpdates = getAllUpdates(updatedObject);
        return allUpdates.stream().filter(U -> !U.getLore_date().isAfter(localDateTime)).toList();
    }

    public U getLastUpdate(T updatedObject, LocalDateTime localDateTime) {
        return getAllUpdatesUpTo(updatedObject, localDateTime).stream().max((update1, update2) -> {
            if (update1.getLore_date().isEqual(update2.getLore_date())) {
                return update1.getCreated_at().compareTo(update2.getCreated_at());
            }
            return update1.getLore_date().compareTo(update2.getLore_date());
        }).orElseThrow(() -> new RuntimeException("No update found"));
    }
}
