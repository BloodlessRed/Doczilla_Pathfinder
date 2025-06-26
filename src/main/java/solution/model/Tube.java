package solution.model;

import java.util.*;

//Не используем record, потому что содержание экземпляров Tube постоянно меняется
public class Tube {
    private Deque<String> liquids;
    private final int capacity;
    private int currentVolume;

    public Tube(int capacity, List<String> liquids) {
        this.capacity = capacity;
        this.liquids = new ArrayDeque<>(liquids);
        this.currentVolume = liquids.size();
    }

    public Tube (Tube other){
        this.capacity = other.capacity;
        this.liquids = new ArrayDeque<>(other.liquids);
        this.currentVolume = other.currentVolume;
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

        Tube otherTube = (Tube) o;
        if (capacity != otherTube.capacity) return false;
        if (this.liquids.size() != otherTube.liquids.size()) return false;
        // this.liquids.equals(other.liquids) выводит false, поэтому проверяем вручную.
        Iterator<String> thisIterator = this.liquids.iterator();
        Iterator<String> otherIterator = otherTube.liquids.iterator();

        while (thisIterator.hasNext()) {
            String thisElement = thisIterator.next();
            String otherElement = otherIterator.next();

            if (!Objects.equals(thisElement, otherElement)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + capacity;

        for (String element : this.liquids){
            result = 31 * result + Objects.hashCode(element);
        }
        return result;
    }
}
