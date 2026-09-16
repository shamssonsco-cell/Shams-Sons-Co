package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

data class ServiceCategoryItem(
  val id: String,
  val title: String,
  val subtitle: String,
  val description: String,
  @DrawableRes val imageRes: Int,
  val keyHighlights: List<String>,
  val subServices: List<SubServiceDetail>,
  val standardWarranty: String = "1-Year Full Onsite Warranty & SLA"
)

data class SubServiceDetail(
  val name: String,
  val summary: String,
  val specs: List<String>,
  val typicalDeployment: String
)

object ShamsServicesData {
  val pillars = listOf(
    ServiceCategoryItem(
      id = "cctv",
      title = "CCTV Camera Surveillance",
      subtitle = "4K Ultra-HD, AI Analytics & 24/7 Remote Monitoring",
      description = "End-to-end commercial, industrial and residential video surveillance architecture. From high-definition IP camera matrices to optical PTZ cameras and secure AI-driven NVR storage.",
      imageRes = R.drawable.img_cctv_camera,
      keyHighlights = listOf(
        "4K UHD Resolution & Color Starlight Night Vision",
        "AI Human & Vehicle Smart Perimeter Detection",
        "Mobile App Live View & Cloud / NVR Multi-channel Playback",
        "Weatherproof IP67 & Vandal-Proof IK10 Enclosures",
        "H.265+ High-efficiency Video Compression"
      ),
      subServices = listOf(
        SubServiceDetail(
          name = "IP Dome & Turret Cameras",
          summary = "Discreet indoor & outdoor cameras with wide-angle coverage and infrared illumination.",
          specs = listOf("4MP / 8MP 4K", "105° - 120° Wide FOV", "Built-in Mic Audio", "PoE (Power over Ethernet)"),
          typicalDeployment = "Offices, Retail Stores, Hallways & Entrance Foyers"
        ),
        SubServiceDetail(
          name = "Long-Range Bullet & Perimeter Cameras",
          summary = "High-impact deterrence with ultra-long IR range up to 80 meters and smart strobe warning.",
          specs = listOf("Motorized Vari-focal 2.8-12mm", "Smart Motion Alert", "Deep Learning Video Analytics", "Lightning Surge Protection"),
          typicalDeployment = "Perimeter Fencing, Parking Lots, Warehouses & Yards"
        ),
        SubServiceDetail(
          name = "PTZ (Pan-Tilt-Zoom) Optical Cameras",
          summary = "360° continuous rotation with 25x/32x optical zoom for expansive areas and auto-tracking.",
          specs = listOf("360° Endless Pan", "Auto-Tracking Target", "Preset Patrol Tours", "150m Smart IR"),
          typicalDeployment = "Commercial Plazas, Logistics Centers & Large Estates"
        ),
        SubServiceDetail(
          name = "Enterprise NVRs & Storage Arrays",
          summary = "High-throughput Network Video Recorders with hot-swappable enterprise surveillance hard drives.",
          specs = listOf("8 / 16 / 32 / 64 Channels", "RAID 0/1/5 Redundancy", "Remote Multi-user Management", "Scheduled & Motion Archival"),
          typicalDeployment = "Server Rooms, Security Operations Centers (SOC)"
        )
      )
    ),
    ServiceCategoryItem(
      id = "access_control",
      title = "Access Control Systems",
      subtitle = "Biometric, RFID Smart Cards & Smart Door Automation",
      description = "Robust access security ensuring only authorized personnel enter sensitive zones. Centralized management for corporate offices, residential buildings, and industrial facilities.",
      imageRes = R.drawable.img_access_control,
      keyHighlights = listOf(
        "Touchless 3D Face Recognition & High-speed Fingerprint",
        "Encrypted RFID / Mifare / NFC Smart Cards & Mobile Credentials",
        "Heavy-duty Electromagnetic (600lbs/1200lbs) & Drop-bolt Locks",
        "Integrated Time & Attendance with Automated Reports",
        "Emergency Fire Alarm Break-glass & Anti-tailgating Interlocks"
      ),
      subServices = listOf(
        SubServiceDetail(
          name = "Biometric Facial Recognition Terminals",
          summary = "Under 0.2 second verification with anti-spoofing dual cameras and mask detection.",
          specs = listOf("50,000 Face Capacity", "Touch-free Verification", "IPS Touchscreen", "TCP/IP & Wi-Fi Enabled"),
          typicalDeployment = "Corporate Headquarters, Executive Suites, Clean Rooms"
        ),
        SubServiceDetail(
          name = "RFID & Smart Card Readers",
          summary = "Sleek mullion readers supporting 13.56MHz Mifare and DESFire high-security encryption.",
          specs = listOf("Wiegand & OSDP Protocol", "LED & Audio Buzzer Feedback", "IP65 Waterproof Housing", "PIN Code Keypad"),
          typicalDeployment = "Staff Entrances, Server Rooms, Elevators & Shared Amenities"
        ),
        SubServiceDetail(
          name = "Electromagnetic Locks & Hardware",
          summary = "Fail-safe magnetic locks, shear locks, push-to-exit buttons, and power backup supplies.",
          specs = listOf("600 lbs / 1200 lbs Holding Force", "Door Status Sensor", "UPS Battery Backup 4-8 hrs", "Key Override"),
          typicalDeployment = "Glass Doors, Wooden Doors, Metal & Fire Emergency Doors"
        ),
        SubServiceDetail(
          name = "Turnstiles & Speed Gates",
          summary = "Flap barriers and tripod turnstiles for high-traffic pedestrian throughput management.",
          specs = listOf("Brushless DC Motor", "Bi-directional Passage", "Anti-pinch Infrared Sensors", "Emergency Free-exit Auto Drop"),
          typicalDeployment = "Lobbies, Metro Stations, Fitness Clubs, Business Towers"
        )
      )
    ),
    ServiceCategoryItem(
      id = "it_networking",
      title = "Complete IT & Networking Solutions",
      subtitle = "Structured Cabling, Server Racks & Enterprise Wi-Fi",
      description = "Comprehensive information technology foundation. We design, deploy, and maintain high-speed network topologies, structured cabling, switching, routing, firewalls, and server infrastructure.",
      imageRes = R.drawable.img_it_networking,
      keyHighlights = listOf(
        "Cat6 / Cat6A Gigabit & 10G/40G Fiber Optic Structured Cabling",
        "Server Rack Cabinets (6U to 42U) with Professional Cable Dressing",
        "Managed Gigabit PoE+ Network Switches & Layer-3 Routers",
        "Enterprise High-Density Wi-Fi 6 / Wi-Fi 7 Mesh Architecture",
        "Network Security Firewalls, VPN & NAS Centralized Backup"
      ),
      subServices = listOf(
        SubServiceDetail(
          name = "Structured Cabling & Fiber Optics",
          summary = "Standards-compliant Cat6/6A copper and single-mode/multi-mode fiber backbones with Fluke testing certification.",
          specs = listOf("Low-Smoke Zero Halogen (LSZH)", "24/48-Port Keystone Patch Panels", "Fiber Fusion Splicing", "Fluke Test Certification"),
          typicalDeployment = "Commercial Buildings, Data Centers, Multi-floor Offices"
        ),
        SubServiceDetail(
          name = "Server Rack & Cable Organization",
          summary = "Turnkey server rack installations with horizontal/vertical cable organizers, PDU power bars, and thermal cooling.",
          specs = listOf("9U Wall-mount to 42U Floor Standing", "Perforated Mesh Cooling Doors", "Smart Rack PDUs", "Cable Tie Dressing"),
          typicalDeployment = "IT Server Rooms, Telco Closets (MDF/IDF)"
        ),
        SubServiceDetail(
          name = "Managed Switches & Routing Infrastructure",
          summary = "High-performance PoE+ switching powering IP cameras, VOIP phones, and access points seamlessly.",
          specs = listOf("PoE+ 370W / 740W Budget", "VLAN & QoS Traffic Separation", "SFP+ 10G Fiber Uplinks", "Hardware Failover"),
          typicalDeployment = "Core Network Infrastructure & Campus Networks"
        ),
        SubServiceDetail(
          name = "Enterprise Wi-Fi & Cyber Security",
          summary = "Seamless wireless roaming without drops, guest captive portals, Next-Gen Firewalls, and secure VPNs.",
          specs = listOf("Wi-Fi 6 (802.11ax) Tri-Band", "WPA3 Enterprise Encryption", "Next-Gen Firewall DPI", "Automated NAS Backup"),
          typicalDeployment = "Hotels, Co-working Spaces, Schools, Warehouses"
        )
      )
    )
  )
}
