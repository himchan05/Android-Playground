package moi.next.Utill

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

// 네비게이션 라우트 enum
enum class NAV_ROUTE(val routeName: String, val desc: String, val btnColor: Color) {
    IntroView("Intro", "인트로 화면", Color(0xFF673AB7)),
    MenuView("Menu", "메뉴 리스트 화면", Color(0xFF2196F3)),
    DetailView("Detail", "메뉴 상세 화면", Color(0xFF629C1F)),
    OrderView("Order", "주문 완료 화면", Color(0xFFFFEB3B))
}

// 네비게이션 라우트 action
class RouteAction(navHostController: NavHostController) {
    // 특정 라우트로 이동
    val navTo: (NAV_ROUTE) -> Unit = { route ->
        navHostController.navigate(route.routeName)
    }
    // 뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}