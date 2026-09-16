package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SectionHeader
import com.example.ui.components.ShamsContactInfo
import com.example.ui.theme.ShamsAmber
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark

@Composable
fun AboutContactScreen(modifier: Modifier = Modifier) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))
      SectionHeader(
        title = "Company Profile & Contact",
        subtitle = "SHAMS & Sons Co - Certified Engineering & Security Solutions Provider.",
        icon = Icons.Default.Business
      )
    }

    // Company Header Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = ShamsNavyDark),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ShamsBluePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Security, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text("SHAMS & Sons Co", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
              Text("Complete IT • CCTV • Access Control Systems", color = ShamsBlueAccent, fontSize = 12.sp)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "SHAMS & Sons Co is a premier technology integration firm specializing in enterprise surveillance, biometric access control automation, structured copper & fiber optic cabling, server room deployment, and cyber security infrastructure.",
            color = Color(0xFFCBD5E1),
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
        }
      }
    }

    // Official Direct Contact Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Official Communications", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          Spacer(modifier = Modifier.height(12.dp))

          // Phone
          ContactRow(
            icon = Icons.Default.Call,
            title = "Engineering Hotline & Mobile",
            value = ShamsContactInfo.PHONE,
            actionLabel = "Call Now",
            onAction = { ShamsContactInfo.launchDialer(context) }
          )

          Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

          // WhatsApp Direct
          ContactRow(
            icon = Icons.Default.Call,
            title = "Official WhatsApp Business",
            value = ShamsContactInfo.WHATSAPP_DISPLAY,
            actionLabel = "WhatsApp",
            onAction = { ShamsContactInfo.launchWhatsApp(context) }
          )

          Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

          // Email
          ContactRow(
            icon = Icons.Default.Email,
            title = "Official Inquiries Email",
            value = ShamsContactInfo.EMAIL,
            actionLabel = "Send Email",
            onAction = { ShamsContactInfo.launchEmail(context) }
          )

          Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

          // Office Location
          ContactRow(
            icon = Icons.Default.LocationOn,
            title = "Engineering Office & Showroom",
            value = ShamsContactInfo.ADDRESS,
            actionLabel = null,
            onAction = {}
          )

          Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

          // Operational Hours
          ContactRow(
            icon = Icons.Default.Schedule,
            title = "Operating Hours",
            value = "Office: Mon-Sat 08:00 - 18:00\nEmergency SLA Dispatch: 24/7/365",
            actionLabel = null,
            onAction = {}
          )
        }
      }
    }

    // Certified Engineering Standards
    item {
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = ShamsEmerald, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Certifications & Engineering Standards", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          }

          Spacer(modifier = Modifier.height(12.dp))

          CertificationBadge("Hikvision Certified Security Associate (HCSA)")
          CertificationBadge("Dahua Technology Certified Professional (DHSP)")
          CertificationBadge("ZKTeco Biometrics & Turnstiles Certified Specialist")
          CertificationBadge("Cisco Certified Network Associate (CCNA Enterprise)")
          CertificationBadge("Fluke Networks Certified Cabling Test Technician (CCTT)")
          CertificationBadge("ISO 9001:2015 Quality Management Compliant")
        }
      }
    }

    // Annual Maintenance Contracts (AMC)
    item {
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Annual Maintenance Contracts (AMC)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          Text("Comprehensive preventative and emergency maintenance for peace of mind.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

          Spacer(modifier = Modifier.height(12.dp))

          AmcPackageItem(
            name = "Standard Silver AMC",
            desc = "Quarterly preventative maintenance, camera cleaning, lens refocusing, NVR disk health audit, 12-hour response."
          )

          AmcPackageItem(
            name = "Enterprise Gold 24/7 SLA",
            desc = "Monthly proactive inspections, firmware patching, standby replacement hardware, dedicated engineer, 2-hour emergency dispatch."
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = {
              ShamsContactInfo.launchEmail(
                context,
                "AMC Quotation Request - SHAMS & Sons Co",
                "Hello SHAMS & Sons Co,\n\nWe would like to request an Annual Maintenance Contract (AMC) quotation for our facility.\n\nFacility Details:"
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().testTag("amc_inquire_btn")
          ) {
            Text("Request AMC Maintenance Quote")
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

@Composable
private fun ContactRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  value: String,
  actionLabel: String?,
  onAction: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
      }
    }

    if (actionLabel != null) {
      Button(
        onClick = onAction,
        colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(start = 6.dp)
      ) {
        Text(actionLabel, fontSize = 11.sp)
      }
    }
  }
}

@Composable
private fun CertificationBadge(label: String) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ShamsEmerald, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(8.dp))
    Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
  }
}

@Composable
private fun AmcPackageItem(name: String, desc: String) {
  Surface(
    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
      Spacer(modifier = Modifier.height(2.dp))
      Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 15.sp)
    }
  }
}
