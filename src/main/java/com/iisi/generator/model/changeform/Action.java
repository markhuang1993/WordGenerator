package com.iisi.generator.model.changeform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Action {
    private List<String> lines;

    public Action(String... lines) {
        this.lines = new ArrayList<>(Arrays.asList(lines));
    }

    public void addLine(String line) {
        this.lines.add(line);
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }
}
