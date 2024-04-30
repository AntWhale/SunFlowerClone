package com.antwhale.sunflower.compose.gallery

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.paging.compose.LazyPagingItems
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.antwhale.sunflower.R
import com.antwhale.sunflower.data.UnsplashPhoto
import com.antwhale.sunflower.viewmodels.GalleryViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
    onPhotoClick: (UnsplashPhoto) -> Unit,
    onUpClick: () -> Unit
) {
    GalleryScreen(
        plantPictures = viewModel.plantPictures,
        onPhotoClick = onPhotoClick,
        onUpClick = onUpClick
    )
}

@Composable
private fun GalleryScreen(
    plantPictures: Flow<PagingData<UnsplashPhoto>>,
    onPhotoClick: (UnsplashPhoto) -> Unit = {},
    onUpClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            GalleryTopBar(onUpClick = onUpClick)
        }
    ) { padding ->
        val pagingItems: LazyPagingItems<UnsplashPhoto> = plantPictures.collectAsLazyPagingItems()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.card_side_margin))
        ) {
            items(
                count = pagingItems.itemCount,
                key = { index ->
                    val photo = pagingItems[index]
                    "${photo?.id ?: ""}${index}"
                }
            ) { index ->
                val photo = pagingItems[index] ?: return@items
                PhotoListItem(photo = photo) {
                    onPhotoClick(photo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryTopBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.gallery_title))
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}



