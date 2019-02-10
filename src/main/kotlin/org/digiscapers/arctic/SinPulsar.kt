package org.digiscapers.arctic

import java.lang.Math.*
import kotlin.math.sign

/**
 * Good old sin(time), made a bit easier to use.
 *
 * Use get() to get values.
 *
 * @param waveTime time from peak to peak in seconds.
 * @param amplitude amplitude of the wave (distance up and down from offset that it will go)
 * @param offset average value that wave oscillates around
 * @param sharpness sin output is raised to this power before being used, sign is preserved.  Changes wave shape.
 * @param phaseStart position of the phase at the time when get is called the first time.
 */
class SinPulsar @JvmOverloads constructor(var waveTime: Float = 1f,
                var amplitude: Float = 1f,
                var offset: Float = 0f,
                var sharpness: Float = 1f,
                phaseStart: Float = 0f): PulsarBase() {

    var phase = phaseStart.toDouble()

    override fun doGetValue(deltaTime: Float, timeSinceStart: Float): Float {
        // Update phase, this way if waveTime is changed thing look smooth
        phase += deltaTime / waveTime

        // Sin wave
        val v1 = sin(phase * PI * 2.0)

        // Raise to sharpness, maintain symmetry around 0
        val v2 = (pow(abs(v1), sharpness.toDouble()) * sign(v1)).toFloat()

        // Map to desired output range
        return offset + amplitude * v2
    }
}