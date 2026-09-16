package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_inquiries")
data class ProjectInquiry(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val clientName: String,
  val companyName: String = "",
  val phone: String,
  val email: String,
  val serviceCategory: String,
  val projectType: String,
  val details: String,
  val estimatedBudget: String = "",
  val status: String = "Submitted",
  val timestamp: Long = System.currentTimeMillis()
)
