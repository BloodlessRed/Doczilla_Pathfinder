package solution.model;

import java.util.*;

//Не используем record, потому что содержание экземпляров Tube постоянно меняется
public class Tube {
    private Deque<String> liquids;
    private final int capacity;
    private int currentVolume;

    public Tube(int capacity, Collection<String> liquids) {
        this.capacity = capacity;
        this.liquids = new ArrayDeque<>(liquids);
        this.currentVolume = liquids.size();
    }

    public Deque<String> getLiquids() {
        return liquids;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getColors() {
        return liquids.stream().toList();
    }

    public String getTopColor() {
        return liquids.peekLast();
    }

    public int getTopColorBlockSize() {
        if (liquids.isEmpty()) {
            return 0;
        }
        String topColor = getTopColor();
        int count = 0;
        Iterator<String> it = liquids.descendingIterator();
        while (it.hasNext()){
            if (it.next().equals(topColor)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public int getFreeSpace() {
        return capacity - currentVolume;
    }

    public boolean isFull() {
        return liquids.size() == capacity;
    }

    public boolean isEmpty() {
        return liquids.isEmpty();
    }

    public boolean isHomogeneous() {
        String initLiq = liquids.peekFirst();
        if (initLiq == null)
            return false;
        return currentVolume == capacity && liquids.stream().allMatch(initLiq::equals);
    }

    public void addLiquid(String color) {
        liquids.addLast(color);
        currentVolume = liquids.size();
    }

    public String removeLiquid() {
        String removedColor = liquids.removeLast();
        currentVolume = liquids.size();
        return removedColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return capacity == tube.capacity && Objects.equals(liquids, tube.liquids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(liquids, capacity);
    }
}
