package com.antwhale.sunflower.compose.plantdetail

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

private val HeaderTransitionOffset = 190.dp

data class PlantDetailsScroller(
    val scrollState: ScrollState,
    val namePosition: Float
) {
    val toolbarTransitionState = MutableTransitionState(ToolbarState.HIDDEN)

    fun getToolbarState(density: Density): ToolbarState {
        //When the namePosition is placed correctly on the screen(position > 1f) and it's
        //position is close to the header, then show the toolbar.
        return if(namePosition > 1f &&
            scrollState.value > (namePosition - getTransitionOffset(density))
            ) {
            toolbarTransitionState.targetState = ToolbarState.SHOWN
            ToolbarState.SHOWN
        } else {
            toolbarTransitionState.targetState = ToolbarState.HIDDEN
            ToolbarState.HIDDEN
        }
    }

    private fun getTransitionOffset(density: Density): Float = with(density) {
        HeaderTransitionOffset.toPx()
    }
}

enum class ToolbarState { HIDDEN, SHOWN }

val ToolbarState.isShown
    get() = this == ToolbarState.SHOWN

