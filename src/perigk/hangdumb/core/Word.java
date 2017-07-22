package perigk.hangdumb.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Word {
    private String finalText;
    private String initialText;


    public Word(String text) {
        this.finalText = text;
    }

    public String getText() {
        return finalText;
    }

    public void setFinalText(String finalText) {
        this.finalText = finalText;
    }

    @Override
    public String toString() {
        return "Word{" +
                "finalText='" + finalText + '\'' +
                '}';
    }
}
