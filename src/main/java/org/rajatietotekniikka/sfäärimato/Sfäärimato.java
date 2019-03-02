package org.rajatietotekniikka.sfäärimato;

import org.digiscapers.arctic.DemoEffects;
import org.rajatietotekniikka.sfäärimato.effektit.*;
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
 *              (Stand by for our next demo, KRISTALLISÄTEILY at Assembly 2019! Or 2029!
 *               and ASTRAALIAURA at the next one!)
 *
 */
public class Sfäärimato extends PApplet {

    long startTime = 0;

    DemoEffects effects;

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
        size(1200, 700, P2D);
        //fullScreen();
    }


    @Override
    public void setup() {
        noCursor();
        background(0.5f);
        startTime = millis();

        float lastEffectEndToDemoEndSeconds = 5f;
        effects = new DemoEffects(this, lastEffectEndToDemoEndSeconds);


        // NOTE: Colors are now given as Hue, Saturation, Brightness, (Alpha),
        // each float parameter ranging between 0f and 1f!
        colorMode(HSB, 1f);

        // DEBUG: Jump to desired time
//        setTimeSeconds(120);

        // Setup effects
///        effects.addEffect(0f, 35f, new IntroStage());
//        effects.addEffect(0f, 30f, new Häröpallot());
        //effects.addEffect(0f, 50f, new SplatterBalls());
        //effects.addEffect(49f, 50f, new PalloAvaruus());
        //effects.addEffect(30f, 50f, new ScrolleriEffekti());
        effects.addEffect(5f, 50f, new ShieraSfääri());
        effects.addEffect(5f, 20f, new IrahSfäärit());

        // TODO: Add more awesome stages!
    }

    @Override
    public void draw() {
        clear();

        // Update effects
        effects.updateAndDraw();
    }



    // Utility functions below

    /**
     * @return true if current demo time is in the specified range (excluding end value)
     */
    public boolean timeBetween(float startSeconds, float endSeconds) {
        float timeSeconds = getTimeSeconds();
        return timeSeconds >= startSeconds && timeSeconds < endSeconds;
    }

    /**
     * @return true if current demo time is after the specified seconds since start.
     */
    public boolean timeAfter(float startSeconds) {
        return getTimeSeconds() >= startSeconds;
    }

    /**
     * @return true if current demo time is before the specified seconds since start.
     */
    public boolean timeBefore(float endSeconds) {
        return getTimeSeconds() < endSeconds;
    }

    /**
     * @return position in time between start and end seconds,
     * 0 at start and 1 at end, interpolating in between and beyond.
     */
    public float relativeTime(float startSeconds, float endSeconds) {
        return map(getTimeSeconds(), startSeconds, endSeconds, 0f, 1f);
    }

    /**
     * @return interpolate over time.
     */
    public float interpolateOverTime(float startSeconds, float endSeconds, float startValue, float endValue) {
        return map(getTimeSeconds(), startSeconds, endSeconds, startValue, endValue);
    }


    /**
     * @return time in the demo since the start in seconds.
     */
    public float getTimeSeconds() {
        return (millis() - startTime) / 1000f;
    }

    /**
     * Jump to specified time
     */
    public void setTimeSeconds(float demoTime) {
        startTime = (long) (millis() - demoTime * 1000);
    }

    /**
     * @param t goes from 0 to 1 and controls the value produced
     * @param startValue value when t <= 0
     * @param midValue value after t >= rampLength and while t <= 1 - rampLength
     * @param endValue value when t >= 1
     * @param rampLength length of transition from start to mid value and from mid to end value.
     * @return smoothly interpolated value
     */
    public float fadeInOut(float t, float startValue, float midValue, float endValue, float rampLength) {
        return fadeInOut(t, startValue, midValue, endValue, rampLength, rampLength, 0f);
    }

    /**
     * @param t goes from 0 to 1 and controls the value produced
     * @param startValue value when t <= 0
     * @param midValue value after t >= startRampLength and while t <= 1 - endRampLength
     * @param endValue value when t >= 1
     * @param startRampLength length of transition from start to mid value
     * @param endRampLength length of transition from mid to end value.
     * @param startDelay t value before starting first ramp
     * @return smoothly interpolated value
     */
    public float fadeInOut(float t, float startValue, float midValue, float endValue, float startRampLength, float endRampLength, float startDelay) {
        if (t <= startDelay) {
            return startValue;
        }
        else if (t <= startDelay + startRampLength) {
            return smoothInterpolate(startValue, midValue, relativePos(t, startDelay, startDelay + startRampLength));
        }
        else if (t > startDelay + startRampLength && t < (1f - endRampLength)) {
            return midValue;
        }
        else {
            return smoothInterpolate(midValue, endValue, relativePos(t, 1f - endRampLength, 1f));
        }
    }

    /**
     * @param t goes from 0 to 1 and controls the value produced
     * @param startValue value when t <= 0
     * @param midValue value after t >= startRampLength and while t <= 1 - endRampLength
     * @param endValue value when t >= 1
     * @param startRampLength length of transition from start to mid value
     * @param endRampLength length of transition from mid to end value.
     * @param startDelay t value before starting first ramp
     * @param endDelay delays the end
     * @return smoothly interpolated value
     */
    public float fadeInOut(float t, float startValue, float midValue, float endValue, float startRampLength, float endRampLength, float startDelay, float endDelay) {
        if (t <= startDelay) {
            return startValue;
        }
        else if (t> 1f-endDelay){
            return endValue;
        }
        else if (t <= startDelay + startRampLength) {
            return smoothInterpolate(startValue, midValue, relativePos(t, startDelay, startDelay + startRampLength));
        }
        else if (t > startDelay + startRampLength && t < (1f - endRampLength-endDelay)) {
            return midValue;
        }
        else {
            return smoothInterpolate(midValue, endValue, relativePos(t, 1f - endRampLength- endDelay, 1f-endDelay));
        }
    }


    public float relativePos(float value, float start, float end) {
        if (end == start) return 0.5f;

        float t = value - start;
        return t / (end - start);
    }

    public float clampToZeroToOne(float value) {
        return clamp(value, 0f, 1f);
    }

    public float clamp(float value, float minValue, float maxValue) {
        if (value < minValue) return minValue;
        if (value > maxValue) return maxValue;
        return value;
    }

    public float smoothInterpolate(float a, float b, float t) {
        // Clamp
        if (t < 0f) return a;
        if (t > 1f) return b;

        //float smoothStepT = t * t * (3f - 2f * t);
        float t2 = (1f - cos(t*PI)) / 2f;
        return lerp(a, b, t2);
    }

    /**
     * Like map, but clamps the outputs
     * @param t
     * @param inputStat
     * @param inputEnd
     * @param outputStart
     * @param outputEnd
     * @return
     */
    public float mapAndClamp(float t, float inputStat, float inputEnd, float outputStart, float outputEnd) {
        float relativePos = clampToZeroToOne(relativePos(t, inputStat, inputEnd));
        return lerp(outputStart, outputEnd, relativePos);
    }

}


