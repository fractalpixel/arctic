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
    int pallomaara = 10000;

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
        float size = 8f + 8* p.randomGaussian();
        float speed = p.random(2.5f, 3f);
        float hue = 0.62f + 0.01f * p.randomGaussian();
        float sat = 0.55f + 0.05f * p.randomGaussian();
        float lum = 0.5f + 0.05f * p.randomGaussian();


        float x =  p.random(-50,p.width +50 );
        float startY = p.random(p.height/2, p.height)+50;

        float wavetime = 7 + 0.1f * p.randomGaussian();
        float waveSize = 15 + 5 *p.randomGaussian();
        float wavephase = (float)(x/p.width*Math.PI*2 + 0.001* p.randomGaussian());

        float kuohuvuus = 0;


        void updateAndDraw(float timeFromStart) {
            float per = (float)(wavephase+timeFromStart*2*Math.PI/wavetime);
            float perBefore = (float)(wavephase+10+timeFromStart*2*Math.PI/wavetime);
            //float wavesin = (float)Math.sin(wavephase+timeFromStart*2*Math.PI/wavetime);

            double k = 2*Math.PI/waveSize;
            float wavestokes = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(per) + 0.5*k*waveSize*Math.cos(2*per));
            float wavestokesBefore = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(perBefore) + 0.5*k*waveSize*Math.cos(2*perBefore));
            float waveY = waveSize*(float)Math.pow(wavestokes,1);
            float y = startY+waveY;

            x -= (speed);
            if (x < -50) x = p.width +50;

            float ballSize = (float) (size *waveY);

            float kuohumäärä = p.map(-wavestokesBefore, 0.7f, 1f, 0, 1);


            if (kuohumäärä < 0) kuohumäärä = 0;
            if (kuohumäärä >1) kuohumäärä = 1;

            kuohuvuus = p.lerp(kuohumäärä, kuohuvuus, 0.9f);

            int vesi = p.color(hue, sat, lum,0.9f);
            int kuohu = p.color(hue, 0, 1, 0.6f);
            int color = p.lerpColor(vesi, kuohu, kuohuvuus);

            p.noStroke();
            p.fill(color);
            p.ellipse(x, y, ballSize, ballSize);

        }

    }
}
