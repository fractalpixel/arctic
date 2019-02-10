package org.digiscapers.arctic

/**
 *
 */
abstract class PulsarBase: Pulsar {

    protected var called = false
    protected var lastCallTime: Long = 0
    protected var startTime: Long = 0

    init {
        startTime = System.currentTimeMillis()
    }

    /**
     * Value for the current time.
     */
    override fun get(): Float {
        var dt = 0.0f
        val t = System.currentTimeMillis()
        if (!called) {
            called = true
            setup()
        }
        else {
            dt = (t - lastCallTime) / 1000.0f
        }
        lastCallTime = t

        val timeSinceStart = (t - startTime) / 1000.0f

        return doGetValue(dt, timeSinceStart)
    }

    protected open fun setup() {}

    protected abstract fun doGetValue(deltaTime: Float, timeSinceStart: Float): Float
}