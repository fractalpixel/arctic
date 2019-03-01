package org.rajatietotekniikka.sfäärimato.effektit

import org.digiscapers.arctic.DemoEffect
import org.rajatietotekniikka.sfäärimato.Sfäärimato

/**
 *
 */
class ScrolleriEffekti(val filu: String = "helloworld.png"): DemoEffect() {

    override fun setup(p: Sfäärimato) {
        val image = p.loadImage(filu)
    }

    override fun updateAndDraw(relativeEffectTime: Float, deltaTime: Float, elapsedEffectTime: Float) {

    }
}