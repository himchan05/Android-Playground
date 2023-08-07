package moi.next.starbucksorder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import moi.next.Model.Category
import moi.next.Model.Drink
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
fun NavigationGraph(
    drinksViewModel: DrinksViewModel,
    startRoute: NAV_ROUTE = NAV_ROUTE.IntroView
) {

    val navController = rememberNavController()
    val routeAction = remember(navController) { RouteAction(navController) }

    NavHost(navController, startRoute.routeName) {
        composable(NAV_ROUTE.IntroView.routeName) {
            IntroView(routeAction)
        }
        composable(NAV_ROUTE.MenuView.routeName) {
            MenuView(drinksViewModel, routeAction)
        }
        composable(NAV_ROUTE.DetailView.routeName) {
            DetailView(drinksViewModel.selectedDrink, routeAction)
        }
    }
}

@Composable
fun IntroView(routeAction: RouteAction) {
    val painter = painterResource(id = R.drawable.splash)
    Box() {
        Image(
            painter = painter,
            contentScale = ContentScale.FillBounds,
            contentDescription = ""
        )

        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { routeAction.navTo(NAV_ROUTE.MenuView) },
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
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

    if (categories != null) {
        CardView(drinksViewModel, categories!!, routeAction)
    } else {
        Text("준비중")
    }
}

@Composable
fun CardView(
    drinksViewModel: DrinksViewModel,
    categories: List<Category>,
    routeAction: RouteAction
) {
    val typography = MaterialTheme.typography
    val context = LocalContext.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            item() {
                Text(
                    category.name,
                    style = typography.titleLarge,
                    modifier = Modifier.padding(10.dp)
                )
            }

            itemsIndexed(category.drinks) { index, drink ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                            .clickable {
                                drinksViewModel._selectedDrink.value = drink
                                routeAction.navTo(NAV_ROUTE.DetailView)
                                Toast
                                    .makeText(context, "클릭됨 ${drink.name}", Toast.LENGTH_LONG)
                                    .show()
                            }
                    ) {
                        Text(drink.name, style = typography.titleMedium)
                        Text(drink.price, style = typography.titleMedium)
                    }

                    if (index != category.drinks.size - 1) {
                        Divider(color = Color.Gray.copy(alpha = 0.12f))
                    }
                }
            }
            item {
                Divider(
                    color = Color.Gray.copy(alpha = 0.12f),
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}

@Composable
fun DetailView(drink: LiveData<Drink>, routeAction: RouteAction) {
    val typography = MaterialTheme.typography
    var selectedSize by remember { mutableStateOf(drink.value!!.sizes[0]) }
    val updateSize = { size: String -> selectedSize = size }

    var selectedIce by remember { mutableStateOf("") }
    val updateIce = { ice: String -> selectedIce = ice }

    var selectedBasic by remember { mutableStateOf("") }
    val updateBasic = { basic: String -> selectedBasic = basic }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        IconButton(onClick = { routeAction.goBack() }) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "BackBtn"
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            drink.value?.let {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(it.name, style = typography.titleSmall)
                    Text(it.price, style = typography.headlineSmall)
                    Text(it.description, style = typography.titleMedium)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        it.basic?.let {
                            Text("기본", style = typography.headlineSmall)

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                it.forEach { basic ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(shape = RoundedCornerShape(10.dp))
                                            .clickable { updateBasic(basic) }
                                            .background(
                                                if (basic == selectedBasic) {
                                                    Color.Magenta
                                                } else {
                                                    Color.LightGray
                                                }
                                            )
                                            .padding(vertical = 12.dp, horizontal = 16.dp)
                                    ) {
                                        Text(
                                            basic,
                                            style = typography.titleLarge,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        it.sizes?.let {
                            Text("사이즈", style = typography.headlineSmall)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                it.forEach { size ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(shape = RoundedCornerShape(10.dp))
                                            .clickable { updateSize(size) }
                                            .background(
                                                if (size == selectedSize) {
                                                    Color.Magenta
                                                } else {
                                                    Color.LightGray
                                                }
                                            )
                                            .padding(vertical = 12.dp, horizontal = 16.dp)
                                    ) {
                                        Text(
                                            size,
                                            style = typography.titleLarge,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        it.ice?.let {
                            Text("얼음", style = typography.headlineSmall)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                it.forEach { ice ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(shape = RoundedCornerShape(10.dp))
                                            .clickable { updateIce(ice) }
                                            .background(
                                                if (ice == selectedIce) {
                                                    Color.Magenta
                                                } else {
                                                    Color.LightGray
                                                }
                                            )
                                            .padding(vertical = 12.dp, horizontal = 16.dp)
                                    ) {
                                        Text(
                                            ice,
                                            style = typography.titleLarge,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { routeAction.navTo(NAV_ROUTE.MenuView) },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text("주문하기", style = typography.titleMedium)
            }
        }

    }
}