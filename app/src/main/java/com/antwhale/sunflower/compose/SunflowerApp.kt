package com.antwhale.sunflower.compose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antwhale.sunflower.R
import com.antwhale.sunflower.compose.gallery.GalleryScreen
import com.antwhale.sunflower.compose.home.HomeScreen
import com.antwhale.sunflower.compose.plantdetail.PlantDetailsScreen

@Composable
fun SunflowerApp() {
    val navController = rememberNavController()
    SunFlowerNavHost(
        navController = navController
    )
}

@Composable
fun SunFlowerNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPlantClick = {
                    navController.navigate("plantDetail/${it.plantId}")
                }
            )
        }
        composable(
            "plantDetail/{plantId}",
            arguments = listOf(navArgument("plantId"){
                type = NavType.StringType
            })
        ){
            PlantDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onShareClick = { createShareIntent(activity, it) },
                onGalleryClick = { navController.navigate("gallery/{it.name") }
            )
        }
        composable(
            "gallery/{plantName}",
            arguments = listOf(navArgument("plantName"){
                type = NavType.StringType
            })
        ){
            GalleryScreen(
                onPhotoClick = {
                    val uri = Uri.parse(it.user.attributionUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    activity.startActivity(intent)
                },
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}

//Helper function for calling a share functionality.
//Should be used when user presses a share button/menu item.
private fun createShareIntent(activity: Activity, plantName: String) {
    val shareText = activity.getString(R.string.share_text_plant, plantName)
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}
