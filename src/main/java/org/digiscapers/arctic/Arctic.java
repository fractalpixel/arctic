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

    long startTime = 0;

    @Override
    public void setup() {
        background(0.5f);
        startTime = millis();
    }

    @Override
    public void draw() {
        clear();
        long t = millis() - startTime;
        float speed = 0.2f + 1f * (0.5f+0.5f*sin(t * 0.0001f));
        float separation = 2f;
        int maxBalls = (int) (t / 1000);
        if (t > 20000) maxBalls += (t-20000) / 250;
        float umts = 1f;
        if (t > 18000) umts =(cos(t * 0.01f) * 0.2f + 1f);

        for (int i = 1; i < maxBalls; i++) {
            float x = umts*width * (0.5f+0.5f*sin(separation*0.192f*i + t * 0.004f* speed));
            float y = umts*height * (0.5f+0.5f* sin(separation*0.17f*i + 13.123f + t * 0.0042f*speed));
            float s = (40 + i * (10 - umts)) ;
            if (t > 30000) s *= 1f - ((t - 30000f)/5000f);
            fill((1f-umts*0.1f * i*1.3f * x) % 60 + i * 5,
                    (i*umts*0.1f * y) % 60 + i * 5,
                    (umts*0.1f + s) % 255);
            ellipse(x, y, s, s);
        }

        textSize(50);
        fill(100, 100, 255);
        if (t > 12000 && t < 16000) text("Pre-Compo Production", width/4, height * 0.3f);
        fill((int)((int)(100*sin(t*0.1f))), 120, 255);
        textSize(100 * umts);
        float disturb = 0f;
        if (t > 24000) disturb = (t - 24000f) / 10000f;
        float tx = disturb * sin(t * 0.017f) * width;
        float ty = disturb * cos(t * 0.013f) * height;
        if (t > 15000 && t < 30000) text("* SFÄÄRIMATO *", width/8 + tx, height * 0.5f + ty);
        fill(50, 100, 220);
        textSize(50);
        if (t > 17000 && t < 22000) text("Aiming for LAST PLACE!", width/4, height * 0.7f);
        if (t > 35000) exit();
    }

}
