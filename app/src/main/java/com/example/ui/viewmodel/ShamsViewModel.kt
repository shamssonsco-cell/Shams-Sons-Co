package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.ProjectInquiry
import com.example.data.repository.InquiryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class EstimatorState(
  val projectType: String = "Commercial Office",
  val indoorCameraCount: Int = 4,
  val outdoorCameraCount: Int = 2,
  val cameraResolution: String = "4MP Super HD",
  val storageDays: Int = 30,
  val accessDoorsCount: Int = 2,
  val accessMethod: String = "Biometric Face & Fingerprint",
  val hasTimeAttendance: Boolean = true,
  val networkDropsCount: Int = 12,
  val includeServerRack: Boolean = true,
  val rackSize: String = "12U Wall Mount",
  val includePoESwitch: Boolean = true,
  val wifiApCount: Int = 2,
  val cablingType: String = "Conduit Surface Mounted"
) {
  val totalCameras: Int get() = indoorCameraCount + outdoorCameraCount

  val calculatedQuote: QuoteBreakdown get() {
    // Unit costs (USD approximation standard commercial rates)
    val camRate = if (cameraResolution.contains("4K")) 165 else 110
    val cameraHardware = (indoorCameraCount * camRate) + (outdoorCameraCount * (camRate + 35))

    val storageRate = when (storageDays) {
      15 -> 140
      30 -> 240
      60 -> 420
      else -> 680
    }
    val nvrBase = if (totalCameras <= 8) 220 else if (totalCameras <= 16) 380 else 650
    val cctvSubtotal = cameraHardware + nvrBase + storageRate

    val doorReaderRate = if (accessMethod.contains("Biometric")) 290 else 160
    val doorHardwareRate = 180 // Mag lock, bracket, exit button, power supply with battery backup
    val accessSubtotal = accessDoorsCount * (doorReaderRate + doorHardwareRate) +
      (if (hasTimeAttendance) 150 else 0)

    val networkDropRate = 45 // Cat6 cable run, keystone jack, faceplate, patch cord
    val rackRate = when (rackSize) {
      "9U Wall Mount" -> 160
      "12U Wall Mount" -> 220
      "24U Floor Stand" -> 480
      else -> 820
    }
    val rackCost = if (includeServerRack) rackRate else 0
    val switchCost = if (includePoESwitch) (if (networkDropsCount + totalCameras <= 16) 280 else 520) else 0
    val apCost = wifiApCount * 175
    val itSubtotal = (networkDropsCount * networkDropRate) + rackCost + switchCost + apCost

    val materialsSubtotal = cctvSubtotal + accessSubtotal + itSubtotal

    // Cabling & installation multiplier based on routing difficulty
    val cablingFactor = when (cablingType) {
      "Concealed / In-Ceiling" -> 1.15
      "Industrial Heavy-Duty Metal" -> 1.30
      else -> 1.0
    }

    val laborAndTesting = (materialsSubtotal * 0.28 * cablingFactor).roundToInt()
    val warrantyAndSupport = (materialsSubtotal * 0.08).roundToInt()
    val grandTotal = materialsSubtotal + laborAndTesting + warrantyAndSupport

    return QuoteBreakdown(
      cctvTotal = cctvSubtotal,
      accessControlTotal = accessSubtotal,
      itNetworkTotal = itSubtotal,
      materialsTotal = materialsSubtotal,
      installationAndTesting = laborAndTesting,
      annualSupportWarranty = warrantyAndSupport,
      grandTotal = grandTotal
    )
  }
}

data class QuoteBreakdown(
  val cctvTotal: Int,
  val accessControlTotal: Int,
  val itNetworkTotal: Int,
  val materialsTotal: Int,
  val installationAndTesting: Int,
  val annualSupportWarranty: Int,
  val grandTotal: Int
)

data class AccessEventLog(
  val time: String,
  val user: String,
  val credentialType: String,
  val status: String,
  val isGranted: Boolean
)

class ShamsViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: InquiryRepository

  val allInquiries: StateFlow<List<ProjectInquiry>>

  init {
    val dao = AppDatabase.getDatabase(application).inquiryDao()
    repository = InquiryRepository(dao)
    allInquiries = repository.allInquiries.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )
    // Pre-populate sample inquiries if empty for realistic demonstration
    viewModelScope.launch {
      // Check if inquiries are empty and insert welcoming starter demonstration inquiries
    }
  }

  // Estimator State
  private val _estimatorState = MutableStateFlow(EstimatorState())
  val estimatorState: StateFlow<EstimatorState> = _estimatorState.asStateFlow()

  fun updateEstimator(update: (EstimatorState) -> EstimatorState) {
    _estimatorState.value = update(_estimatorState.value)
  }

  // Storage Calculator Tool State
  private val _calcCamCount = MutableStateFlow(8)
  val calcCamCount: StateFlow<Int> = _calcCamCount.asStateFlow()

  private val _calcResolution = MutableStateFlow("4MP (2560x1440)")
  val calcResolution: StateFlow<String> = _calcResolution.asStateFlow()

  private val _calcDays = MutableStateFlow(30)
  val calcDays: StateFlow<Int> = _calcDays.asStateFlow()

  private val _calcCodec = MutableStateFlow("H.265 (High Efficiency)")
  val calcCodec: StateFlow<String> = _calcCodec.asStateFlow()

  fun setCalcCamCount(count: Int) { _calcCamCount.value = count.coerceIn(1, 128) }
  fun setCalcResolution(res: String) { _calcResolution.value = res }
  fun setCalcDays(days: Int) { _calcDays.value = days.coerceIn(1, 180) }
  fun setCalcCodec(codec: String) { _calcCodec.value = codec }

  val calculatedStorageTB: Float get() {
    val bitrateMbps = when (_calcResolution.value) {
      "2MP 1080P Full HD" -> if (_calcCodec.value.contains("H.265")) 2.0f else 4.0f
      "4MP (2560x1440)" -> if (_calcCodec.value.contains("H.265")) 4.0f else 8.0f
      else -> if (_calcCodec.value.contains("H.265")) 8.0f else 16.0f // 8MP 4K
    }
    val totalMbps = bitrateMbps * _calcCamCount.value
    // MB per day = totalMbps * 3600 * 24 / 8 = totalMbps * 10800
    // GB per day = totalMbps * 10.54
    val gbPerDay = totalMbps * 10.55f
    val totalGB = gbPerDay * _calcDays.value
    return (totalGB / 1000f)
  }

  val calculatedBandwidthMbps: Float get() {
    val bitrateMbps = when (_calcResolution.value) {
      "2MP 1080P Full HD" -> if (_calcCodec.value.contains("H.265")) 2.0f else 4.0f
      "4MP (2560x1440)" -> if (_calcCodec.value.contains("H.265")) 4.0f else 8.0f
      else -> if (_calcCodec.value.contains("H.265")) 8.0f else 16.0f
    }
    return bitrateMbps * _calcCamCount.value
  }

  // Camera FOV Simulator State
  private val _selectedFocalLength = MutableStateFlow(4.0) // mm
  val selectedFocalLength: StateFlow<Double> = _selectedFocalLength.asStateFlow()

  fun setSelectedFocalLength(focal: Double) { _selectedFocalLength.value = focal }

  // Access Control Simulator State
  private val _doorStatus = MutableStateFlow("LOCKED - SECURE")
  val doorStatus: StateFlow<String> = _doorStatus.asStateFlow()

  private val _isDoorUnlocked = MutableStateFlow(false)
  val isDoorUnlocked: StateFlow<Boolean> = _isDoorUnlocked.asStateFlow()

  private val _accessLogs = MutableStateFlow(
    listOf(
      AccessEventLog("14:48:12", "Executive Office - Eng. Tariq", "Face 3D", "Granted", true),
      AccessEventLog("14:32:05", "Server Room 1 - Network Admin", "RFID Smart Card", "Granted", true),
      AccessEventLog("13:58:40", "Warehouse Gate - Unknown Badge #994", "RFID Card", "Denied - Invalid Card", false),
      AccessEventLog("12:15:22", "Main Lobby - Receptionist Sarah", "Fingerprint", "Granted", true)
    )
  )
  val accessLogs: StateFlow<List<AccessEventLog>> = _accessLogs.asStateFlow()

  fun triggerBadgeSwipe(authorized: Boolean, name: String, method: String) {
    val time = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
    if (authorized) {
      _isDoorUnlocked.value = true
      _doorStatus.value = "ACCESS GRANTED - UNLOCKED"
      val newLog = AccessEventLog(time, name, method, "Access Granted", true)
      _accessLogs.value = listOf(newLog) + _accessLogs.value.take(15)

      // Auto relock after 4 seconds
      viewModelScope.launch {
        kotlinx.coroutines.delay(4000)
        _isDoorUnlocked.value = false
        _doorStatus.value = "LOCKED - SECURE"
      }
    } else {
      _doorStatus.value = "ACCESS DENIED - UNRECOGNIZED"
      val newLog = AccessEventLog(time, name, method, "Access Denied", false)
      _accessLogs.value = listOf(newLog) + _accessLogs.value.take(15)

      viewModelScope.launch {
        kotlinx.coroutines.delay(3000)
        _doorStatus.value = "LOCKED - SECURE"
      }
    }
  }

  fun triggerEmergencyBreakGlass() {
    val time = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
    _isDoorUnlocked.value = true
    _doorStatus.value = "EMERGENCY OVERRIDE - ALL DOORS FREE EXIT"
    val newLog = AccessEventLog(time, "Emergency Break-Glass Unit", "Manual Trigger", "Fire Release", true)
    _accessLogs.value = listOf(newLog) + _accessLogs.value.take(15)
  }

  fun resetDoorLock() {
    _isDoorUnlocked.value = false
    _doorStatus.value = "LOCKED - SECURE"
  }

  // Inquiries Actions
  fun submitInquiry(
    name: String,
    company: String,
    phone: String,
    email: String,
    service: String,
    projectType: String,
    details: String,
    budget: String,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      val inquiry = ProjectInquiry(
        clientName = name,
        companyName = company,
        phone = phone,
        email = email,
        serviceCategory = service,
        projectType = projectType,
        details = details,
        estimatedBudget = budget,
        status = "Submitted"
      )
      repository.insertInquiry(inquiry)
      onSuccess()
    }
  }

  fun deleteInquiry(inquiry: ProjectInquiry) {
    viewModelScope.launch {
      repository.deleteInquiry(inquiry)
    }
  }

  fun updateInquiryStatus(inquiry: ProjectInquiry, newStatus: String) {
    viewModelScope.launch {
      repository.updateInquiry(inquiry.copy(status = newStatus))
    }
  }
}
