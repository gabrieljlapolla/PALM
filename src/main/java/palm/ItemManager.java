package palm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

public class ItemManager<I extends Item> extends HashMap<String, I> implements Iterable<I> {

    /**
     * Adds item to Map
     *
     * @param item Item to add
     * @return true if item is added, false if duplicate key
     */
    public boolean add(I item) {
        if (this.containsKey(item.getName())) {
            return false;
        } else {
            this.put(item.getName(), item);
            return true;
        }
    }

    public Iterator<I> iterator() {
        return this.values().iterator();
    }

    public Stream<I> stream() {
        return this.values().stream();
    }

}
