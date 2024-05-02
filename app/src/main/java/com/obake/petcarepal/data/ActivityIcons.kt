package com.obake.petcarepal.data

import com.obake.petcarepal.R

enum class ActivityIcons(val icon: Int, val resName: Int) {
    Walk(R.drawable.person_walking_solid, R.string.walk),
    Feed(R.drawable.utensils_solid, R.string.feed),
    Play(R.drawable.football_solid, R.string.play),
    Groom(R.drawable.comb_solid, R.string.groom),
    Brush(R.drawable.tooth_solid, R.string.brush),
    Train(R.drawable.brain_solid, R.string.train),
    Sleep(R.drawable.bed_solid, R.string.sleep)
}