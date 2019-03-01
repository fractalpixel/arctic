package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.digiscapers.arctic.SinPulsar;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

/**
 *
 */
public class Häröpallot extends DemoEffect {
    SinPulsar bump = new SinPulsar(5f, 0.05f, 1f, 0.5f);
    SinPulsar twister = new SinPulsar(6.7f, 0.4f, 0.01f, 1.6f);
    SinPulsar twistee = new SinPulsar(15.2f, 0.1f, 0.01f, 0.6f);
    SinPulsar xWobble = new SinPulsar(14.3f, 0.03f, 1f, 0.7f, 0.82f);
    SinPulsar yWobble = new SinPulsar(15.7f, 0.03f, 1f, 0.6f, 0.23f);
    SinPulsar colorWobble = new SinPulsar(10.7f, 0.2f, 1f, 1f);


    public void setup(@NotNull Sfäärimato p) {

    }

    public void updateAndDraw(float relativeTime, float deltaTime, float elapsedEffectTime) {
        bump.setWaveTime(p.lerp(5, 1, relativeTime));
        bump.setSharpness(p.lerp(0.3f, 5f, relativeTime));
        //xWobble.setAmplitude(bump.get());
        //yWobble.setAmplitude(bump.get());

        float scale = p.lerp(15, 19, relativeTime);
        float r = 50f + scale * scale * bump.get();

        float x = p.width * 0.5f * xWobble.get();
        float y = p.height * 0.5f * yWobble.get();

        float hue = 0.7f * colorWobble.get();
        float sat = 0.5f * bump.get();
        float brightness = 0.5f * 2 * bump.get();
        float alpha = p.lerp(0.15f, 0.35f, relativeTime);
        p.fill(hue, sat, brightness, alpha);
        p.noStroke();
        int color = p.color(0.2f, 0.2f, 0.2f);

        //ellipse(x, y, r, r);

        twistee.setAmplitude(twister.get());
        float levels = p.lerp(1.5f, 4.5f, relativeTime);
        float palloRadius = r * p.fadeInOut(relativeTime, 0.1f, 0.5f, 0f, 0.9f, 0.1f, 0f);
        float twistScale = p.lerp(0.3f, 1.6f, relativeTime);
        float palloScale = 0.8f + 0.3f*twister.get();
        häröpallo(x, y, palloRadius, palloScale, twistee.get(), levels, twistScale, hue, sat, brightness, alpha);
    }

    void häröpallo(float x, float y, float radius, float scale, float twist, float levels, float twistScale, float h, float s, float b, float a) {

        h += 0.07f; // Wander
        b *= 0.9f; // Darken
        s *= 1.1f;  // Intensify

        int maxNum = 5;
        for (int i = 0; i < maxNum; i++) {
            float turn = p.map(i, 0, maxNum, 0f, 1f);
            float rx = x + radius * p.cos(p.PI*2*(twist + turn));
            float ry = y + radius * -p.sin(p.PI*2*(twist + turn));
            float size = radius * scale;
            if (levels < 1) size *= levels;

            p.fill(h, s, b, a);
            p.ellipse(rx, ry, size, size);

            // WE HAVE TO GO DEEPER!!
            if (levels > 1) {
                häröpallo(rx, ry, size, scale, twist * twistScale, levels - 1f, twistScale, h, s, b, a);
            }
        }
    }

}
