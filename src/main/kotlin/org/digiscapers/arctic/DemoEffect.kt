package org.digiscapers.arctic

import org.rajatietotekniikka.sfäärimato.Sfäärimato
import processing.core.PConstants.HSB
import java.util.*

/**
 * It can draw itself and things!
 * Also has duration and such.
 */
abstract class DemoEffect {
    var startSeconds: Float = 0f
    var endSeconds: Float = 30f

    var elapsedEffectTime: Float = 0f
    var deltaTime: Float = 1f / 60f
    var relativeEffectTime: Float = 0f

    val effectRandom = Random()

    lateinit var p: Sfäärimato

    fun init(surface: Sfäärimato) {
        this.p = surface
        setup(p)
    }



    abstract fun setup(p: Sfäärimato)

    fun setDuration(startSeconds: Float, lengthSeconds: Float) {
        this.startSeconds = startSeconds
        this.endSeconds = startSeconds + lengthSeconds
    }

    fun handleUpdate(currentTime: Float,
                     deltaTime: Float = 1f / 60f) {

        if (currentTime in startSeconds..endSeconds) {
            this.deltaTime = deltaTime
            relativeEffectTime = Sfäärimato.map(currentTime, startSeconds, endSeconds, 0f, 1f)
            elapsedEffectTime  = currentTime - startSeconds

            // Colors are hue, sat, brightness, alpha, range 0..1f.
            p.colorMode(HSB, 1f)

            // Init random with seed for this demo effect
            effectRandom.setSeed(hashCode().toLong())

            updateAndDraw(relativeEffectTime, deltaTime, elapsedEffectTime)
        }
    }

    /**
     * @param surface: Processing context to draw on.
     *                 Or make a demo effect an inner class of the main demo class to access the drawing commands directly.
     * @param relativeEffectTime 0 at the start of the effect and 1 at the end of the effect.
     * @parma deltaTime amount of time to advance.
     */
    abstract fun updateAndDraw(relativeEffectTime: Float,
                               deltaTime: Float,
                               elapsedEffectTime: Float )

}