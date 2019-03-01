package org.digiscapers.arctic

import processing.core.PApplet

/**
 * Sellanen pallo joka olisi voinut olla kaikkien pallojen äiti jos sen olisi tehnyt demon alussa.
 * Nyt sitä käytetään vain joissain osissa...
 *
 * Kirjoitettu kotlinilla koska default argumentit jne on vaan paljon helpompia.
 */
open class Sfääri @JvmOverloads constructor (
    var x: Float = 0f,
    var y: Float = 0f,
    var dx: Float = 0f,
    var dy: Float = 0f,
    var forceX: Float = 0f,
    var forceY: Float = 0f,
    var mass: Float = 1f,
    var size: Float = 0.06f,
    var hue: Float = 0f, // Punainen
    var sat: Float = 0.5f,
    var lum: Float = 0.5f,
    var alpha: Float = 1f,
    var updateFunction: (sfääri: Sfääri, deltaTime: Float) -> Unit = {sf, dt -> /* Do nothing by default */}) {


    /**
     * Pitää kutsua kerran ruudussa jotta kaikki pienet pallot saavat tarpeeksi prosessoritehoa että ne ei kuole.
     */
    open fun updateAndDraw(surface: PApplet,
                           frameTime: Float = 1f / 60f) {
        // Kutsutaan käyttäjän oma update funktiota (jos annettu)
        updateFunction(this, frameTime)

        // Gotta do more fysiks!
        fysiikkaa(frameTime)

        // Could as well draw it from here as well.  In a real game updates might run at a different frequency from drawing
        draw(surface)
    }

    open fun fysiikkaa(deltaTime: Float) {
        // Nopel prise winning fysix enkine pelow!
        dx += forceX * deltaTime / mass
        dy += forceY * deltaTime / mass
        x += dx * deltaTime
        y += dy * deltaTime
    }

    open fun draw(pinta: PApplet) {
        val kokoReferenssi = pinta.height // Että skaalaantuu ruudun koon mukaan
        val diametri = kokoReferenssi * size

        pinta.fill(hue, sat, lum, alpha)
        pinta.ellipse(x, y, diametri, diametri)
    }

}