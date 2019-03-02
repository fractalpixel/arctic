package org.digiscapers.arctic

import org.rajatietotekniikka.dragonsofballpit.Demo

/**
 * Keeps a collection of effects and updates them and stuff.
 * Handles timing.
 */
class DemoEffects(val surface: Demo,
                  var lastEffectEndToDemoEndSeconds: Float = 5f) {

    val effects = ArrayList<DemoEffect>()

    private var relativeTime: Long = 0
    private var lastFrameTime = System.currentTimeMillis()

    private var endTimeSeconds = -1f

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
            if (demoTime > t - effect.overlapWithPrevious) effect.startDemo(demoTime)

            t += effect.durationSeconds
        }

        // Draw effects (next ones under previous one)
        for (effect in effects.reversed()) {
            effect.handleUpdate(demoTime, frameDurationSeconds)
        }

        // Set end time if all effects ended and we didn't set it yet
        if (endTimeSeconds < 0 && effects.all { it.ended }) {
            endTimeSeconds = demoTime + lastEffectEndToDemoEndSeconds
        }

        // Should we end
        if (endTimeSeconds >= 0 && demoTime > endTimeSeconds) {
            // Exit
            surface.exit()
        }
    }

    /**
     * @return time in the demo since the start in seconds.
     */
    fun getTimeSeconds(): Float {
        return (System.currentTimeMillis() - relativeTime - demoStartTime) / 1000f
    }


}