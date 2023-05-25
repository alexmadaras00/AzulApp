package view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DisplayRow implements Display {
    private List<Display> displays;
    private int height;

    public DisplayRow(int height) {
        this.height = height;
        displays = new LinkedList<Display>();
    }

    public void addDisplay(Display display) {
        if (height == display.height()) {
            displays.add(display);
        } 
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }

    @Override
    public List<String> toStringList() {
        List<List<String>> stringList = new LinkedList<List<String>>();
        for (Display display : displays) {
            stringList.add(display.toStringList());
        }
        List<String> flatList = new ArrayList<String>();
        for (int i = 0; i < height(); i++) {
            flatList.add("");
        }
        for (List<String> displayStringList : stringList) {
            for (int i = 0; i < height(); i++) {
                flatList.set(i, flatList.get(i) + displayStringList.get(i));
            }
        }
        return flatList;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return displays.size();
    }

}
