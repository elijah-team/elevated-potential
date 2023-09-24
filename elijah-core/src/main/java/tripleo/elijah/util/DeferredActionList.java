package tripleo.elijah.util;

import java.util.*;

public class DeferredActionList<T> {
    private List<DeferredAction<T>> backing = new ArrayList<>();

    DeferredAction<T> get(int index) {
        return backing.get(index);
    }

    void add(DeferredAction<T> item) {
        backing.add(item);
    }

    int size() {
        return backing.size();
    }
}
