package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

import java.util.ArrayList;

/**
 *
 */
public class PalloAvaruus extends DemoEffect {

    ArrayList<AvaruusPallo> avaruusPallot = new ArrayList<AvaruusPallo>();

    public void setup(@NotNull Sfäärimato p) {

    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {

        int ballNum = (int) p.fadeInOut(relativeEffectTime, 0, 200, 200, 0.5f, 0.1f, 0f);

        // Adjust number of balls
        while (avaruusPallot.size() < ballNum) {
            avaruusPallot.add(new AvaruusPallo());
        }
        while (avaruusPallot.size() > ballNum && ballNum > 0) {
            avaruusPallot.remove(0);
        }

        float pesukoneEffekti = p.fadeInOut(relativeEffectTime, 0, 0.4f, -0.8f, 0.35f, 0.25f, 0.4f);
        float ballSpeed = p.fadeInOut(relativeEffectTime, 0.05f, 1f, -5f, 0.7f, 0.18f, 0);
        for (AvaruusPallo avaruusPallo : avaruusPallot) {
            avaruusPallo.speed = ballSpeed;
            avaruusPallo.a += pesukoneEffekti * deltaTime;
            avaruusPallo.updateAndDraw();
        }
    }

    class AvaruusPallo {
        float r = 0f;
        float a = p.random(2f * p.PI);
        float z = 0f;
        float speed = 0.1f + 0.001f * p.randomGaussian();
        float hue = 0.7f + 0.05f * p.randomGaussian();
        float sat = 0.5f + 0.1f * p.randomGaussian();
        float lum = 0.5f + 0.1f * p.randomGaussian();

        float maxZ = 8f + 1f * p.randomGaussian();

        void updateAndDraw() {
            z += getDeltaTime() * speed;
            r += getDeltaTime() * speed * z * 100f; // Crappy projection to radial distance

            // Warp to start when going round
            if (z > maxZ) {
                z = 0f;
                r = 0f;
            }
            if (z < -1) z = -1;

            float x = p.width/2f + r * p.cos(a);
            float y = p.height/2f + r * -p.sin(a);
            float size = p.height * 0.005f * z * z;

            float alpha = p.fadeInOut(z / maxZ, 0f, 1f, 0f, 0.4f, 0.4f, 0);
            p.fill(hue, sat, lum, alpha);
            p.ellipse(x, y, size, size);
        }
    }



}
