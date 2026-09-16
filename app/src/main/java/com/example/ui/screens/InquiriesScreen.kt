package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProjectInquiry
import com.example.ui.components.SectionHeader
import com.example.ui.components.ShamsContactInfo
import com.example.ui.components.StatusBadge
import com.example.ui.theme.ShamsBluePrimary
import com.example.ui.theme.ShamsEmerald
import com.example.ui.theme.ShamsNavyDark
import com.example.ui.viewmodel.ShamsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InquiriesScreen(
  viewModel: ShamsViewModel,
  initialDetails: String = "",
  initialBudget: String = "",
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val inquiries by viewModel.allInquiries.collectAsState()

  var isFormOpen by remember { mutableStateOf(initialDetails.isNotEmpty()) }
  var clientName by remember { mutableStateOf("") }
  var companyName by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var selectedService by remember { mutableStateOf("CCTV Camera Surveillance") }
  var selectedProjectType by remember { mutableStateOf("Commercial Office") }
  var projectDetails by remember { mutableStateOf(initialDetails) }
  var budget by remember { mutableStateOf(initialBudget) }

  var inquiryToDelete by remember { mutableStateOf<ProjectInquiry?>(null) }

  val serviceCategories = listOf(
    "CCTV Camera Surveillance",
    "Access Control Systems",
    "IT & Structured Cabling",
    "Complete Turnkey Solution"
  )

  val propertyTypes = listOf("Commercial Office", "Warehouse / Industrial", "Retail Shop", "Residential Villa", "Data Center")

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))
      SectionHeader(
        title = "Service Requests & Inquiries",
        subtitle = "Book a free onsite engineering survey or submit technical project requirements.",
        icon = Icons.Default.Assignment
      )
    }

    // Toggle Form Button
    item {
      Button(
        onClick = { isFormOpen = !isFormOpen },
        colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
          .testTag("toggle_inquiry_form_btn")
      ) {
        Icon(
          imageVector = if (isFormOpen) Icons.Default.Close else Icons.Default.Add,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (isFormOpen) "Close Request Form" else "New Site Survey / Consultation Request",
          fontWeight = FontWeight.Bold
        )
      }
    }

    // Inquiry Submission Form
    item {
      AnimatedVisibility(visible = isFormOpen) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Project & Contact Information",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Direct submission to SHAMS & Sons Co engineering desk",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = clientName,
              onValueChange = { clientName = it },
              label = { Text("Contact Person Name *") },
              modifier = Modifier.fillMaxWidth().testTag("input_client_name"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = companyName,
              onValueChange = { companyName = it },
              label = { Text("Company / Property Name") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number *") },
                modifier = Modifier.weight(1f).testTag("input_phone"),
                singleLine = true
              )
              OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address *") },
                modifier = Modifier.weight(1f).testTag("input_email"),
                singleLine = true
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Required Service Category", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              serviceCategories.forEach { cat ->
                FilterChip(
                  selected = selectedService == cat,
                  onClick = { selectedService = cat },
                  label = { Text(cat, fontSize = 11.sp) }
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Property / Facility Type", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              propertyTypes.forEach { prop ->
                FilterChip(
                  selected = selectedProjectType == prop,
                  onClick = { selectedProjectType = prop },
                  label = { Text(prop, fontSize = 11.sp) }
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = budget,
              onValueChange = { budget = it },
              label = { Text("Estimated Budget (Optional)") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = projectDetails,
              onValueChange = { projectDetails = it },
              label = { Text("Project Details / Specific Requirements *") },
              modifier = Modifier.fillMaxWidth().height(110.dp),
              placeholder = { Text("e.g. Need 8 cameras with 30 days storage, 2 access control doors, and 16 Cat6 network drops...") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Button(
                onClick = {
                  if (clientName.isBlank() || phone.isBlank()) {
                    Toast.makeText(context, "Please enter your name and phone number", Toast.LENGTH_SHORT).show()
                    return@Button
                  }
                  viewModel.submitInquiry(
                    name = clientName,
                    company = companyName,
                    phone = phone,
                    email = if (email.isBlank()) "Not specified" else email,
                    service = selectedService,
                    projectType = selectedProjectType,
                    details = if (projectDetails.isBlank()) "Standard Site Survey requested" else projectDetails,
                    budget = budget
                  ) {
                    Toast.makeText(context, "Inquiry saved! Sending copy to SHAMS & Sons Co...", Toast.LENGTH_SHORT).show()
                    val emailSubject = "New Project Inquiry: $selectedService - $clientName ($selectedProjectType)"
                    val emailBody = """
                      New Service Request for SHAMS & Sons Co:
                      ----------------------------------------
                      Client: $clientName
                      Company: $companyName
                      Phone: $phone
                      Email: $email
                      Service: $selectedService
                      Property Type: $selectedProjectType
                      Estimated Budget: $budget
                      
                      Requirements & Details:
                      $projectDetails
                    """.trimIndent()
                    ShamsContactInfo.launchEmail(context, emailSubject, emailBody)
                    isFormOpen = false
                    clientName = ""
                    companyName = ""
                    phone = ""
                    email = ""
                    projectDetails = ""
                    budget = ""
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ShamsBluePrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f).testTag("submit_inquiry_btn")
              ) {
                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Submit & Dispatch Email", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "Your Project Requests (${inquiries.size})",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(6.dp))
    }

    if (inquiries.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("No Submitted Inquiries Yet", fontWeight = FontWeight.Bold)
            Text(
              "Use the button above or configure our Estimator to send your requirements to SHAMS & Sons Co.",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }
        }
      }
    }

    items(inquiries) { inquiry ->
      InquiryItemCard(
        inquiry = inquiry,
        onCallClient = { ShamsContactInfo.launchDialer(context, inquiry.phone) },
        onEmailClient = { ShamsContactInfo.launchEmail(context, "Regarding Inquiry #${inquiry.id}", inquiry.details) },
        onDelete = { inquiryToDelete = inquiry },
        onStatusChange = { newStatus -> viewModel.updateInquiryStatus(inquiry, newStatus) },
        modifier = Modifier.padding(vertical = 6.dp)
      )
    }

    item {
      Spacer(modifier = Modifier.height(30.dp))
    }
  }

  // Delete Confirmation Dialog
  if (inquiryToDelete != null) {
    AlertDialog(
      onDismissRequest = { inquiryToDelete = null },
      title = { Text("Delete Inquiry") },
      text = { Text("Are you sure you want to remove this project inquiry record?") },
      confirmButton = {
        Button(
          onClick = {
            inquiryToDelete?.let { viewModel.deleteInquiry(it) }
            inquiryToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
          Text("Delete")
        }
      },
      dismissButton = {
        TextButton(onClick = { inquiryToDelete = null }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
private fun InquiryItemCard(
  inquiry: ProjectInquiry,
  onCallClient: () -> Unit,
  onEmailClient: () -> Unit,
  onDelete: () -> Unit,
  onStatusChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(false) }
  val dateFormatted = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(inquiry.timestamp))

  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = inquiry.serviceCategory,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
          )
          Text(
            text = "${inquiry.clientName} ${if (inquiry.companyName.isNotBlank()) "(${inquiry.companyName})" else ""} • $dateFormatted",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
        StatusBadge(status = inquiry.status)
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Property: ${inquiry.projectType} • Phone: ${inquiry.phone}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
      )

      if (inquiry.estimatedBudget.isNotBlank()) {
        Text(
          text = "Budget: ${inquiry.estimatedBudget}",
          fontSize = 12.sp,
          color = ShamsEmerald,
          fontWeight = FontWeight.Bold
        )
      }

      AnimatedVisibility(visible = isExpanded) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Scope / Specifications:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = inquiry.details,
              fontSize = 12.sp,
              modifier = Modifier.padding(10.dp),
              lineHeight = 16.sp
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text("Update Status:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            listOf("Site Survey Scheduled", "Quote Provided", "Completed").forEach { st ->
              FilterChip(
                selected = inquiry.status == st,
                onClick = { onStatusChange(st) },
                label = { Text(st.take(15), fontSize = 10.sp) }
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = { isExpanded = !isExpanded },
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = "Expand",
            modifier = Modifier.size(20.dp)
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          IconButton(onClick = onCallClient, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = ShamsBluePrimary, modifier = Modifier.size(18.dp))
          }
          IconButton(onClick = onEmailClient, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Email, contentDescription = "Email", tint = ShamsEmerald, modifier = Modifier.size(18.dp))
          }
          IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
          }
        }
      }
    }
  }
}
