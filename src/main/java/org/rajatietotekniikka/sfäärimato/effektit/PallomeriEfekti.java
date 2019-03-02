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







public class PallomeriEfekti extends DemoEffect {

    ArrayList<PallomeriPallo> pallomeri = new ArrayList<PallomeriPallo>();
    int pallomaara = 17000;
    ArrayList<PallomeriPallo> pallomeri2 = new ArrayList<PallomeriPallo>();
    ArrayList<PallomeriPallo> pallomeri3 = new ArrayList<PallomeriPallo>();
    ArrayList<Pallomyrsky> pallopilvi = new ArrayList<Pallomyrsky>();
    public float scaling = 1;

    Pallokaarme kaarme;

    public void setup(@NotNull Sfäärimato p) {
        scaling = 1.0f*p.width/1200;
        for (int i = 0; i < pallomaara*0.01; i++) {
            pallopilvi.add(new Pallomyrsky());
        }
        for (int i = 0; i < pallomaara; i++) {
            pallomeri.add(new PallomeriPallo(p.height/2,-0.1f, p.height*1.4f));
        }
        for (int i = 0; i < pallomaara; i++) {
            pallomeri2.add(new PallomeriPallo(p.height*3/4, -0.05f, p.height*1.5f));
        }
        for (int i = 0; i < pallomaara; i++) {
            pallomeri2.add(new PallomeriPallo(p.height, -0.15f, p.height*2f));
        }
        kaarme = new Pallokaarme();
    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {
        float meriY = p.height*p.fadeInOut(relativeEffectTime, -0.7f, 0,0.6f,0.15f,0.15f, 0.1f, 0.08f);
        float myrskyY = p.height*p.fadeInOut(relativeEffectTime, -0.65f, 0,0.4f,0.12f,0.1f, 0.15f, 0.05f);



        for (Pallomyrsky pallo : pallopilvi) {

            pallo.updateAndDraw(elapsedEffectTime, myrskyY);
        }
        for (PallomeriPallo pallo : pallomeri) {

            pallo.updateAndDraw(elapsedEffectTime, meriY);
        }

        if (elapsedEffectTime > 13){
            kaarme.updateAndDraw(elapsedEffectTime-18, deltaTime);
        }

        for (PallomeriPallo pallo : pallomeri2) {

            pallo.updateAndDraw(elapsedEffectTime + 1, meriY);
        }
        if (relativeEffectTime < 0.3) {
            for (PallomeriPallo pallo : pallomeri3) {

                pallo.updateAndDraw(elapsedEffectTime + 2, meriY);
            }
        }

    }


    public class Pallomyrsky{
        float size = (100f + 50* p.randomGaussian())*scaling;
        float speed = (p.random(2f, 10f))*scaling;
        float hue = 0.62f + 0.01f * p.randomGaussian();
        float sat = 0 + 0.05f * p.randomGaussian();
        float lum = 0.25f + 0.1f * p.randomGaussian();


        float x =  p.random(-50,p.width +50 );

        float startY =p.random(-p.height/4, p.height/2);


        float wavetime = (7 + 0.00005f * p.randomGaussian())*scaling;
        float waveSize = (15 + 5 *p.randomGaussian())*scaling;
        boolean kaantunut = false;

        void updateAndDraw(float timeFromStart, float myrskyOffset) {
            if (myrskyOffset > p.height/8){
                float keskustaX = p.width/2;
                speed = Math.abs(speed)*1.2f;
                if (x> keskustaX){
                    speed = -speed;
                    kaantunut = true;
                }
            }

            x += -speed;
            if (x < -50){
                x = p.width + 50;
                speed = (p.random(2f, 10f))*scaling;
            }
            p.noStroke();
            p.fill(hue, sat, lum, 0.5f);
            p.ellipse(x, startY+myrskyOffset, size, size);



        }







    }



    public class Pallokaarme {
        float size =70*scaling;
        float speed = 220*scaling;
        float hue = 0.65f + 0.01f * p.randomGaussian();
        float sat = 0.75f + 0.05f * p.randomGaussian();
        float lum = 0.55f + 0.05f * p.randomGaussian();
        float startY = p.height-p.height/4;
        float startX = p.width/5;
        float wavetime = 5*scaling;
        float waveHeight = 200*scaling;
        float segmentStep = 0.043f*scaling;
        float currentX = startX;
        float xpos =startX;
        float snakeTurn = 0;

        Random random = new Random();

        List<Float> sizeProfile = new ArrayList<Float>();

        public Pallokaarme(){
            sizeProfile.add(size*0.35f);
            sizeProfile.add(size*0.45f);
            sizeProfile.add(size*0.55f);
            sizeProfile.add(size*0.85f);
            sizeProfile.add(size*0.95f);
            sizeProfile.add(size*0.75f);
            sizeProfile.add(size*0.70f);
            float sizemiod = 0.67f;
            for (int i = 0; i <14 ; i++) {
                sizeProfile.add(size*sizemiod);
                sizemiod += 0.033;
                if (sizemiod > 1) sizemiod = 1;
            }
            for (int i = 0; i <= 10 ; i++) {
                sizeProfile.add(size);
            }
            sizemiod = 1;
            for (int i = 0; i < 5; i++) {
                sizeProfile.add(size*sizemiod);
                sizemiod -= 0.025;
            }
            for (int i = 0; i < 5; i++) {
                sizeProfile.add(size*sizemiod);
                sizemiod -= 0.05;
            }
            for (int i = 0; i < 15; i++) {
                sizeProfile.add(size*sizemiod);
                sizemiod -= 0.025;
            }

        }



        void updateAndDraw(float timeFromStart, float deltatime) {
            boolean allOut = false;
            if (snakeTurn < 4){
                for (int i = sizeProfile.size()-1; i >= 0 ; i--) {
                    float segmentTime  = timeFromStart -i*segmentStep ;
                    float period = (float)(segmentTime*2*Math.PI/wavetime);

                    if (segmentTime >= -50){
                        float wavesinUnit = (float)Math.sin(period);
                        float wavesinUnit2 = (float)Math.sin(period/2 +Math.PI);
                        float wavesin = waveHeight*wavesinUnit;
                        float y = startY -wavesin;
                        if (snakeTurn > 1){
                          wavesinUnit2 = (float)Math.sin(period*2 +Math.PI);
                        }
                        if (snakeTurn > 2){
                            wavesin = waveHeight*(wavesinUnit+wavesinUnit2);
                            y = startY -wavesin;
                        }

                        p.stroke(p.color(0.4f, 0.8f,0.3f, 0.5f));
                        random.setSeed(i);
                        p.fill((float)(hue+0.001f*i+random.nextFloat()*0.005), sat, (float)(lum+random.nextFloat()*0.1f));
                        float sizemap = p.mapAndClamp(wavesinUnit2, -1,1, 0.6f,1);
                        //float currentSegmentSize = sizeProfile.get(i);
                        float currentSegmentSize = sizeProfile.get(i)*sizemap;
                        float x = xpos -i*segmentStep*speed*sizemap;

                        p.ellipse(x, y, currentSegmentSize, currentSegmentSize);
                        allOut = false;
                        if (speed > 0 && x > p.width+300) allOut = true;

                        if (speed < 0 && x < -400) allOut = true;

                        currentX = x;
                    }

                }

            }



            xpos += speed*deltatime;
            if (allOut){
                speed = -speed*1.1f;
                wavetime = wavetime*3/4;
                segmentStep -= 0.0025f;
                snakeTurn++;

            }


        }



    }

    public class PallomeriPallo {
        float size = (13f +7 * p.randomGaussian())*scaling;
        float speed = scaling*(p.random(2.5f, 3f));
        float hue = 0.65f + 0.01f * p.randomGaussian();
        float sat = 0.6f + 0.05f * p.randomGaussian();
        float lum = 0.45f + 0.05f * p.randomGaussian();


        float x =  p.random(-50,p.width +50 );

        float startY;


        float wavetime = scaling*(7 + 0.00005f * p.randomGaussian());
        float waveSize = scaling*(15 + 5 *p.randomGaussian());
        float wavephase = scaling*((float)(x/p.width*Math.PI*2 + 0.001* p.randomGaussian()));

        float kuohuvuus = 0;
        float maxWave = 0;



        public PallomeriPallo(float height, float lumCorr, float pallomeriAla){
            startY = p.random(height, pallomeriAla)+50;
            lum += lumCorr;
        }






        void updateAndDraw(float timeFromStart, float meriYOffset) {
            float per = (float)(wavephase+timeFromStart*2*Math.PI/wavetime);
            float perBefore = (float)(wavephase+10+timeFromStart*2*Math.PI/wavetime);
            //float wavesin = (float)Math.sin(wavephase+timeFromStart*2*Math.PI/wavetime);

            double k = 2*Math.PI/waveSize;
            float wavestokes = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(per) + 0.5*k*waveSize*Math.cos(2*per));
            float wavestokesBefore = (float)((1-1/16*Math.pow((k*waveSize),2))*Math.cos(perBefore) + 0.5*k*waveSize*Math.cos(2*perBefore));
            float waveY = waveSize*(float)Math.pow(wavestokes,1);
            float beforewaveY = waveSize*(float)Math.pow(wavestokesBefore,1);

            float y = startY+waveY+meriYOffset;

            maxWave = Math.max(waveY, maxWave);
            x -= (speed);
            if (x < -50) {
                x = p.width +50;
                //wavephase = 10*(float)(x/p.width*Math.PI*2 + 0.001* p.randomGaussian()) + timeFromStart;
                //wavetime =(float)(5*wavetime);
            }



            float ballSize = (float) (size *Math.pow(p.mapAndClamp(waveY,-maxWave,0, 0.5f, 1f),1.05));

            float kuohumäärä = p.mapAndClamp(-wavestokesBefore, 0.7f, 1f, 0, 1);

            kuohumäärä = p.mapAndClamp(startY, p.height*0.5f, p.height*0.9f, kuohumäärä, 0*kuohumäärä);

            if (kuohumäärä < 0) kuohumäärä = 0;
            if (kuohumäärä >1) kuohumäärä = 1;

            kuohuvuus = p.lerp(kuohumäärä, kuohuvuus, 0.9f);

            int vesi = p.color(hue, sat, lum,0.9f);
            int kuohu = p.color(hue, 0, 1, 0.6f);
            int color = p.lerpColor(vesi, kuohu, kuohuvuus);

            p.noStroke();
            float kuohunAlku = p.mapAndClamp(meriYOffset, -p.height/3.0f, 0, 0, 1);
            color = p.lerpColor(vesi,color,kuohunAlku);
            p.fill(color);
            p.ellipse(x, y, ballSize, ballSize);

        }

    }
}
