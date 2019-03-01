package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;
import org.jetbrains.annotations.NotNull;
import org.rajatietotekniikka.sfäärimato.Sfäärimato;

/**
 *
 */
public class IrahSfäärit extends DemoEffect {

    public void setup(@NotNull Sfäärimato p) {

    }

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {

        // Testipalloj
        // p. etuliitteellä pääsee käsiksi processing ympäristöön.
        // getEffectRandom() alustetaan aina ennen tätä rutiinia, joten antaa aina samat satunnaisluvut
        // RelativeEffectTime menee 0sta 1:een


        for (int i = 1; i < 100; i++) {
            float hue = (float) getEffectRandom().nextGaussian();
            float sat = 0.7f;
            float lum = 0.4f;
            float alpha = 0.6f;
            p.fill(hue, sat, lum, alpha);

            float scale = p.height * 0.5f;

            float x = p.width * 0.5f + scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            float y = p.height * 0.5f + scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            float size = scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            p.ellipse(x, y, size, size);
        }

    }
}
