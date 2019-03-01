package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */







public class ShieraSfääri extends DemoEffect {

    ArrayList<PallomeriPallo> pallomeri = new ArrayList<PallomeriPallo>();
    int pallomaara = 10000;
    ArrayList<PallomeriPallo> pallomeri2 = new ArrayList<PallomeriPallo>();
    ArrayList<Pallomyrsky> pallopilvi = new ArrayList<Pallomyrsky>();

    Pallokaarme kaarme;

    public void setup(@NotNull Sfäärimato p) {
        for (int i = 0; i < pallomaara*0.01; i++) {
            pallopilvi.add(new Pallomyrsky());
        }
        for (int i = 0; i < pallomaara; i++) {
            pallomeri.add(new PallomeriPallo(p.height/2,-0.1f));
        }
        for (int i = 0; i < pallomaara; i++) {
            pallomeri2.add(new PallomeriPallo(p.height*3/4, -0.05f));
        }
        kaarme = new Pallokaarme();
    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {
        for (Pallomyrsky pallo : pallopilvi) {

            pallo.updateAndDraw(elapsedEffectTime);
        }
        for (PallomeriPallo pallo : pallomeri) {

            pallo.updateAndDraw(elapsedEffectTime);
        }
        if (elapsedEffectTime > 10){
            kaarme.updateAndDraw(elapsedEffectTime-15, deltaTime);
        }

        for (PallomeriPallo pallo : pallomeri2) {

            pallo.updateAndDraw(elapsedEffectTime + 1);
        }

    }


    public class Pallomyrsky{
        float size = 100f + 50* p.randomGaussian();
        float speed = p.random(2.5f, 4f);
        float hue = 0.62f + 0.01f * p.randomGaussian();
        float sat = 0 + 0.05f * p.randomGaussian();
        float lum = 0.3f + 0.1f * p.randomGaussian();


        float x =  p.random(-50,p.width +50 );

        float startY =p.random(-50, p.height/2);


        float wavetime = 7 + 0.00005f * p.randomGaussian();
        float waveSize = 15 + 5 *p.randomGaussian();

        void updateAndDraw(float timeFromStart) {
            x += -speed;
            if (x < -50){
                x = p.width + 50;
            }
            p.noStroke();
            p.fill(hue, sat, lum, 0.5f);
            p.ellipse(x, startY, size, size);

        }







    }



    public class Pallokaarme {
        float size = 50;
        float speed = 200;
        float hue = 0.35f + 0.01f * p.randomGaussian();
        float sat = 0.55f + 0.05f * p.randomGaussian();
        float lum = 0.5f + 0.05f * p.randomGaussian();
        float startY = p.height-p.height/4;
        float startX = p.width/5;
        float wavetime = 5;
        float waveHeight = 200;
        float segmentStep = 0.07f;
        float currentX = startX;
        float xpos =startX;

        Random random = new Random();

        List<Float> sizeProfile = new ArrayList<Float>();

        public Pallokaarme(){
            sizeProfile.add(size*0.4f);
            sizeProfile.add(size*0.6f);
            sizeProfile.add(size*0.9f);
            sizeProfile.add(size*0.7f);
            sizeProfile.add(size*0.6f);
            sizeProfile.add(size*0.7f);
            sizeProfile.add(size*0.8f);
            sizeProfile.add(size*0.9f);
            for (int i = 0; i <= 5 ; i++) {
                sizeProfile.add(size);
            }
            sizeProfile.add(size*0.95f);
            sizeProfile.add(size*0.9f);
            sizeProfile.add(size*0.8f);
            sizeProfile.add(size*0.75f);
            sizeProfile.add(size*0.7f);
            sizeProfile.add(size*0.65f);
            sizeProfile.add(size*0.6f);
            sizeProfile.add(size*0.5f);
            sizeProfile.add(size*0.4f);
            sizeProfile.add(size*0.3f);
            sizeProfile.add(size*0.3f);
            sizeProfile.add(size*0.2f);
        }



        void updateAndDraw(float timeFromStart, float deltatime) {
            boolean allOut = false;
            for (int i = sizeProfile.size()-1; i >= 0 ; i--) {
                float segmentTime  = timeFromStart -i*segmentStep ;
                float period = (float)(segmentTime*2*Math.PI/wavetime);

                if (segmentTime >= -50){

                    float wavesin = waveHeight*(float)Math.sin(period);
                    float y = startY -wavesin;
                    float x = xpos -i*segmentStep*speed;
                    p.stroke(0);
                    random.setSeed(i);
                    p.fill((float)(hue+0.01f*i+random.nextFloat()*0.005), sat, (float)(lum+random.nextFloat()*0.1f));
                    p.ellipse(x, y, sizeProfile.get(i), sizeProfile.get(i));
                    allOut = false;
                    if (speed > 0 && x > p.width+300) allOut = true;

                    if (speed < 0 && x < -400) allOut = true;

                    currentX = x;
                }

            }
            xpos += speed*deltatime;
            if (allOut){
                speed = -speed*1.1f;
                wavetime = wavetime*3/4;

                //size += 0.2f;

            }


        }



    }

    public class PallomeriPallo {
        float size = 10f + 5* p.randomGaussian();
        float speed = p.random(2.5f, 3f);
        float hue = 0.62f + 0.01f * p.randomGaussian();
        float sat = 0.55f + 0.05f * p.randomGaussian();
        float lum = 0.5f + 0.05f * p.randomGaussian();


        float x =  p.random(-50,p.width +50 );

        float startY;


        float wavetime = 7 + 0.00005f * p.randomGaussian();
        float waveSize = 15 + 5 *p.randomGaussian();
        float wavephase = (float)(x/p.width*Math.PI*2 + 0.001* p.randomGaussian());

        float kuohuvuus = 0;
        float maxWave = 0;

        public PallomeriPallo(float height, float lumCorr){
            startY = p.random(height, p.height)+50;
            lum += lumCorr;
        }






        void updateAndDraw(float timeFromStart) {
            float per = (float)(wavephase+timeFromStart*2*Math.PI/wavetime);
            float perBefore = (float)(wavephase+10+timeFromStart*2*Math.PI/wavetime);
            //float wavesin = (float)Math.sin(wavephase+timeFromStart*2*Math.PI/wavetime);

            double k = 2*Math.PI/waveSize;
            float wavestokes = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(per) + 0.5*k*waveSize*Math.cos(2*per));
            float wavestokesBefore = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(perBefore) + 0.5*k*waveSize*Math.cos(2*perBefore));
            float waveY = waveSize*(float)Math.pow(wavestokes,1);

            float y = startY+waveY;

            maxWave = Math.max(waveY, maxWave);
            x -= (speed);
            if (x < -50) {
                x = p.width +50;
                //wavephase = 10*(float)(x/p.width*Math.PI*2 + 0.001* p.randomGaussian()) + timeFromStart;
                //wavetime =(float)(5*wavetime);
            }



            float ballSize = (float) (size *Math.pow(p.mapAndClamp(waveY,-maxWave,0, 0.5f, 1f),1));

            float kuohumäärä = p.mapAndClamp(-wavestokesBefore, 0.7f, 1f, 0, 1);

            kuohumäärä = p.mapAndClamp(startY, p.height*0.5f, p.height*0.9f, kuohumäärä, 0*kuohumäärä);

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
