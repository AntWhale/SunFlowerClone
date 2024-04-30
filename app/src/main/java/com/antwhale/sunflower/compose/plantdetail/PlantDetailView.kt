package com.antwhale.sunflower.compose.plantdetail

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.antwhale.sunflower.R
import com.antwhale.sunflower.compose.utils.TextSnackbarContainer
import com.antwhale.sunflower.data.Plant
import com.antwhale.sunflower.viewmodels.PlantDetailViewModel
import java.lang.reflect.Modifier

data class PlantDetailsCallbacks(
    val onFabClick: () -> Unit,
    val onBackClick: () -> Unit,
    val onShareClick: (String) -> Unit,
    val onGalleryClick: (Plant) -> Unit
)

@Composable
fun PlantDetailsScreen(
    plantDetailsViewModel: PlantDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onGalleryClick: (Plant) -> Unit
) {
    val plant = plantDetailsViewModel.plant.observeAsState().value
    val isPlanted = plantDetailsViewModel.isPlanted.collectAsState(initial = false).value
    val showSnackbar = plantDetailsViewModel.showSnackbar.observeAsState().value

    if(plant != null && isPlanted!= null && showSnackbar != null) {
        Surface {
            TextSnackbarContainer(
                snackbarText = stringResource(R.string.added_plant_to_garden),
                showSnackbar = showSnackbar,
                onDismissSnackbar = { plantDetailsViewModel.dismissSnackbar() }
            ) {
                PlantDetails(
                    plant,
                    isPlanted,
                    plantDetailsViewModel.hasValidUnsplashKey(),
                    PlantDetailsCallbacks(
                        onBackClick = onBackClick,
                        onFabClick = {
                            plantDetailsViewModel.addPlantToGarden()
                        },
                        onShareClick = onShareClick,
                        onGalleryClick = onGalleryClick
                    )
                )
            }
        }
    }
}

@Composable
fun PlantDetails(
    plant: Plant,
    isPlanted: Boolean,
    hasValidUnsplashKey: Boolean,
    callbacks: PlantDetailsCallbacks,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    //PlantDetails owns the scrollerPosition to simulate CollapsingToolbarLayout's behavior
    val scrollState = rememberScrollState()
    var plantScroller by remember {
        mutableStateOf(PlantDetailsScroller(scrollState, Float.MIN_VALUE))
    }
    val transitionState =
        remember(plantScroller) { plantScroller.toolbarTransitionState }

}