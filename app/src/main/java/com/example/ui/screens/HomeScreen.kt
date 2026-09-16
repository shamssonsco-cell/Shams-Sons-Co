package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ServiceCategoryItem
import com.example.data.model.ShamsServicesData
import com.example.ui.components.SectionHeader
import com.example.ui.components.ShamsContactInfo
import com.example.ui.theme.ShamsAmber
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsCobalt
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark

@Composable
fun HomeScreen(
  onNavigateToEstimator: () -> Unit,
  onNavigateToInquiries: () -> Unit,
  onNavigateToTools: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Hero Banner
    item {
      HeroHeaderSection(
        onGetQuote = onNavigateToEstimator,
        onRequestSurvey = onNavigateToInquiries
      )
    }

    // Quick Stats Bar
    item {
      StatsBanner()
    }

    // Quick Action Cards
    item {
      QuickActionsRow(
        onGetQuote = onNavigateToEstimator,
        onRequestSurvey = onNavigateToInquiries,
        onDiagnostics = onNavigateToTools
      )
    }

    // Three Core Pillars Header
    item {
      Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        SectionHeader(
          title = "Complete Professional Solutions",
          subtitle = "Engineered, installed, and certified to international standards by SHAMS & Sons Co.",
          icon = Icons.Default.Verified
        )
      }
    }

    // Three Core Service Pillars
    items(ShamsServicesData.pillars) { service ->
      ServicePillarCard(
        service = service,
        onBookService = onNavigateToInquiries,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
      )
    }

    // Why Choose SHAMS Section
    item {
      WhyChooseShamsSection(modifier = Modifier.padding(16.dp))
    }

    // Trusted Brands & Partners
    item {
      CertifiedBrandsSection(modifier = Modifier.padding(16.dp))
    }

    // Emergency Contact Banner
    item {
      DirectContactBanner(
        onCall = { ShamsContactInfo.launchDialer(context) },
        onWhatsApp = { ShamsContactInfo.launchWhatsApp(context) },
        onEmail = { ShamsContactInfo.launchEmail(context) },
        modifier = Modifier.padding(16.dp)
      )
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun HeroHeaderSection(
  onGetQuote: () -> Unit,
  onRequestSurvey: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(290.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.img_hero_security),
      contentDescription = "SHAMS Security & IT Command Center",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )

    // Dark gradient overlay for clear contrast
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0x990A0F1D),
              Color(0xCC0A0F1D),
              Color(0xF00A0F1D)
            )
          )
        )
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
      verticalArrangement = Arrangement.Bottom
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(ShamsBluePrimary.copy(alpha = 0.25f))
          .border(1.dp, ShamsBlueAccent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Security,
          contentDescription = null,
          tint = ShamsBlueAccent,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "ENTERPRISE SECURITY & IT INFRASTRUCTURE",
          color = Color.White,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "SHAMS & Sons Co",
        style = MaterialTheme.typography.displayLarge.copy(fontSize = 28.sp),
        color = Color.White,
        fontWeight = FontWeight.ExtraBold
      )

      Text(
        text = "IT Infrastructure • CCTV Camera Installation • Biometric Access Control",
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFFCBD5E1),
        lineHeight = 20.sp,
        modifier = Modifier.padding(vertical = 6.dp)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onGetQuote,
          colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("hero_quote_btn")
        ) {
          Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Instant Estimator", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }

        OutlinedButton(
          onClick = onRequestSurvey,
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
          border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.linearGradient(listOf(ShamsBlueAccent, Color.White))
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("hero_survey_btn")
        ) {
          Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Book Site Survey", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
      }
    }
  }
}

@Composable
private fun StatsBanner() {
  Surface(
    color = ShamsNavyDark,
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      StatItem(value = "1,200+", label = "Projects Done")
      StatDivider()
      StatItem(value = "99.9%", label = "System Uptime")
      StatDivider()
      StatItem(value = "24/7", label = "Tech Support")
      StatDivider()
      StatItem(value = "100%", label = "Certified SLA")
    }
  }
}

@Composable
private fun StatItem(value: String, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      color = ShamsBlueAccent,
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = label,
      color = Color(0xFF94A3B8),
      fontSize = 10.sp
    )
  }
}

@Composable
private fun StatDivider() {
  Box(
    modifier = Modifier
      .height(24.dp)
      .width(1.dp)
      .background(Color(0xFF334155))
  )
}

