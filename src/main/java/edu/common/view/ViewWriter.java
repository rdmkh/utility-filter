package edu.common.view;

import java.io.PrintStream;

public class ViewWriter implements View {

    private final PrintStream out;

    public ViewWriter() {
        out = new PrintStream(System.out, true);
    }

    @Override
    public void printMessage(String message) {
        out.println(message);
    }

    @Override
    public void printfMessage(String message, Object ... objects) {
        out.printf(message, objects);
        out.println();
    }
}
