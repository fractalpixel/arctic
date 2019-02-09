package org.digiscapers

import processing.core.PApplet

/**
 *
 */
class TestProcessing: PApplet() {
    override fun settings() {

        size(500,400, P3D)
    }

    override fun setup() {

        background(0.5f)
    }

    override fun draw() {
        ellipse(200f,200f,50f,50f)

    }
}