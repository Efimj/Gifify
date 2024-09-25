package com.yefim.giphy_api.model

/**
 * The MPAA-style rating for this content. Examples include Y, G, PG, PG-13 and R
 */
enum class MPAARating(val value: String) {
    Y("Y"),
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R");

    override fun toString(): String {
        return value
    }
}