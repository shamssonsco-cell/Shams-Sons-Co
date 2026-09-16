package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SectionHeader
import com.example.ui.components.ShamsContactInfo
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark
import com.example.ui.viewmodel.ShamsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EstimatorScreen(
  viewModel: ShamsViewModel,
  onProceedToInquiry: (summary: String, budget: String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val state by viewModel.estimatorState.collectAsState()
  val quote = state.calculatedQuote

  val projectTypes = listOf("Commercial Office", "Warehouse / Industrial", "Retail Shop", "Residential Villa", "Data Center")
  val resolutions = listOf("4MP Super HD", "8MP 4K Ultra HD")
  val storageOptions = listOf(15, 30, 60, 90)
  val rackSizes = listOf("9U Wall Mount", "12U Wall Mount", "24U Floor Stand", "42U Server Cabinet")
  val cablingOptions = listOf("Conduit Surface Mounted", "Concealed / In-Ceiling", "Industrial Heavy-Duty Metal")

  val fullBoqText = """
    SHAMS & Sons Co - Project Specification & Quotation
    --------------------------------------------------
    Project Type: ${state.projectType}
    
    1. CCTV SURVEILLANCE SYSTEM:
       - Indoor Dome Cameras: ${state.indoorCameraCount} units
       - Outdoor Perimeter Cameras: ${state.outdoorCameraCount} units
       - Resolution: ${state.cameraResolution}
       - Video Archival Duration: ${state.storageDays} Days NVR Storage
       - Subtotal: $${quote.cctvTotal}
       
    2. ACCESS CONTROL SYSTEM:
       - Secure Access Doors: ${state.accessDoorsCount} doors
       - Identification Method: ${state.accessMethod}
       - Time & Attendance Software: ${if (state.hasTimeAttendance) "Included" else "None"}
       - Subtotal: $${quote.accessControlTotal}
       
    3. IT & NETWORK INFRASTRUCTURE:
       - Cat6 Structured Network Drops: ${state.networkDropsCount} points
       - Server Rack: ${if (state.includeServerRack) state.rackSize else "Not included"}
       - Managed PoE+ Switch: ${if (state.includePoESwitch) "Included" else "None"}
       - Wi-Fi 6 Enterprise APs: ${state.wifiApCount} units
       - Cabling Standard: ${state.cablingType}
       - Subtotal: $${quote.itNetworkTotal}
       
    FINANCIAL SUMMARY:
    - Equipment & Materials: $${quote.materialsTotal}
    - Certified Installation & Fluke Testing: $${quote.installationAndTesting}
    - 1-Year Comprehensive Onsite SLA & Warranty: $${quote.annualSupportWarranty}
    --------------------------------------------------
    ESTIMATED TOTAL: $${quote.grandTotal} USD
    
    Company: SHAMS & Sons Co
    Email: ${ShamsContactInfo.EMAIL}
    Phone / WhatsApp: ${ShamsContactInfo.PHONE}
  """.trimIndent()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(12.dp))
      SectionHeader(
        title = "Smart Project Estimator",
        subtitle = "Configure your CCTV, Access Control & IT requirements for an instant BOQ calculation.",
        icon = Icons.Default.Calculate
      )
    }

    // Project Type Selector
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "Facility / Project Type",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(8.dp))
          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            projectTypes.forEach { type ->
              FilterChip(
                selected = state.projectType == type,
                onClick = { viewModel.updateEstimator { it.copy(projectType = type) } },
                label = { Text(type, fontSize = 12.sp) },
                leadingIcon = if (state.projectType == type) {
                  { Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
              )
            }
          }
        }
      }
    }

    // 1. CCTV Configuration Section
    item {
      SectionCard(
        title = "1. CCTV Camera System",
        icon = Icons.Default.Videocam,
        accentColor = ShamsBluePrimary
      ) {
        // Indoor Cameras
        CounterRow(
          title = "Indoor Dome Cameras",
          subtitle = "Wide-angle ceiling mounted with audio",
          count = state.indoorCameraCount,
          onDecrement = { viewModel.updateEstimator { it.copy(indoorCameraCount = (it.indoorCameraCount - 1).coerceAtLeast(0)) } },
          onIncrement = { viewModel.updateEstimator { it.copy(indoorCameraCount = (it.indoorCameraCount + 1).coerceAtMost(64)) } }
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        // Outdoor Cameras
        CounterRow(
          title = "Outdoor Bullet / PTZ Cameras",
          subtitle = "Weatherproof IP67 with starlight night vision",
          count = state.outdoorCameraCount,
          onDecrement = { viewModel.updateEstimator { it.copy(outdoorCameraCount = (it.outdoorCameraCount - 1).coerceAtLeast(0)) } },
          onIncrement = { viewModel.updateEstimator { it.copy(outdoorCameraCount = (it.outdoorCameraCount + 1).coerceAtMost(64)) } }
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        // Camera Resolution
        Text("Camera Resolution Quality", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          resolutions.forEach { res ->
            FilterChip(
              selected = state.cameraResolution == res,
              onClick = { viewModel.updateEstimator { it.copy(cameraResolution = res) } },
              label = { Text(res, fontSize = 12.sp) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Storage Days
        Text("Video Archival Duration (NVR)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          storageOptions.forEach { days ->
            FilterChip(
              selected = state.storageDays == days,
              onClick = { viewModel.updateEstimator { it.copy(storageDays = days) } },
              label = { Text("$days Days", fontSize = 11.sp) },
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // 2. Access Control Configuration Section
    item {
      SectionCard(
        title = "2. Access Control System",
        icon = Icons.Default.Lock,
        accentColor = ShamsEmerald
      ) {
        CounterRow(
          title = "Protected Access Doors",
          subtitle = "Includes mag-lock, exit sensor & power backup",
          count = state.accessDoorsCount,
          onDecrement = { viewModel.updateEstimator { it.copy(accessDoorsCount = (it.accessDoorsCount - 1).coerceAtLeast(0)) } },
          onIncrement = { viewModel.updateEstimator { it.copy(accessDoorsCount = (it.accessDoorsCount + 1).coerceAtMost(32)) } }
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        Text("Authentication Method", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        val authMethods = listOf("Biometric Face & Fingerprint", "RFID Smart Card & PIN")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          authMethods.forEach { method ->
            FilterChip(
              selected = state.accessMethod == method,
              onClick = { viewModel.updateEstimator { it.copy(accessMethod = method) } },
              label = { Text(method, fontSize = 11.sp) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text("Time & Attendance Software", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
            Text("Automated staff check-in & payroll export", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Switch(
            checked = state.hasTimeAttendance,
            onCheckedChange = { checked -> viewModel.updateEstimator { it.copy(hasTimeAttendance = checked) } },
            colors = SwitchDefaults.colors(checkedThumbColor = ShamsEmerald)
          )
        }
      }
    }

    // 3. IT & Structured Cabling Section
    item {
      SectionCard(
        title = "3. IT & Network Infrastructure",
        icon = Icons.Default.Router,
        accentColor = ShamsBlueAccent
      ) {
        CounterRow(
          title = "Cat6 Structured Drops",
          subtitle = "Workstation and network data outlets",
          count = state.networkDropsCount,
          step = 4,
          onDecrement = { viewModel.updateEstimator { it.copy(networkDropsCount = (it.networkDropsCount - 4).coerceAtLeast(0)) } },
          onIncrement = { viewModel.updateEstimator { it.copy(networkDropsCount = (it.networkDropsCount + 4).coerceAtMost(128)) } }
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        CounterRow(
          title = "Wi-Fi 6 Enterprise APs",
          subtitle = "High-density ceiling access points",
          count = state.wifiApCount,
          onDecrement = { viewModel.updateEstimator { it.copy(wifiApCount = (it.wifiApCount - 1).coerceAtLeast(0)) } },
          onIncrement = { viewModel.updateEstimator { it.copy(wifiApCount = (it.wifiApCount + 1).coerceAtMost(16)) } }
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text("Server Rack Cabinet", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
            Text("With PDU, patch panel and cable organizers", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Switch(
            checked = state.includeServerRack,
            onCheckedChange = { checked -> viewModel.updateEstimator { it.copy(includeServerRack = checked) } }
          )
        }

        if (state.includeServerRack) {
          Spacer(modifier = Modifier.height(8.dp))
          FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            rackSizes.forEach { size ->
              FilterChip(
                selected = state.rackSize == size,
                onClick = { viewModel.updateEstimator { it.copy(rackSize = size) } },
                label = { Text(size, fontSize = 11.sp) }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Cabling Route & Containment", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          cablingOptions.forEach { opt ->
            FilterChip(
              selected = state.cablingType == opt,
              onClick = { viewModel.updateEstimator { it.copy(cablingType = opt) } },
              label = { Text(opt, fontSize = 11.sp) }
            )
          }
        }
      }
    }

    // Live Quotation Breakdown Summary
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text("ITEMIZED ESTIMATION", color = ShamsBlueAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
              Text("Project Estimate Breakdown", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            IconButton(
              onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("SHAMS Quote", fullBoqText)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Full Quote BOQ copied to clipboard!", Toast.LENGTH_SHORT).show()
              }
            ) {
              Icon(Icons.Default.ContentCopy, contentDescription = "Copy Quote", tint = ShamsBlueAccent)
            }
          }

          Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFF334155))

          QuoteRow(label = "CCTV Cameras & NVR Hardware", value = "$${quote.cctvTotal}")
          QuoteRow(label = "Access Control & Door Automation", value = "$${quote.accessControlTotal}")
          QuoteRow(label = "IT, Structured Cabling & Switching", value = "$${quote.itNetworkTotal}")
          QuoteRow(label = "Certified Installation & Fluke Testing", value = "$${quote.installationAndTesting}", isHighlight = true)
          QuoteRow(label = "1-Year Onsite Warranty & SLA", value = "$${quote.annualSupportWarranty}", isHighlight = true)

          Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFF334155))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text("Estimated Total (Turnkey)", color = Color(0xFF94A3B8), fontSize = 12.sp)
              Text("Inclusive of Labor & Hardware", color = ShamsEmerald, fontSize = 11.sp)
            }
            Text(
              text = "$${quote.grandTotal} USD",
              color = Color.White,
              fontSize = 24.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Primary Actions
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = {
                onProceedToInquiry(fullBoqText, "$${quote.grandTotal}")
              },
              colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("estimator_book_survey_btn")
            ) {
              Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Submit for Survey", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
              onClick = {
                ShamsContactInfo.launchEmail(
                  context = context,
                  subject = "Quotation Request: ${state.projectType} ($${quote.grandTotal})",
                  body = fullBoqText
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("estimator_email_btn")
            ) {
              Icon(Icons.Default.Email, contentDescription = null, tint = ShamsEmerald, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Email to SHAMS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun SectionCard(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  accentColor: Color,
  content: @Composable () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(14.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(accentColor.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(14.dp))
      content()
    }
  }
}

@Composable
private fun CounterRow(
  title: String,
  subtitle: String,
  count: Int,
  step: Int = 1,
  onDecrement: () -> Unit,
  onIncrement: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
      Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
      IconButton(
        onClick = onDecrement,
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
      }

      Text(
        text = "$count",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 14.dp)
      )

      IconButton(
        onClick = onIncrement,
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
      ) {
        Icon(Icons.Default.Add, contentDescription = "Increase", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
      }
    }
  }
}

@Composable
private fun QuoteRow(label: String, value: String, isHighlight: Boolean = false) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      color = if (isHighlight) ShamsBlueAccent else Color(0xFFCBD5E1),
      fontSize = 13.sp
    )
    Text(
      text = value,
      color = Color.White,
      fontWeight = FontWeight.SemiBold,
      fontSize = 13.sp
    )
  }
}
