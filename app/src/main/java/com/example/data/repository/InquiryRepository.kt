package com.example.data.repository

import com.example.data.db.InquiryDao
import com.example.data.model.ProjectInquiry
import kotlinx.coroutines.flow.Flow

class InquiryRepository(private val inquiryDao: InquiryDao) {
  val allInquiries: Flow<List<ProjectInquiry>> = inquiryDao.getAllInquiries()

  suspend fun insertInquiry(inquiry: ProjectInquiry): Long {
    return inquiryDao.insertInquiry(inquiry)
  }

  suspend fun updateInquiry(inquiry: ProjectInquiry) {
    inquiryDao.updateInquiry(inquiry)
  }

  suspend fun deleteInquiry(inquiry: ProjectInquiry) {
    inquiryDao.deleteInquiry(inquiry)
  }

  suspend fun deleteById(id: Long) {
    inquiryDao.deleteById(id)
  }
}
