package org.rajatietotekniikka.sfäärimato.effektit

import org.digiscapers.arctic.DemoEffect
import org.digiscapers.arctic.Sfääri
import org.digiscapers.arctic.SfääriJärjestelmä
import org.rajatietotekniikka.sfäärimato.Sfäärimato
import org.rajatietotekniikka.sfäärimato.utils.SampledImage

/**
 *
 */
class ScrolleriEffekti(val filu: String = "scroller.png"): DemoEffect() {

    lateinit var scrollerImage: SampledImage

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
            ball.hue *= 0.94f // Reddish fade
            ball.sat = Sfäärimato.lerp(ball.sat, 1f, 0.01f) // Saturate
            ball.lum *= 0.87f // Darken
            ball.alpha *= 0.94f // Transparencify

            // Fade at end
            //ball.alpha *= p.mapAndClamp(relativeEffectTime, 0.9f, 1f, 1f, 0f)

            // Colorize with scroller
            val sample = scrollerImage.sample(ball.x, ball.y)
            val alpha = p.alpha(sample)
            val keep = p.clampToZeroToOne(Sfäärimato.lerp(1f, 0.4f, alpha))
            ball.hue = if (alpha > 0.2f) p.hue(sample) else ball.hue //Sfäärimato.lerp(p.hue(sample), ball.hue, keep * 0.5f)
            ball.sat = Sfäärimato.lerp(p.saturation(sample), ball.sat, keep)
            ball.lum = Sfäärimato.lerp(p.brightness(sample), ball.lum, keep)
            ball.alpha = Sfäärimato.lerp(alpha, ball.alpha, keep)
            //ball.lum *= ball.alpha

            // Wrap around
            if (ball.x > p.width + wrapMargin)  ball.x += -p.width -wrapMargin*2f
            if (ball.x < -wrapMargin)           ball.x += +p.width +wrapMargin*2f
            if (ball.y > p.height + wrapMargin) ball.y += -p.height -wrapMargin*2f
            if (ball.y < -wrapMargin)           ball.y += +p.height +wrapMargin*2f

            // Fade in/out
            val alphaFade = p.fadeInOut(relativeEffectTime, 0f, 1f, 0f, 0.1f, 0.1f, 0f)
            ball.alpha *= alphaFade
        }
    }

    override fun setup(p: Sfäärimato) {
        scrollerImage = SampledImage(p, filu, 1f)
        scrollerImage.load()

        pallot.init(p)
    }

    override fun updateAndDraw(relativeEffectTime: Float, deltaTime: Float, elapsedEffectTime: Float) {
        // Un-evenize scroll
        val flipPos = 0.45f
        val scrollPos = p.fadeInOut(relativeEffectTime, 0f, 0.707f, 1f, flipPos, 1f - flipPos, 0f)

        //xPos = -relativeEffectTime * (image.width * 2 * scale + p.width) + image.width
        scrollerImage.xPos = (p.width * 1.2f) / scrollerImage.scale + -scrollPos * (scrollerImage.image.width /*+ (p.width* 1.4f) / scrollerImage.scale*/)

        p.noStroke()


        //val pallomäärä = p.fadeInOut(relativeEffectTime, 0.1f, 1f, 0.3f, 0.2f, 0.2f, 0f)
        val pallomäärä = 1f
        pallot.updateAndDraw(p, deltaTime, (10000 * pallomäärä).toInt())

    }

}