package org.digiscapers.arctic;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point to demo.
 * Uses processing library.
 */
public class Arctic extends PApplet {

    /**
     * Called when program starts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Start this processing applet
        PApplet.main(Arctic.class);
    }

    @Override
    public void settings() {
// UNCOMMENT FOR COMPO VERSION!!
//        size(1920, 1080, P3D);
        size(1366, 768, P3D);
        fullScreen(P3D);
        randomSeed(8719);
    }

    long startTime = 0;

    List<DemoEffect> effects = new ArrayList<DemoEffect>();

    @Override
    public void setup() {
        background(0.5f);
        startTime = millis();

        noSmooth();
        noCursor();
        noStroke();


        rectMode(CENTER);
    }

    private void setupDemo() {
        effects.add(new Sky());
    }

    @Override
    public void draw() {
        clear();


        // TODO: Serious things here.  3D scenes and stuff.

        for (DemoEffect effect : effects) {
            effect.draw();
        }

    }

}
