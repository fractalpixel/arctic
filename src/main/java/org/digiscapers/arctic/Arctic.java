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
        ellipse(200f,200f,50f,50f);

    }

}
