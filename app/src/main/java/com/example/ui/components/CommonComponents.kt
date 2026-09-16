package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ShamsBlueAccent
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark

object ShamsContactInfo {
  const val COMPANY_NAME = "SHAMS & Sons Co"
  const val TAGLINE = "Complete IT, CCTV Camera & Access Control Solutions"
  const val EMAIL = "shamssonsco@gmail.com"
  const val PHONE = "+1 (800) 555-7426"
  const val WHATSAPP = "+18005557426"
  const val ADDRESS = "Corporate Tower, Suite 402 - Tech District"
  const val LICENSE = "Licensed Security & IT Infrastructure Contractor"

  fun launchEmail(context: Context, subject: String = "Inquiry: IT & CCTV Solutions - SHAMS & Sons Co", body: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
      data = Uri.parse("mailto:$EMAIL")
      putExtra(Intent.EXTRA_SUBJECT, subject)
      putExtra(Intent.EXTRA_TEXT, body)
    }
    try {
      context.startActivity(Intent.createChooser(intent, "Send Email via"))
    } catch (e: Exception) {
      Toast.makeText(context, "No email app found. Email: $EMAIL", Toast.LENGTH_LONG).show()
    }
  }

  fun launchDialer(context: Context, phone: String = PHONE) {
    val cleanPhone = phone.replace(Regex("[^0-9+]"), "")
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanPhone"))
    try {
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Phone: $phone", Toast.LENGTH_SHORT).show()
    }
  }

  fun launchWhatsApp(context: Context, text: String = "Hello SHAMS & Sons Co, I would like to inquire about your CCTV & IT solutions.") {
    val cleanNumber = WHATSAPP.replace("+", "")
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=${Uri.encode(text)}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
      context.startActivity(intent)
    } catch (e: Exception) {
      launchEmail(context, "WhatsApp Inquiry", text)
    }
  }
}

@Composable
fun ShamsAppHeader(
  modifier: Modifier = Modifier,
  onCallClick: () -> Unit = {},
  onEmailClick: () -> Unit = {}
) {
  val context = LocalContext.current
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = ShamsNavyDark,
    shadowElevation = 4.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
              Brush.linearGradient(
                colors = listOf(ShamsBluePrimary, ShamsBlueAccent)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "SHAMS Security Logo",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "SHAMS & Sons Co",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(ShamsEmerald.copy(alpha = 0.2f))
                .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
              Text(
                text = "LICENSED",
                color = ShamsEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
          Text(
            text = "IT • CCTV • Access Control",
            style = MaterialTheme.typography.bodySmall,
            color = ShamsBlueAccent
          )
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = {
            onCallClick()
            ShamsContactInfo.launchDialer(context)
          },
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E293B))
            .testTag("header_call_button")
        ) {
          Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call SHAMS & Sons",
            tint = ShamsBlueAccent,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            onEmailClick()
            ShamsContactInfo.launchEmail(context)
          },
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E293B))
            .testTag("header_email_button")
        ) {
          Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Email SHAMS & Sons",
            tint = ShamsEmerald,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SectionHeader(
  title: String,
  subtitle: String? = null,
  icon: ImageVector? = null,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )
    }
    if (subtitle != null) {
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun StatusBadge(
  status: String,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor) = when (status.lowercase()) {
    "submitted" -> Pair(Color(0xFF0284C7).copy(alpha = 0.15f), Color(0xFF0284C7))
    "site survey scheduled" -> Pair(Color(0xFFF59E0B).copy(alpha = 0.18f), Color(0xFFD97706))
    "quote provided" -> Pair(Color(0xFF8B5CF6).copy(alpha = 0.18f), Color(0xFF7C3AED))
    "completed" -> Pair(Color(0xFF10B981).copy(alpha = 0.18f), Color(0xFF059669))
    else -> Pair(Color(0xFF64748B).copy(alpha = 0.18f), Color(0xFF475569))
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(6.dp))
      .background(bgColor)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Text(
      text = status.uppercase(),
      color = textColor,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold
    )
  }
}
