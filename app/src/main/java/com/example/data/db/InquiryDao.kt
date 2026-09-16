package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ProjectInquiry
import kotlinx.coroutines.flow.Flow

@Dao
interface InquiryDao {
  @Query("SELECT * FROM project_inquiries ORDER BY timestamp DESC")
  fun getAllInquiries(): Flow<List<ProjectInquiry>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertInquiry(inquiry: ProjectInquiry): Long

  @Update
  suspend fun updateInquiry(inquiry: ProjectInquiry)

  @Delete
  suspend fun deleteInquiry(inquiry: ProjectInquiry)

  @Query("DELETE FROM project_inquiries WHERE id = :id")
  suspend fun deleteById(id: Long)
}
