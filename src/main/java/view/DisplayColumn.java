package view;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayColumn implements Display {
    private List<Display> displays;

    public DisplayColumn() {
        displays = new LinkedList<Display>();
    }

    public void addDisplay(Display display) {
        displays.add(display);
    }

    public String toString() {
        return String.join("\n", toStringList());
    }

    public List<String> toStringList() {
        List<List<String>> stringList = new LinkedList<List<String>>();
        for (Display display : displays) {
            stringList.add(display.toStringList());
        }
        List<String> flatList = stringList.stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
        return flatList;
    }

    @Override
    public int height() {
        return displays.size();
    }

    @Override
    public int width() {
        return 0;
    }
}
