package org.osariusz.lorepaint.shared;

import org.osariusz.lorepaint.user.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class UpdateService<T, U extends Update> {

    public abstract List<U> getAllUpdates(T updatedObject);

    public abstract boolean objectAccessFunction(T updatedObject, UserDTO userDTO);

    public List<U> getAvailableUpdates(List<T> objects, UserDTO userDTO, long loreId) {
        List<U> updates = new ArrayList<>();
        List<T> places = objects.stream().filter(
                (place) -> objectAccessFunction(place, userDTO)
        ).toList();
        for (T object : places) {
            updates.addAll(getAllUpdates(object));
        }
        return updates;
    }

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
        }).orElseThrow(() -> new NoSuchElementException("No update found"));
    }
}
