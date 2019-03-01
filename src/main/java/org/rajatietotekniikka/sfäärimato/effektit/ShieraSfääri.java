package org.rajatietotekniikka.sfäärimato.effektit;

import org.digiscapers.arctic.DemoEffect;

/**
 *
 */
public class ShieraSfääri extends DemoEffect {

    public void updateAndDraw(float relativeEffectTime, float deltaTime, float elapsedEffectTime) {

        // Testipalloj
        // p. etuliitteellä pääsee käsiksi processing ympäristöön.
        // getEffectRandom() alustetaan aina ennen tätä rutiinia, joten antaa aina samat satunnaisluvut
        // RelativeEffectTime menee 0sta 1:een

        for (int i = 1; i < 100; i++) {
            float hue = (float) getEffectRandom().nextGaussian();
            float sat = 0.5f;
            float lum = 0.5f;
            float alpha = 0.5f;
            p.fill(hue, sat, lum, alpha);

            float scale = p.height * 0.5f;

            float x = p.width * 0.5f + scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            float y = p.height * 0.5f + scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            float size = scale * (float) getEffectRandom().nextGaussian() * relativeEffectTime;
            p.ellipse(x, y, size, size);
        }

    }
}
