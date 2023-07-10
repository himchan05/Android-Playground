package moi.next.starbucksorder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import moi.next.Model.DrinksViewModel
import moi.next.Utill.NAV_ROUTE
import moi.next.Utill.RouteAction

class MainActivity : ComponentActivity() {
    private val drinksViewModel: DrinksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationGraph(drinksViewModel)
        }
    }
}

@Composable
fun NavigationGraph(drinksViewModel: DrinksViewModel,
                    startRoute: NAV_ROUTE = NAV_ROUTE.IntroView) {

    val navController = rememberNavController()
    val routeAction = remember(navController) { RouteAction(navController) }

    NavHost(navController, startRoute.routeName) {
        composable(NAV_ROUTE.IntroView.routeName) {
            IntroView(routeAction = routeAction)
        }
        composable(NAV_ROUTE.MenuView.routeName) {
            MenuView(drinksViewModel, routeAction = routeAction)
        }
    }
}

@Composable
fun IntroView(routeAction: RouteAction) {
    val painter = painterResource(id = R.drawable.splash)
    Box() {
        Image(painter = painter,
            contentScale = ContentScale.FillBounds,
            contentDescription = "")

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = { routeAction.navTo(NAV_ROUTE.MenuView) },
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                Text("다음")
            }
        }
    }
}

@Composable
fun MenuView(drinksViewModel: DrinksViewModel, routeAction: RouteAction) {
    val categories by drinksViewModel.categories.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        drinksViewModel.fetchDrinks(context)
        Log.e("text", categories.toString())
    }

    Column() {
        Text("hi")

        Button(onClick = { routeAction.goBack() }) {
            Text("Back")
        }

        if (categories != null) {
            LazyColumn {
                items(categories!!) { category ->
                    Text(category.name)
                }
            }
        } else {
            Text("준비중")
        }


    }
}