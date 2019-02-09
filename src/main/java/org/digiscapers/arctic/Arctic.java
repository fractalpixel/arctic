package org.digiscapers.arctic;

import processing.core.PApplet;

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
        size(1920/2, 1080/2, P3D);
    }

    @Override
    public void setup() {
        background(0.5f);
    }

    @Override
    public void draw() {
        clear();
        for (int i = 1; i < 10; i++) {
            float x = width * (0.5f+0.5f*sin(1.32f*i + millis() * 0.01f));
            float y = height * (0.5f+0.5f* sin(1.72f*i + 13.123f + millis() * 0.012f));
            float s = 40 + i * 10;
            fill(x % 255, y % 255, s % 255);
            ellipse(x, y, s, s);
        }

    }

}
