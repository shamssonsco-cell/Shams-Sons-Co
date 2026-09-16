package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SectionHeader
import com.example.ui.theme.ShamsAmber
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark
import com.example.ui.theme.ShamsRose
import com.example.ui.viewmodel.ShamsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ToolsScreen(
  viewModel: ShamsViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val toolTabs = listOf("CCTV Storage", "Lens FOV", "Access Simulator", "Cable Tester")

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    SectionHeader(
      title = "Engineering Diagnostics",
      subtitle = "Interactive tools for system architects, installers & security technicians.",
      icon = Icons.Default.Build,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
    )

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.primary,
      modifier = Modifier.fillMaxWidth()
    ) {
      toolTabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = { Text(title, fontSize = 12.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) },
          modifier = Modifier.testTag("tool_tab_$index")
        )
      }
    }

    when (selectedTab) {
      0 -> StorageCalculatorTab(viewModel)
      1 -> LensFovSimulatorTab(viewModel)
      2 -> AccessControlSimulatorTab(viewModel)
      3 -> CablePinoutTesterTab()
    }
  }
}

// -------------------------------------------------------------
// TAB 1: CCTV Storage & Bandwidth Calculator
// -------------------------------------------------------------
@Composable
private fun StorageCalculatorTab(viewModel: ShamsViewModel) {
  val camCount by viewModel.calcCamCount.collectAsState()
  val resolution by viewModel.calcResolution.collectAsState()
  val days by viewModel.calcDays.collectAsState()
  val codec by viewModel.calcCodec.collectAsState()

  val storageTB = viewModel.calculatedStorageTB
  val bandwidthMbps = viewModel.calculatedBandwidthMbps

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text("CALCULATED REQUIREMENT", color = ShamsBlueAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("Total NVR Storage", color = Color(0xFF94A3B8), fontSize = 12.sp)
              Text(
                text = "${String.format("%.2f", storageTB)} TB",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Rec: ${if (storageTB <= 4) "1x 4TB" else if (storageTB <= 8) "2x 4TB" else if (storageTB <= 16) "2x 8TB" else "4x 8TB"} Surveillance HDD",
                color = ShamsEmerald,
                fontSize = 11.sp
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text("Network Bandwidth", color = Color(0xFF94A3B8), fontSize = 12.sp)
              Text(
                text = "${String.format("%.1f", bandwidthMbps)} Mbps",
                color = ShamsBlueAccent,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Min: 1Gbps PoE Switch",
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(14.dp)) }

    // Sliders & Controls
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Camera Count
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Total IP Cameras", fontWeight = FontWeight.SemiBold)
            Text("$camCount Cameras", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
          }
          Slider(
            value = camCount.toFloat(),
            onValueChange = { viewModel.setCalcCamCount(it.toInt()) },
            valueRange = 1f..64f,
            steps = 63,
            colors = SliderDefaults.colors(thumbColor = ShamsBluePrimary, activeTrackColor = ShamsBluePrimary)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Archival Days
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Retention Duration", fontWeight = FontWeight.SemiBold)
            Text("$days Days", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
          }
          Slider(
            value = days.toFloat(),
            onValueChange = { viewModel.setCalcDays(it.toInt()) },
            valueRange = 7f..90f,
            steps = 82,
            colors = SliderDefaults.colors(thumbColor = ShamsBluePrimary, activeTrackColor = ShamsBluePrimary)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Resolution
          Text("Video Stream Resolution", fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(6.dp))
          val resolutions = listOf("2MP 1080P Full HD", "4MP (2560x1440)", "8MP 4K Ultra HD")
          resolutions.forEach { res ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              FilterChip(
                selected = resolution == res,
                onClick = { viewModel.setCalcResolution(res) },
                label = { Text(res, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth()
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Compression Codec
          Text("Video Compression Codec", fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(6.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("H.265 (High Efficiency)", "H.264 (Standard)").forEach { cod ->
              FilterChip(
                selected = codec == cod,
                onClick = { viewModel.setCalcCodec(cod) },
                label = { Text(cod, fontSize = 11.sp) },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// TAB 2: Camera Lens & Field of View (FOV) Simulator
// -------------------------------------------------------------
@Composable
private fun LensFovSimulatorTab(viewModel: ShamsViewModel) {
  val selectedFocal by viewModel.selectedFocalLength.collectAsState()

  val focalOptions = listOf(2.8, 4.0, 6.0, 12.0)
  val (fovAngle, doriDetect, doriRecognize, doriIdentify) = when (selectedFocal) {
    2.8 -> Quadruple(105, 42, 17, 4)
    4.0 -> Quadruple(84, 63, 25, 6)
    6.0 -> Quadruple(55, 95, 38, 9)
    else -> Quadruple(28, 190, 76, 19)
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    item {
      // Visual Arc Canvas
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .height(240.dp),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Box(modifier = Modifier.fillMaxSize()) {
          Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val centerX = size.width / 2f
            val cameraY = size.height * 0.85f

            // Draw Camera Icon base
            drawCircle(
              color = Color.White,
              radius = 10f,
              center = Offset(centerX, cameraY)
            )

            // Draw FOV cone
            val radius = size.height * 0.72f
            val halfAngleRad = Math.toRadians((fovAngle / 2.0)).toFloat()

            val leftX = centerX - radius * sin(halfAngleRad)
            val leftY = cameraY - radius * cos(halfAngleRad)

            val rightX = centerX + radius * sin(halfAngleRad)
            val rightY = cameraY - radius * cos(halfAngleRad)

            // Outer detection arc
            drawArc(
              color = ShamsBlueAccent.copy(alpha = 0.35f),
              startAngle = 270f - (fovAngle / 2f),
              sweepAngle = fovAngle.toFloat(),
              useCenter = true,
              topLeft = Offset(centerX - radius, cameraY - radius),
              size = Size(radius * 2, radius * 2)
            )

            // Recognition arc (closer)
            val recRadius = radius * 0.45f
            drawArc(
              color = ShamsEmerald.copy(alpha = 0.5f),
              startAngle = 270f - (fovAngle / 2f),
              sweepAngle = fovAngle.toFloat(),
              useCenter = true,
              topLeft = Offset(centerX - recRadius, cameraY - recRadius),
              size = Size(recRadius * 2, recRadius * 2)
            )

            // Guidelines
            drawLine(Color.White.copy(alpha = 0.8f), Offset(centerX, cameraY), Offset(leftX, leftY), strokeWidth = 2f)
            drawLine(Color.White.copy(alpha = 0.8f), Offset(centerX, cameraY), Offset(rightX, rightY), strokeWidth = 2f)
          }

          Column(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(14.dp)
          ) {
            Text("LENS COVERAGE CONE", color = ShamsBlueAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text("${selectedFocal}mm Lens ($fovAngle° FOV)", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          }

          Row(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            FovLegendChip(label = "Detect", color = ShamsBlueAccent.copy(alpha = 0.7f))
            FovLegendChip(label = "Recognize", color = ShamsEmerald)
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(14.dp)) }

    // Lens selector chips
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Select Camera Focal Length", fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(8.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            focalOptions.forEach { focal ->
              FilterChip(
                selected = selectedFocal == focal,
                onClick = { viewModel.setSelectedFocalLength(focal) },
                label = { Text("${focal}mm", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text("DORI Standard Distance Ratings (EN-62676-4):", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))

          DoriRow(type = "Detection (25 px/m)", distance = "$doriDetect meters", desc = "Notice presence of an individual or vehicle")
          DoriRow(type = "Observation (63 px/m)", distance = "${(doriDetect * 0.4).toInt()} meters", desc = "View characteristic details like clothing color")
          DoriRow(type = "Recognition (125 px/m)", distance = "$doriRecognize meters", desc = "Determine with high certainty if known person")
          DoriRow(type = "Identification (250 px/m)", distance = "$doriIdentify meters", desc = "Positive facial identification beyond doubt")
        }
      }
    }
  }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
private fun FovLegendChip(label: String, color: Color) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
    Spacer(modifier = Modifier.width(4.dp))
    Text(label, color = Color.White, fontSize = 10.sp)
  }
}

@Composable
private fun DoriRow(type: String, distance: String, desc: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(type, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
      Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Text(distance, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
  }
}

// -------------------------------------------------------------
// TAB 3: Access Control Door Relay & Simulator
// -------------------------------------------------------------
@Composable
private fun AccessControlSimulatorTab(viewModel: ShamsViewModel) {
  val doorStatus by viewModel.doorStatus.collectAsState()
  val isUnlocked by viewModel.isDoorUnlocked.collectAsState()
  val logs by viewModel.accessLogs.collectAsState()

  val statusColor by animateColorAsState(
    targetValue = if (isUnlocked) ShamsEmerald else ShamsRose,
    animationSpec = tween(300),
    label = "door_color"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Door State Panel
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("DOOR LOCK RELAY: MAIN ENTRANCE", color = ShamsBlueAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Box(
              modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(statusColor)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .background(statusColor.copy(alpha = 0.2f))
              .border(2.dp, statusColor, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
              contentDescription = null,
              tint = statusColor,
              modifier = Modifier.size(40.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = doorStatus,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )

          Text(
            text = if (isUnlocked) "Maglock Holding Force Released (0 lbs)" else "Holding Force Active (1,200 lbs)",
            color = Color(0xFF94A3B8),
            fontSize = 12.sp
          )
        }
      }
    }

    item { Spacer(modifier = Modifier.height(14.dp)) }

    // Test Actions
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Interactive Credentials Testing", fontWeight = FontWeight.Bold)
          Text("Simulate live biometric and card credential scans", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

          Spacer(modifier = Modifier.height(12.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = { viewModel.triggerBadgeSwipe(true, "Eng. Shams Al-Din (Staff #01)", "3D Face ID") },
              colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Valid Face ID", fontSize = 11.sp)
            }

            Button(
              onClick = { viewModel.triggerBadgeSwipe(true, "Operations Mgr (Badge #402)", "RFID Card") },
              colors = ButtonDefaults.buttonColors(containerColor = ShamsEmerald),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Fingerprint, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Valid RFID", fontSize = 11.sp)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
              onClick = { viewModel.triggerBadgeSwipe(false, "Unregistered Card #909", "RFID Card") },
              colors = ButtonDefaults.outlinedButtonColors(contentColor = ShamsRose),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Invalid Card", fontSize = 11.sp)
            }

            Button(
              onClick = { viewModel.triggerEmergencyBreakGlass() },
              colors = ButtonDefaults.buttonColors(containerColor = ShamsRose),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.CrisisAlert, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Break Glass", fontSize = 11.sp)
            }
          }

          if (isUnlocked) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
              onClick = { viewModel.resetDoorLock() },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Reset Lock Immediately")
            }
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(14.dp)) }

    // Live Event Access Logs
    item {
      Text("Real-Time Access Control Logs", fontWeight = FontWeight.Bold, fontSize = 14.sp)
      Spacer(modifier = Modifier.height(6.dp))
    }

    items(logs) { log ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (log.isGranted) ShamsEmerald else ShamsRose)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(log.user, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
              Text("${log.credentialType} • ${log.time}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
            }
          }
          Text(
            text = log.status,
            color = if (log.isGranted) ShamsEmerald else ShamsRose,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------
// TAB 4: Network Structured Cabling RJ45 Pinout & Continuity Tester
// -------------------------------------------------------------
@Composable
private fun CablePinoutTesterTab() {
  var standard by remember { mutableStateOf("T568B (Standard Commercial)") }
  var isTesting by remember { mutableStateOf(false) }
  var currentActivePin by remember { mutableIntStateOf(-1) }
  val coroutineScope = rememberCoroutineScope()

  val pins = if (standard.contains("T568B")) {
    listOf(
      Pair(1, "White / Orange"),
      Pair(2, "Orange"),
      Pair(3, "White / Green"),
      Pair(4, "Blue"),
      Pair(5, "White / Blue"),
      Pair(6, "Green"),
      Pair(7, "White / Brown"),
      Pair(8, "Brown")
    )
  } else {
    listOf(
      Pair(1, "White / Green"),
      Pair(2, "Green"),
      Pair(3, "White / Orange"),
      Pair(4, "Blue"),
      Pair(5, "White / Blue"),
      Pair(6, "Orange"),
      Pair(7, "White / Brown"),
      Pair(8, "Brown")
    )
  }

  fun getColorForPin(name: String): Color {
    return when {
      name == "Orange" -> Color(0xFFF97316)
      name.contains("Orange") -> Color(0xFFFED7AA)
      name == "Green" -> Color(0xFF22C55E)
      name.contains("Green") -> Color(0xFFBBF7D0)
      name == "Blue" -> Color(0xFF3B82F6)
      name.contains("Blue") -> Color(0xFFBFDBFE)
      name == "Brown" -> Color(0xFF854D0E)
      name.contains("Brown") -> Color(0xFFE5D5C5)
      else -> Color.Gray
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("FLUKE CONTINUITY TESTER", color = ShamsBlueAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Text("RJ45 Cat6/Cat6A Pinout Reference", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)

          Spacer(modifier = Modifier.height(10.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("T568B (Standard Commercial)", "T568A (Government / Telco)").forEach { std ->
              FilterChip(
                selected = standard == std,
                onClick = { standard = std },
                label = { Text(std.take(5), fontSize = 11.sp) },
                modifier = Modifier.weight(1f)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 8 LED Pins in row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            pins.forEach { (num, _) ->
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                  modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                      if (currentActivePin == num) ShamsEmerald
                      else if (isTesting && currentActivePin > num) ShamsEmerald.copy(alpha = 0.5f)
                      else Color(0xFF334155)
                    )
                    .border(
                      1.dp,
                      if (currentActivePin == num) Color.White else Color.Transparent,
                      CircleShape
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  Text("$num", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("P$num", fontSize = 9.sp, color = Color(0xFF94A3B8))
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              if (!isTesting) {
                isTesting = true
                coroutineScope.launch {
                  for (i in 1..8) {
                    currentActivePin = i
                    delay(300)
                  }
                  delay(400)
                  isTesting = false
                  currentActivePin = -1
                }
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(if (isTesting) "Testing Continuity (Pins 1-8)..." else "Run Cable Continuity Test")
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(14.dp)) }

    item {
      Text("Pin-by-Pin Wire Color Map ($standard)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
      Spacer(modifier = Modifier.height(6.dp))
    }

    items(pins) { (pinNumber, colorName) ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(getColorForPin(colorName))
                .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Pin $pinNumber", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          Text(colorName, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        }
      }
    }
  }
}