@Composable
private fun QuickActionsRow(
  onGetQuote: () -> Unit,
  onRequestSurvey: () -> Unit,
  onDiagnostics: () -> Unit
) {
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Text(
      text = "Quick Services Hub",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      QuickActionCard(
        title = "Calculate Estimate",
        subtitle = "CCTV & IT pricing",
        icon = Icons.Default.Calculate,
        accentColor = ShamsBluePrimary,
        onClick = onGetQuote,
        modifier = Modifier.weight(1f)
      )

      QuickActionCard(
        title = "Diagnostic Tools",
        subtitle = "Storage & Lens FOV",
        icon = Icons.Default.Speed,
        accentColor = ShamsEmerald,
        onClick = onDiagnostics,
        modifier = Modifier.weight(1f)
      )

      QuickActionCard(
        title = "Emergency Desk",
        subtitle = "Call 24/7 Engineer",
        icon = Icons.Default.Call,
        accentColor = ShamsAmber,
        onClick = { ShamsContactInfo.launchDialer(context) },
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun QuickActionCard(
  title: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  accentColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .testTag("quick_action_${title.lowercase().replace(" ", "_")}"),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(accentColor.copy(alpha = 0.4f), Color.Transparent)))
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      horizontalAlignment = Alignment.Start
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 11.sp
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ServicePillarCard(
  service: ServiceCategoryItem,
  onBookService: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(false) }

  ElevatedCard(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .testTag("service_card_${service.id}"),
    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
  ) {
    Column {
      // Image Header with overlay
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = service.imageRes),
          contentDescription = service.title,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color(0xCC0B132B), Color(0xF00B132B))
              )
            )
        )

        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(14.dp)
        ) {
          Text(
            text = service.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = service.subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = ShamsBlueAccent
          )
        }
      }

      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = service.description,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Key Technical Highlights:",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        service.keyHighlights.forEach { highlight ->
          Row(
            modifier = Modifier.padding(vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = ShamsEmerald,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = highlight,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        // Expandable Sub-services
        AnimatedVisibility(visible = isExpanded) {
          Column(modifier = Modifier.padding(top = 14.dp)) {
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "Detailed Equipment & Deployment Options:",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            service.subServices.forEach { sub ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(10.dp)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Text(
                    text = sub.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = sub.summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                  Spacer(modifier = Modifier.height(6.dp))

                  FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    sub.specs.forEach { spec ->
                      Box(
                        modifier = Modifier
                          .clip(RoundedCornerShape(6.dp))
                          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                          .padding(horizontal = 6.dp, vertical = 2.dp)
                      ) {
                        Text(
                          text = spec,
                          fontSize = 11.sp,
                          color = MaterialTheme.colorScheme.primary,
                          fontWeight = FontWeight.Medium
                        )
                      }
                    }
                  }

                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Deployment: ${sub.typicalDeployment}",
                    fontSize = 11.sp,
                    color = ShamsEmerald,
                    fontWeight = FontWeight.Medium
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedButton(
            onClick = { isExpanded = !isExpanded },
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(if (isExpanded) "Hide Details" else "View Specs")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
              imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
          }

          Button(
            onClick = onBookService,
            colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text("Inquire Solution")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
          }
        }
      }
    }
  }
}

@Composable
private fun WhyChooseShamsSection(modifier: Modifier = Modifier) {
  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
    shape = RoundedCornerShape(16.dp)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = ShamsAmber,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Why SHAMS & Sons Co?",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      WhyPoint(
        title = "Fluke Certified Copper & Fiber Testing",
        desc = "Every structured cable run is certified with Fluke testers guaranteeing Gigabit and 10Gbps throughput."
      )
      WhyPoint(
        title = "Licensed Security & Surveillance Engineers",
        desc = "Authorized technicians ensuring full local regulatory compliance, data privacy, and tamper protection."
      )
      WhyPoint(
        title = "1-Year Onsite Hardware & Cabling SLA",
        desc = "Dedicated fast-response team for preventative maintenance, emergency firmware patching, and replacement."
      )
      WhyPoint(
        title = "Scalable Architecture",
        desc = "Built with growth in mind, from 4-camera small business setups to multi-building corporate campuses."
      )
    }
  }
}

@Composable
private fun WhyPoint(title: String, desc: String) {
  Row(
    modifier = Modifier.padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(8.dp)
        .padding(top = 6.dp)
        .clip(CircleShape)
        .background(ShamsBlueAccent)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
      )
      Text(
        text = desc,
        color = Color(0xFF94A3B8),
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }
  }
}

@Composable
private fun CertifiedBrandsSection(modifier: Modifier = Modifier) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = "Enterprise Hardware Ecosystem",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = "We deploy tier-1 industrial brands guaranteed for 24/7 reliability",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(10.dp))

    val brands = listOf(
      "Hikvision", "Dahua", "Uniview", "ZKTeco",
      "Suprema", "Cisco", "Ubiquiti UniFi", "Schneider Electric"
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      brands.take(4).forEach { brand ->
        BrandChip(brand, modifier = Modifier.weight(1f))
      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      brands.takeLast(4).forEach { brand ->
        BrandChip(brand, modifier = Modifier.weight(1f))
      }
    }
  }
}

@Composable
private fun BrandChip(brand: String, modifier: Modifier = Modifier) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceVariant,
    tonalElevation = 1.dp
  ) {
    Box(
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = brand,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1
      )
    }
  }
}

@Composable
private fun DirectContactBanner(
  onCall: () -> Unit,
  onWhatsApp: () -> Unit,
  onEmail: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    color = ShamsBluePrimary,
    shadowElevation = 3.dp
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Ready to Secure Your Facility?",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Text(
        text = "Direct Hotline & WhatsApp: 0321-2336901\nSpeak directly with a SHAMS & Sons Co project engineer.",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White.copy(alpha = 0.95f),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(vertical = 6.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onCall,
          colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = ShamsBluePrimary),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1.2f)
        ) {
          Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("0321-2336901", fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }

        Button(
          onClick = onWhatsApp,
          colors = ButtonDefaults.buttonColors(containerColor = ShamsEmerald, contentColor = Color.White),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }

        Button(
          onClick = onEmail,
          colors = ButtonDefaults.buttonColors(containerColor = ShamsNavyDark, contentColor = Color.White),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(0.9f)
        ) {
          Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Email", fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
      }
    }
  }
}
