package org.digiscapers.arctic;

import processing.core.PApplet;

/**
 * BONUS STAGE!  Unlock with MAD SKILLS!  This is an EASTER SPHERE!
 *
 *               Rajatietotekniikka presents:
 *
 *              * * * S F Ä Ä R I M A T O * * *
 *
 *            Coming to a bonus stage near you soon!
 *
 *              (Stand by for our next demo, KRISTALLISÄTEILY at Assembly 2019! Or 2029!)
 *
 */
public class Sfäärimato extends PApplet {

    long startTime = 0;

    /**
     * Called when program starts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Start this processing applet
        PApplet.main(Sfäärimato.class);
    }

    @Override
    public void settings() {
        // Running this with P2D
// UNCOMMENT FOR COMPO VERSION!!
//        size(1920, 1080, P2D);
        size(1200, 800, P2D);
    }

    @Override
    public void setup() {
        background(0.5f);
        startTime = millis();

        // NOTE: Colors are now given as Hue, Saturation, Brightness, (Alpha),
        // each float parameter ranging between 0f and 100f!
        colorMode(HSB, 100f);

        // DEBUG: Jump to desired time
       // setTimeSeconds(30);

    }

    @Override
    public void draw() {
        clear();

        float introStart = 0;
        float introEnd = 35;
        float palloStart = introEnd - 6;
        float palloEnd = palloStart + 25;
        float demoEnd = palloEnd + 1;

        // Häröpallo stage!  (Draw under intro as it fades in from underneath)
        if (timeBetween(palloStart, palloEnd)) {
            häröpalloStage(getTimeSeconds() - palloStart, relativeTime(palloStart, palloEnd));
        }

        // Intro
        if (timeBetween(introStart, introEnd)) {
            introStage(getTimeSeconds() - introStart, relativeTime(introStart, introEnd));
        }

        // TODO: Add more awesome SFÄÄRIMATO stages!

        // The end!
        if (getTimeSeconds() > demoEnd) exit();
    }

    private void introStage(float time, float relativeTime) {
        // This one uses RGB colors still
        colorMode(RGB, 255f);
        stroke(0,0,0);

        float speed = 0.2f + 1f * (0.5f+0.5f*sin(time * 0.1f));
        float separation = 2f;
        int maxBalls = (int) (time);
        if (time > 20) maxBalls += (time-20) / 0.350f;
        float umts = 1f;
        if (time > 18) umts =(cos(time * 7f) * 0.2f + 1f);

        float trackScale = lerp(0.3f, 1.3f, relativeTime);
        for (int i = 1; i < maxBalls; i++) {
            float x = umts*width * (0.5f+trackScale*0.5f*sin(separation*0.192f*i + time * 4f* speed));
            float y = umts*height * (0.5f+trackScale*0.5f* sin(separation*0.17f*i + 13.123f + time * 4.2f*speed));
            float s = (40 + i * (10 - umts)) ;
            if (time > 30) s *= 1f - ((time - 30f)/5f);
            fill((1f-umts*0.1f * i*1.3f * x) % 60 + i * 5,
                    (i*umts*0.1f * y) % 60 + i * 5,
                    (umts*0.1f + s) % 255);
            ellipse(x, y, s, s);
        }

        textSize(50);
        fill(100, 100, 255);
        if (time > 12 && time < 16) text("Rajatietotekniikka presents", width*0.15f, height * 0.3f);
        fill(((int)(100*sin(time*10f))), 120, 255);
        textSize(100 * umts);
        float disturb = 0f;
        if (time > 24) disturb = (time - 24f) / 15f;
        float tx = disturb * sin(time * 17f);
        float ty = disturb * cos(time * 13f);
        if (time > 15 && time < 30) text("⛯* SFÄÄRIMATO *⛯", width * (0.08f+ tx) - umts*50f+50, height * (0.5f + ty)- umts*50f+50);
        fill(30, 50, 200);
        textSize(50);
        if (time > 17 && time < 20) text("And the BONUS STAGE!", width*0.2f, height * 0.7f);
    }


    SinPulsar bump = new SinPulsar(5f, 0.3f, 1f, 3f);
    SinPulsar twister = new SinPulsar(6.7f, 0.4f, 0.01f, 1.6f);
    SinPulsar twistee = new SinPulsar(15.2f, 0.1f, 0.01f, 0.6f);
    SinPulsar xWobble = new SinPulsar(14.3f, 0.03f, 1f, 0.7f, 0.82f);
    SinPulsar yWobble = new SinPulsar(15.7f, 0.03f, 1f, 0.6f, 0.23f);
    SinPulsar colorWobble = new SinPulsar(10.7f, 0.2f, 1f, 1f);
    void häröpalloStage(float seconds, float relativeTime) {
        // Colors are hue, sat, brightness, alpha, range 0..100f.
        colorMode(HSB, 100f);

        bump.setWaveTime(lerp(5, 1, relativeTime));
        bump.setSharpness(lerp(0.3f, 5f, relativeTime));
        //xWobble.setAmplitude(bump.get());
        //yWobble.setAmplitude(bump.get());

        float scale = lerp(15, 19, relativeTime);
        float r = 50f + scale * scale * bump.get();

        float x = width * 0.5f * xWobble.get();
        float y = height * 0.5f * yWobble.get();

        float hue = 70 * colorWobble.get();
        float sat = 50 * bump.get();
        float brightness = 50 * 2 * bump.get();
        float alpha = lerp(15, 35, relativeTime);
        fill(hue, sat, brightness, alpha);
        noStroke();
        //ellipse(x, y, r, r);

        twistee.setAmplitude(twister.get());
        float levels = lerp(1.5f, 3.5f, relativeTime);
        float palloRadius = r * lerp(0.1f, 0.7f, relativeTime);
        if (relativeTime > 0.95) palloRadius *= map(relativeTime, 0.95f, 1, 1, 7f);
        float twistScale = lerp(0.3f, 1.6f, relativeTime);
        float palloScale = 0.8f + 0.3f*twister.get();
        häröpallo(x, y, palloRadius, palloScale, twistee.get(), levels, twistScale, hue, sat, brightness, alpha);
    }

    void häröpallo(float x, float y, float radius, float scale, float twist, float levels, float twistScale, float h, float s, float b, float a) {

        h += 7f; // Wander
        b *= 0.9f; // Darken
        s *= 1.1f;  // Intensify

        int maxNum = 5;
        for (int i = 0; i < maxNum; i++) {
            float turn = map(i, 0, maxNum, 0f, 1f);
            float rx = x + radius * cos(PI*2*(twist + turn));
            float ry = y + radius * -sin(PI*2*(twist + turn));
            float size = radius * scale;
            if (levels < 1) size *= levels;

            fill(h, s, b, a);
            ellipse(rx, ry, size, size);

            // WE HAVE TO GO DEEPER!!
            if (levels > 1) {
                häröpallo(rx, ry, size, scale, twist * twistScale, levels - 1f, twistScale, h, s, b, a);
            }
        }
    }


    // Utility functions below

    /**
     * @return true if current demo time is in the specified range (excluding end value)
     */
    boolean timeBetween(float startSeconds, float endSeconds) {
        float timeSeconds = getTimeSeconds();
        return timeSeconds >= startSeconds && timeSeconds < endSeconds;
    }

    /**
     * @return true if current demo time is after the specified seconds since start.
     */
    boolean timeAfter(float startSeconds) {
        return getTimeSeconds() >= startSeconds;
    }

    /**
     * @return true if current demo time is before the specified seconds since start.
     */
    boolean timeBefore(float endSeconds) {
        return getTimeSeconds() < endSeconds;
    }

    /**
     * @return position in time between start and end seconds,
     * 0 at start and 1 at end, interpolating in between and beyond.
     */
    float relativeTime(float startSeconds, float endSeconds) {
        return map(getTimeSeconds(), startSeconds, endSeconds, 0f, 1f);
    }

    /**
     * @return interpolate over time.
     */
    float interpolateOverTime(float startSeconds, float endSeconds, float startValue, float endValue) {
        return map(getTimeSeconds(), startSeconds, endSeconds, startValue, endValue);
    }


    /**
     * @return time in the demo since the start in seconds.
     */
    float getTimeSeconds() {
        return (millis() - startTime) / 1000f;
    }

    /**
     * Jump to specified time
     */
    void setTimeSeconds(float demoTime) {
        startTime = (long) (millis() - demoTime * 1000);
    }

}


