package com.stmx.mocklock

import com.stmx.mocklock.data.entity.GeoPoint
import com.stmx.mocklock.ui.models.GeoPointUI

val route = arrayOf(
    GeoPoint(57.691052243336436, 39.77586150169373),
    GeoPoint(57.691047, 39.775871),
    GeoPoint(57.691959, 39.777717),
    GeoPoint(57.692249, 39.778262),
    GeoPoint(57.692239, 39.778285),
    GeoPoint(57.692239, 39.778285),
    GeoPoint(57.692249, 39.778262),
    GeoPoint(57.692383, 39.778429),
    GeoPoint(57.696160, 39.782325),
    GeoPoint(57.696193, 39.782269),
    GeoPoint(57.696193, 39.782268),
    GeoPoint(57.697147, 39.780555),
    GeoPoint(57.697172, 39.780601),
    GeoPoint(57.697172, 39.780601),
    GeoPoint(57.697147, 39.780555),
    GeoPoint(57.696930, 39.780125),
    GeoPoint(57.696930, 39.780125),
    GeoPoint(57.696921, 39.780109),
    GeoPoint(57.697020, 39.779938),
    GeoPoint(57.696772, 39.779451),
    GeoPoint(57.696796, 39.779409),
    GeoPoint(57.696796, 39.779409),
    GeoPoint(57.697184, 39.778724),
    GeoPoint(57.697360, 39.779090),
    GeoPoint(57.697660, 39.778554),
    GeoPoint(57.697654, 39.778544),
    GeoPoint(57.697654, 39.778544),
    GeoPoint(57.697660, 39.778554),
    GeoPoint(57.697891, 39.778302),
    GeoPoint(57.697954, 39.778176),
    GeoPoint(57.697900, 39.777849),
    GeoPoint(57.698013, 39.777651),
    GeoPoint(57.698125, 39.777554),
    GeoPoint(57.698641, 39.776733),
    GeoPoint(57.698777, 39.776875),
    GeoPoint(57.699076, 39.776376),
    GeoPoint(57.699058, 39.776009),
    GeoPoint(57.699070, 39.775990),
    GeoPoint(57.699070, 39.775990),
    GeoPoint(57.699383, 39.775486),
    GeoPoint(57.699748, 39.776204),
    GeoPoint(57.700353, 39.777378),
    GeoPoint(57.701004, 39.778733),
    GeoPoint(57.701467, 39.779649),
    GeoPoint(57.701449, 39.779680),
)

object Foo {
    private var callback: ((GeoPointUI) -> Unit)? = null
    fun observePoint(callback: (GeoPointUI) -> Unit) {
        this.callback = callback
    }

    fun setPoint(point: GeoPointUI) {
        callback?.invoke(point)
    }
}