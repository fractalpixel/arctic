package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

/**
 * Matooooo!
 */
public class IntroStage extends DemoEffect {

    public void setup(@NotNull Sfäärimato p) {

    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {
        // This one uses RGB colors still
        p.colorMode(p.RGB, 255f);
        p.stroke(0, 0, 0);

        float speed = 0.2f + 1f * (0.5f + 0.5f * p.sin(elapsedEffectTime  * 0.1f));
        float separation = 2f;
        int maxBalls = (int) (elapsedEffectTime );
        if (elapsedEffectTime  > 20) maxBalls += (elapsedEffectTime  - 20) / 0.350f;
        float umts = 1f;
        if (elapsedEffectTime  > 18) umts = (p.cos(elapsedEffectTime  * 7f) * 0.2f + 1f);

        float trackScale = p.lerp(0.3f, 1.3f, relativeEffectTime);
        for (int i = 1; i < maxBalls; i++) {
            float x = umts * p.width * (0.5f + trackScale * 0.5f * p.sin(separation * 0.192f * i + elapsedEffectTime * 4f * speed));
            float y = umts * p.height * (0.5f + trackScale * 0.5f * p.sin(separation * 0.17f * i + 13.123f + elapsedEffectTime * 4.2f * speed));
            float s = (40 + i * (10 - umts));
            if (elapsedEffectTime  > 30) s *= 1f - ((elapsedEffectTime  - 30f) / 5f);
            p.fill((1f - umts * 0.1f * i * 1.3f * x) % 60 + i * 5,
                    (i * umts * 0.1f * y) % 60 + i * 5,
                    (umts * 0.1f + s) % 255);
            p.ellipse(x, y, s, s);
        }

        p.textSize(50);
        p.fill(100, 100, 255);
        if (elapsedEffectTime  > 12 && elapsedEffectTime  < 16) p.text("Rajatietotekniikka presents", p.width * 0.15f, p.height * 0.3f);
        p.fill(((int) (120 * p.sin(elapsedEffectTime  * 10f))), 120, 255);
        p.textSize(100 * umts);
        float disturb = 0f;
        if (elapsedEffectTime  > 24) disturb = (elapsedEffectTime  - 24f) / 15f;
        float tx = disturb * p.sin(elapsedEffectTime * 17f);
        float ty = disturb * p.cos(elapsedEffectTime * 13f);
        if (elapsedEffectTime  > 15 && elapsedEffectTime  < 30)
            p.text("⛯* SFÄÄRIMATO *⛯", p.width * (0.08f + tx) - umts * 50f + 50, p.height * (0.5f + ty) - umts * 50f + 50);
//        fill(30, 50, 200);
//        textSize(60);
//        if (time > 17 && time < 25) text("Ja Homeopaattinen Harmonia", width*0.1f, height * 0.7f);

    }
}