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
                    val numberOfFiles: Int = 10,
                    val startDelay: Float = 0.2f,
                    val splatBallScale: Float = 0.75f,
                    val splatCount: Int = 30000): DemoEffect() {


    lateinit var sampler: SampledImage



    var imageSize = 0.5f

    var splatCyclePos = 0f
    var visibleSplatNum = 0

    var splatterFocus = 0.1f
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


        // Load image if needed
        ensureSplatLoaded(visibleSplatNum)

        // Update splat when it changes
        if (splatCyclePos < prevSplatterCycle) {

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
        splatterFocus = 0.2f + 0.02f * random.nextGaussian().toFloat()

        // Fade to different color
        val fadeAwayHue = random.nextFloat()
        splatterFadeAwayColor = p.color(fadeAwayHue, 1f, 0f, 0f)

        // Init random destination etc
        for (splat in splatter) {
            splat.init(random, visibleSplatNum)
        }

    }


    data class Splat(val host: SplatterBalls) {
        var targetRelX: Float = 0f
        var targetRelY: Float = 0f
        var startRelX: Float = 0f
        var startRelY: Float = 0f
        var color: Int = 0
        var sizeFactor: Float = 0.01f
        var rotations: Float = 0f

        fun init(random: Random, num: Int) {
            targetRelX = 0.7f * host.p.width * (random.nextFloat() - 0.5f + 0.2f*random.nextGaussian().toFloat())
            targetRelY = 0.7f * host.p.height * (random.nextFloat() - 0.5f + 0.2f*random.nextGaussian().toFloat())
            startRelX = 0.5f * host.p.width * (random.nextFloat() - 0.5f + 0.2f*random.nextGaussian().toFloat())
            startRelY = 0.5f * host.p.height * (random.nextFloat() - 0.5f + 0.2f*random.nextGaussian().toFloat())
            sizeFactor = 0.015f + 0.004f * random.nextGaussian().toFloat()
            color = host.sampler.sample(targetRelX + host.p.width / 2, targetRelY + host.p.height / 2)
            rotations = if (num <= 0) 0f
                       else 4f * random.nextGaussian().toFloat().toFloat()
        }

        fun draw() {

            var sourceX = 0.1f * startRelX * host.splatExpansion
            var sourceY = 0.1f * startRelY * host.splatExpansion
            val targetX = targetRelX * host.splatExpansion
            val targetY = targetRelY * host.splatExpansion

            // Rotate transform backwards for extreme extrem
            val a = rotations * Math.PI * 2f
            val r = host.p.height * 0.3f
            val rotXOffX = r * Math.cos(a).toFloat()
            val rotXOffY = r * -Math.sin(a).toFloat()
            sourceX += rotXOffX
            sourceY += rotXOffY

            val x = PApplet.lerp(sourceX, targetX, host.splatSizes)
            val y = PApplet.lerp(sourceY, targetY, host.splatSizes)

            val size = host.splatBallScale * host.splatSizes * sizeFactor * host.p.height
            /*
            println("host.splatSizes = ${host.splatSizes}")
            println("host.p.height = ${host.p.height}")
            println("sizeFactor = ${sizeFactor}")
            println("size = ${size}")
            */

            val sampledColor = host.sampler.sample(x  + host.p.width / 2, y  + host.p.height / 2)
            color = host.p.lerpColor(color, sampledColor, 0.07f)

            val c = host.p.lerpColor(host.splatterFadeAwayColor, color, host.splatAlpha)

            // Get alpha from darkness
            val helpAlpha = host.p.mapAndClamp(host.p.brightness(c), 0.1f, 0.6f, 0f, 1f)

            // Alpha doesn't seemto be properly lerped or something?  mix in here:
            host.p.fill(host.p.hue(c), host.p.saturation(c), host.p.brightness(c), 0.66f * helpAlpha * host.splatAlpha)

            host.p.ellipse(x  + host.p.width / 2, y  + host.p.height / 2, size, size)
        }

    }

}