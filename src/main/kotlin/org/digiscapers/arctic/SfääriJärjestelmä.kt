package org.digiscapers.arctic

import org.rajatietotekniikka.dragonsofballpit.Demo
import java.util.*

/**
 * Kun on monta sfääriä ja ne kaikki pitäisi piirtää tjms ja on tylsää tehdä itse looppi.
 *
 * @param alkumäärä alussa olevien pallojen määrä
 * @param pallonTappoValitsin valitsee minkä pallon tulee poistaa jos niitä on liikaa.  By default poistaa vanhimman.
 * @param palloTehdas luo uuden pallon kun on tarvis.  Saa parametrina pallon numeron ja seedatun satunnaislukugeneraattorin.
 */
open class SfääriJärjestelmä @JvmOverloads constructor(
    var alkumäärä: Int = 10,
    var pallonTappoValitsin: (pallot: List<Sfääri>) -> Sfääri = { it.first() },
    var palloTehdas: (number: Int, random: Random) -> Sfääri = { i, r ->
        Sfääri(
            r.nextGaussian().toFloat(),
            r.nextGaussian().toFloat(),
            r.nextGaussian().toFloat(),
            r.nextGaussian().toFloat(),
            r.nextGaussian().toFloat(),
            r.nextGaussian().toFloat(),
            size = r.nextFloat() * 0.05f,
            hue = r.nextFloat())
    }) {

    val sfäärit = ArrayList<Sfääri>()

    private var nextId = 1
    private var random = Random()

    fun init(pinta: Demo) {
        for (i in 1 .. alkumäärä) {
            luoPallo(pinta)
        }
    }

    /**
     * Update and draw balls, can also adjust number of balls.
     */
    fun updateAndDraw(pinta: Demo,
                      deltaTime: Float = 1f / 60f,
                      palloMäärä: Int = 10) {

        var targetNum = palloMäärä
        if (targetNum < 0) targetNum = 0

        // Remove extras
        while (sfäärit.size > targetNum) {
            poistaPallo()
        }

        // Add new if needed
        while (sfäärit.size < targetNum) {
            luoPallo(pinta)
        }

        // Any overridden updates
        for (sfääri in sfäärit) {
            updateBall(pinta, sfääri)
        }

        // Update all
        for (sfääri in sfäärit) {
            sfääri.updateAndDraw(pinta, deltaTime)
        }


    }

    protected open fun updateBall(pinta: Demo,
                                  ball: Sfääri) {

    }

    protected fun luoPallo(pinta: Demo): Sfääri {
        val id = nextId++
        random.setSeed(id.toLong())
        random.setSeed(random.nextLong())

        val pallo = palloTehdas(id, random)
        sfäärit.add(pallo)

        initBall(pinta, pallo)

        return pallo
    }

    protected open fun initBall(p: Demo, ball: Sfääri) {

    }

    protected fun poistaPallo(): Sfääri {
        val ballToRemove = pallonTappoValitsin(sfäärit)
        sfäärit.remove(ballToRemove)

        return ballToRemove
    }

}