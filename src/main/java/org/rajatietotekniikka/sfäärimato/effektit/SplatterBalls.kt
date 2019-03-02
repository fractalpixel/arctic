package org.rajatietotekniikka.sfäärimato.effektit

import org.digiscapers.arctic.DemoEffect
import org.rajatietotekniikka.sfäärimato.Sfäärimato
import org.rajatietotekniikka.sfäärimato.utils.SampledImage
import processing.core.PApplet
import java.util.*

/**
 *
 */
class SplatterBalls(val fileBaseName: String = "splat",
                    val numberOfFiles: Int = 3,
                    val startDelay: Float = 0.2f): DemoEffect() {


    lateinit var sampler: SampledImage


    val splatCount = 10000

    var imageSize = 0.5f

    var splatCyclePos = 0f
    var visibleSplatNum = 0

    var splatterFocus = 0.4f
    var splatExpansion = 0.5f
    var splatSizes = 0.5f
    var splatAlpha = 0.5f

    var splatterFadeAwayColor: Int = 0

    val splatter = ArrayList<Splat>()

    val random = Random()

    override fun setup(p: Sfäärimato) {

        // Create sampler
        sampler = SampledImage(p, fileBaseName + "_1.png", imageSize)

        // Create splats
        for(i in 0..splatCount) {
            splatter.add(Splat(this))
        }

        // Setup first
        setupSplatter(0)
    }

    override fun updateAndDraw(relativeEffectTime: Float, deltaTime: Float, elapsedEffectTime: Float) {

        // Determine position splat cycle and splat num
        val prevSplatterCycle = splatCyclePos
        val r = p.mapAndClamp(relativeEffectTime, startDelay, 1f, 0f, 1f)
        visibleSplatNum = 1 + (r * numberOfFiles).toInt()
        splatCyclePos = (r * numberOfFiles) % 1f

        // Scale splat positions etc over splat cycle
        splatExpansion = p.fadeInOut(splatCyclePos, 0f, 1f, 20f, 0.3f, 0.2f, 0.25f)
        splatSizes = p.fadeInOut(splatCyclePos, 0f, 1f, 5f, 0.25f, 0.23f, 0.25f)
        splatAlpha = p.fadeInOut(splatCyclePos, 0f, 1f, 0f, 0.15f, 0.1f, 0.25f)

        /*
        println("")
        println("visibleSplatNum = ${visibleSplatNum}")
        println("splatCyclePos = ${splatCyclePos}")
        println("splatExpansion = ${splatExpansion}")
        println("splatSizes = ${splatSizes}")
        println("splatAlpha = ${splatAlpha}")
        */


        // Update splat when it changes
        if (splatCyclePos < prevSplatterCycle) {
            // Load image if needed
            ensureSplatLoaded(visibleSplatNum)

            // Setup table for next splatter
            setupSplatter(visibleSplatNum)
        }

        // Draw
        p.noStroke()
        for (splat in splatter) {
            splat.draw()
        }
    }

    fun ensureSplatLoaded(num: Int) {
        // If same file as before, this will not do anything
        sampler.setFile(fileBaseName + "_$num.png")
        sampler.load()

        // Center
        sampler.center()
    }

    private fun setupSplatter(splatterNum: Int) {

        // Init random for this one
        random.setSeed(splatterNum.toLong())
        random.setSeed(random.nextLong())

        // Spread of this one
        splatterFocus = 0.5f + 0.2f * random.nextGaussian().toFloat()

        // Fade to different color
        val fadeAwayHue = random.nextFloat()
        splatterFadeAwayColor = p.color(fadeAwayHue, 1f, 0f, 0f)

        // Init random destination etc
        for (splat in splatter) {
            splat.init(random)
        }

    }


    data class Splat(val host: SplatterBalls) {
        var targetRelX: Float = 0f
        var targetRelY: Float = 0f
        var startRelX: Float = 0f
        var startRelY: Float = 0f
        var color: Int = 0
        var sizeFactor: Float = 0.01f

        fun init(random: Random) {
            targetRelX = host.splatterFocus * host.p.width * random.nextGaussian().toFloat()
            targetRelY = host.splatterFocus * host.p.height * random.nextGaussian().toFloat()
            startRelX = host.splatterFocus * host.p.width * random.nextGaussian().toFloat()
            startRelY = host.splatterFocus * host.p.height * random.nextGaussian().toFloat()
            sizeFactor = 0.015f + 0.004f * random.nextGaussian().toFloat()
            color = host.sampler.sample(targetRelX + host.p.width / 2, targetRelY + host.p.height / 2)
        }

        fun draw() {

            val sourceX = 0.2f * startRelX * host.splatExpansion + host.p.width / 2
            val sourceY = 0.2f * startRelY * host.splatExpansion + host.p.height / 2
            val targetX = targetRelX * host.splatExpansion + host.p.width / 2
            val targetY = targetRelY * host.splatExpansion + host.p.height / 2

            val x = PApplet.lerp(sourceX, targetX, host.splatSizes)
            val y = PApplet.lerp(sourceY, targetY, host.splatSizes)

            val size = host.splatSizes * sizeFactor * host.p.height
            /*
            println("host.splatSizes = ${host.splatSizes}")
            println("host.p.height = ${host.p.height}")
            println("sizeFactor = ${sizeFactor}")
            println("size = ${size}")
            */

            color = host.sampler.sample(targetRelX + host.p.width / 2, targetRelY + host.p.height / 2)

            val c = host.p.lerpColor(host.splatterFadeAwayColor, color, host.splatAlpha)

            // Alpha doesn't seemto be properly lerped or something?  mix in here:
            host.p.fill(host.p.hue(c), host.p.saturation(c), host.p.brightness(c), 0.8f * host.splatAlpha)

            host.p.ellipse(x, y, size, size)
        }

    }

}