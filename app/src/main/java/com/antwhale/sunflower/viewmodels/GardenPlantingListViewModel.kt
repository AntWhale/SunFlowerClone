package com.antwhale.sunflower.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.antwhale.sunflower.data.GardenPlantingRepository
import com.antwhale.sunflower.data.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository : GardenPlantingRepository
) : ViewModel() {
    val plantAndGardenPlantings : Flow<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}