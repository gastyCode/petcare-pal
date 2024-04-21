package com.obake.petcarepal.ui.tips

import com.obake.petcarepal.data.model.Tip

data class TipsState (
    val tips: List<Tip> = emptyList(),
    val currentTip: Tip? = null
)