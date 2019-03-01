package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

import java.util.ArrayList;

/**
 *
 */
public class ShieraSfääri extends DemoEffect {

    ArrayList<PallomeriPallo> pallomeri = new ArrayList<PallomeriPallo>();
    int pallomaara = 100;

    public void setup(@NotNull Sfäärimato p) {
        for (int i = 0; i < pallomaara; i++) {
            pallomeri.add(new PallomeriPallo());
        }
    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {
        for (PallomeriPallo pallo : pallomeri) {

            pallo.updateAndDraw(elapsedEffectTime);
        }

    }

    public class PallomeriPallo {
        float size = 10f + 5* p.randomGaussian();
        float speed = 0.1f + 0.001f * p.randomGaussian();
        float hue = 0.7f + 0.05f * p.randomGaussian();
        float sat = 0.5f + 0.1f * p.randomGaussian();
        float lum = 0.5f + 0.1f * p.randomGaussian();


        float startX =  p.random(0,p.width );
        float startY = p.random(p.height/2, p.height);

        float wavetime = 3 + 0.5f * p.randomGaussian();
        float waveSize = 50 + 10 *p.randomGaussian();

        void updateAndDraw(float timeFromStart) {
            float waveY = waveSize*(float)Math.pow(Math.sin(timeFromStart*2*Math.PI/wavetime),2);
            float y = startY+waveY;
            p.fill(hue, sat, lum);
            p.ellipse(startX, y, size, size);
        }

    }
}
