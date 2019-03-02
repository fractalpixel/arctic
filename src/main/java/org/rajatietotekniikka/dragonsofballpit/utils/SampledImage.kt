package org.rajatietotekniikka.dragonsofballpit.utils

import org.rajatietotekniikka.dragonsofballpit.Demo
import processing.core.PConstants
import processing.core.PImage

/**
 *
 */
class SampledImage(val surface: Demo,
                   var fileName: String,
                   val scaleOfScreen: Float = 0.5f,
                   val backgroundColor: Int = 0) {

    // Change these to change the offset of the image
    var xPos = 0f
    var yPos = 0f

    var scale = 1f
    private var loaded = false
    lateinit var image: PImage

    /**
     * Change file
     * Call load() to load.
     */
    fun setFile(name: String) {
        if (fileName != name) {
            fileName = name
            loaded = false
        }
    }

    /**
     * Ensure image is loaded.  It will also load automatically on first call to sample.
     */
    fun load() {
        if (!loaded) {
            loaded = true

            try {
                image = surface.loadImage(fileName, "png")
            }
            catch (e: Exception) {
                println("***************************")
                println("** WARNING!!  Image not found: '$fileName': " + e.message)
                println("***************************")
                image = PImage(1, 1, PConstants.ARGB) // Just a placeholder to not crash things
            }
            scale = (surface.height * scaleOfScreen) / image.height.toFloat()
        }
    }

    /**
     * Color at the specified screen pos for the image
     */
    fun sample(x: Float, y:Float): Int {
        load()

        val sampleX = (x/scale - xPos).toInt()
        val sampleY = (y/scale - yPos).toInt()

        return if (sampleX in 0 until image.width &&
            sampleY in 0 until image.height) {
            image.get(sampleX, sampleY)
        }
        else {
            backgroundColor
        }
    }

    fun center() {
        load()

        //                             v-- Should be 0.5, but its not correct.  Fudge factor it.
        xPos = surface.width * 0.5f - 0.6f * image.width * scale
        yPos = surface.height * 0.5f - 0.6f * image.height * scale
    }

}