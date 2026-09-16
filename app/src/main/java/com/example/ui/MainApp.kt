package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.ShamsAppHeader
import com.example.ui.screens.AboutContactScreen
import com.example.ui.screens.EstimatorScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InquiriesScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsNavyDark
import com.example.ui.viewmodel.ShamsViewModel

enum class AppTab(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String
) {
  HOME("Solutions", Icons.Filled.Home, Icons.Outlined.Home, "tab_home"),
  ESTIMATOR("Estimator", Icons.Filled.Calculate, Icons.Outlined.Calculate, "tab_estimator"),
  TOOLS("Tools", Icons.Filled.Build, Icons.Outlined.Build, "tab_tools"),
  INQUIRIES("Inquiries", Icons.Filled.Assignment, Icons.Outlined.Assignment, "tab_inquiries"),
  CONTACT("About & Contact", Icons.Filled.Info, Icons.Outlined.Info, "tab_contact")
}

@Composable
fun MainApp(
  viewModel: ShamsViewModel = viewModel()
) {
  var selectedTab by remember { mutableStateOf(AppTab.HOME) }
  var passedDetails by remember { mutableStateOf("") }
  var passedBudget by remember { mutableStateOf("") }

  Scaffold(
    topBar = {
      ShamsAppHeader()
    },
    bottomBar = {
      NavigationBar(
        containerColor = ShamsNavyDark,
        contentColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier
          .windowInsetsPadding(WindowInsets.navigationBars)
          .testTag("main_navigation_bar")
      ) {
        AppTab.values().forEach { tab ->
          val isSelected = selectedTab == tab
          NavigationBarItem(
            selected = isSelected,
            onClick = { selectedTab = tab },
            icon = {
              Icon(
                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                contentDescription = tab.title
              )
            },
            label = {
              Text(
                text = tab.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Color.White,
              selectedTextColor = ShamsBlueAccent,
              indicatorColor = ShamsBluePrimary,
              unselectedIconColor = Color(0xFF94A3B8),
              unselectedTextColor = Color(0xFF94A3B8)
            ),
            modifier = Modifier.testTag(tab.testTag)
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Crossfade(targetState = selectedTab, label = "tab_crossfade") { tab ->
        when (tab) {
          AppTab.HOME -> HomeScreen(
            onNavigateToEstimator = { selectedTab = AppTab.ESTIMATOR },
            onNavigateToInquiries = { selectedTab = AppTab.INQUIRIES },
            onNavigateToTools = { selectedTab = AppTab.TOOLS }
          )

          AppTab.ESTIMATOR -> EstimatorScreen(
            viewModel = viewModel,
            onProceedToInquiry = { boqText, budget ->
              passedDetails = boqText
              passedBudget = budget
              selectedTab = AppTab.INQUIRIES
            }
          )

          AppTab.TOOLS -> ToolsScreen(viewModel = viewModel)

          AppTab.INQUIRIES -> InquiriesScreen(
            viewModel = viewModel,
            initialDetails = passedDetails,
            initialBudget = passedBudget
          )

          AppTab.CONTACT -> AboutContactScreen()
        }
      }
    }
  }
}
