package com.antwhale.sunflower.compose.garden

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.antwhale.sunflower.R
import com.antwhale.sunflower.data.PlantAndGardenPlantings
import com.antwhale.sunflower.viewmodels.GardenPlantingListViewModel
import com.antwhale.sunflower.viewmodels.PlantAndGardenPlantingsViewModel

@Composable
fun GardenScreen(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    viewModel: GardenPlantingListViewModel = hiltViewModel(),
    onAddPlantClick: () -> Unit,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
    val gardenPlants by viewModel.plantAndGardenPlantings.collectAsState(initial = emptyList())
    GardenScreen(
        gardenPlants = gardenPlants,
        modifier = modifier,
        onAddPlantClick = onAddPlantClick,
        onPlantClick = onPlantClick
    )
}

@Composable
fun GardenScreen(
    gardenPlants: List<PlantAndGardenPlantings>,
    modifier: Modifier = Modifier,
    onAddPlantClick: () -> Unit = {},
    onPlantClick: (PlantAndGardenPlantings) -> Unit = {}
) {
    if(gardenPlants.isEmpty()) {
        EmptyGarden(onAddPlantClick, modifier)
    } else {
        GardenList(gardenPlants = gardenPlants, onPlantClick = onPlantClick, modifier = modifier)
    }
}

@Composable
private fun GardenList(
    gardenPlants: List<PlantAndGardenPlantings>,
    onPlantClick: (PlantAndGardenPlantings) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyVerticalGrid(
        columns =  GridCells.Fixed(2),
        modifier,
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.card_side_margin),
            vertical = dimensionResource(id = R.dimen.card_side_margin)
        )
    ) {
        items(
            items = gardenPlants,
            key = { it.plant.plantId }
        ) {
            GardenListItem(plant = it, onPlantClick = onPlantClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GardenListItem(
    plant: PlantAndGardenPlantings,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
    val vm = PlantAndGardenPlantingsViewModel(plant)

    val cardSideMargin = dimensionResource(id = R.dimen.card_side_margin)
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

    ElevatedCard(
        onClick = {onPlantClick(plant)},
        modifier = Modifier.padding(
            start = cardSideMargin,
            end = cardSideMargin,
            bottom = dimensionResource(id = R.dimen.card_bottom_margin)
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(Modifier.fillMaxWidth()) {
            SunflowerImage(
                model = vm.imageUrl,
                contentDescription = plant.plant.description,
                Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.plant_item_image_height)),
                contentScale = ContentScale.Crop
            )

            //Planted date
            Text(
                text = vm.plantName,
                Modifier
                    .padding(vertical = marginNormal)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )

            // Planted date
            Text(
                text = stringResource(id = R.string.plant_date_header),
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = vm.plantDateString,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.labelSmall
            )

            // Last Watered
            Text(
                text = stringResource(id = R.string.watered_date_header),
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = marginNormal),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = vm.waterDateString,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.watering_next,
                    count = vm.wateringInterval,
                    vm.wateringInterval
                ),
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = marginNormal),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun EmptyGarden(onAddPlantClick: () -> Unit, modifier: Modifier = Modifier) {
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.garden_empty),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            shape = MaterialTheme.shapes.medium,
            onClick = onAddPlantClick
        ) {
            Text(
                text = stringResource(id = R.string.add_plant),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
