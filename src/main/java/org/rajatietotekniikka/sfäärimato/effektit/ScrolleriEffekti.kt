package org.rajatietotekniikka.sfäärimato.effektit

import org.digiscapers.arctic.DemoEffect
import org.digiscapers.arctic.Sfääri
import org.digiscapers.arctic.SfääriJärjestelmä
import org.rajatietotekniikka.sfäärimato.Sfäärimato
import processing.core.PImage

/**
 *
 */
class ScrolleriEffekti(val filu: String = "helloworld.png"): DemoEffect() {

    var scale = 6f
    var xPos = 0f
    var yPos = 0f

    private lateinit var image: PImage

    private val pallot = object: SfääriJärjestelmä(0) {

        override fun initBall(p: Sfäärimato, ball: Sfääri) {
            ball.x = p.random(0f, p.width.toFloat())
            ball.y = p.random(0f, p.height.toFloat())
            ball.dx *= 4f
            ball.dy *= 4f
            ball.forceX *= 1f
            ball.forceY *= 1f + 0.5f
            ball.size *= 1.1f + 0.04f
            ball.lum = 0f
            ball.sat = 0f
            ball.alpha = 0f
        }

        val wrapMargin = 100f

        override fun updateBall(p: Sfäärimato, ball: Sfääri) {

            // Blow
            ball.forceX = p.mapAndClamp(relativeEffectTime, 0f, 1f, -4f, 40f)

            // Darken colors
            ball.hue *= 0.98f // Reddish fade
            ball.sat = Sfäärimato.lerp(ball.sat, 1f, 0.01f) // Saturate
            ball.lum *= 0.87f // Darken
            ball.alpha *= 0.96f // Transparencify

            // Colorize with scroller
            val sample = sample(ball.x, ball.y)
            val alpha = p.alpha(sample)
            val keep = p.clampToZeroToOne(Sfäärimato.lerp(1f, 0.7f, alpha))
            ball.hue = if (alpha > 0.2f) p.hue(sample) else ball.hue //Sfäärimato.lerp(p.hue(sample), ball.hue, keep * 0.5f)
            ball.sat = Sfäärimato.lerp(p.saturation(sample), ball.sat, keep)
            ball.lum = Sfäärimato.lerp(p.brightness(sample), ball.lum, keep)
            ball.alpha = Sfäärimato.lerp(alpha, ball.alpha, keep)
            //ball.lum *= ball.alpha

            // Wrap around
            if (ball.x > p.width + wrapMargin) ball.x = -wrapMargin
            if (ball.x < -wrapMargin) ball.x = p.width + wrapMargin
            if (ball.y > p.height + wrapMargin) ball.y = -wrapMargin
            if (ball.y < -wrapMargin) ball.y = p.height + wrapMargin

            // Fade in/out
            val alphaFade = p.fadeInOut(relativeEffectTime, 0f, 1f, 0f, 0.1f, 0.1f, 0f)
            ball.alpha *= alphaFade
        }
    }

    override fun setup(p: Sfäärimato) {
        image = p.loadImage(filu)

        scale = p.height / image.height.toFloat()

        pallot.init(p)
    }

    override fun updateAndDraw(relativeEffectTime: Float, deltaTime: Float, elapsedEffectTime: Float) {
        // Un-evenize scroll
        val flipPos = 0.45f
        val scrollPos = p.fadeInOut(relativeEffectTime, 0f, 0.57f, 1f, flipPos, 1f - flipPos, 0f)

        //xPos = -relativeEffectTime * (image.width * 2 * scale + p.width) + image.width
        xPos = (p.width * 1.2f) / scale + -scrollPos * (image.width + (p.width* 1.4f) / scale)

        p.noStroke()


        //val pallomäärä = p.fadeInOut(relativeEffectTime, 0.1f, 1f, 0.3f, 0.2f, 0.2f, 0f)
        val pallomäärä = 1f
        pallot.updateAndDraw(p, deltaTime, (12000 * pallomäärä).toInt())

    }

    fun sample(x: Float, y:Float): Int {
        val sampleX = (x/scale - xPos).toInt()
        val sampleY = (y/scale - yPos).toInt()

        return if (sampleX in 0 until image.width &&
                   sampleY in 0 until image.height) {
            image[sampleX, sampleY]
        }
        else {
            //p.color(x/ p.width, 1f, 0.5f)
            p.color(0f)
        }
    }
}