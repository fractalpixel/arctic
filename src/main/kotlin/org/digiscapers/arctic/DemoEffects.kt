package org.digiscapers.arctic

import org.rajatietotekniikka.sfäärimato.Sfäärimato

/**
 * Keeps a collection of effects and updates them and stuff.
 * Handles timing.
 */
class DemoEffects(val surface: Sfäärimato,
                  var demoEndTime: Float = 60f) {

    val effects = ArrayList<DemoEffect>()

    private var relativeTime: Long = 0
    private var lastFrameTime = System.currentTimeMillis()


    var demoStartTime: Long = 0
    var frameDurationSeconds = 0.001f

    init {
        relativeTime = System.currentTimeMillis()
    }

    fun addEffect(overlapWithPrevious: Float = 0f,
                  durationSeconds: Float = 30f,
                  demoEffect: DemoEffect) {
        demoEffect.init(surface)
        demoEffect.durationSeconds = durationSeconds
        demoEffect.overlapWithPrevious = overlapWithPrevious
        effects.add(demoEffect)
    }

    fun updateAndDraw() {

        // Calculate delta time
        val currentSystemTime = System.currentTimeMillis()
        frameDurationSeconds = (currentSystemTime - lastFrameTime) / 1000f
        lastFrameTime = currentSystemTime

        val demoTime = getTimeSeconds()

        // Start effects if necessary
        var t = 0f
        for (effect in effects) {
            // Start if if necessary
            if (surface.timeSeconds > t - effect.overlapWithPrevious) effect.startDemo(surface.timeSeconds)

            t += effect.durationSeconds
        }

        // Draw effects (next ones under previous one)
        for (effect in effects.reversed()) {
            effect.handleUpdate(demoTime, frameDurationSeconds)
        }

        // Check if we should end demo
        if (getTimeSeconds() > demoEndTime) surface.exit()
    }

    /**
     * @return time in the demo since the start in seconds.
     */
    fun getTimeSeconds(): Float {
        return (System.currentTimeMillis() - relativeTime - demoStartTime) / 1000f
    }


}