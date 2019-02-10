package org.digiscapers.arctic

/**
 * Something that produces a changing value over time.
 */
interface Pulsar {
    fun get(): Float
}