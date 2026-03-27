package com.cipher.media.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Room entity and DAO for intruder detection logs.
 */
@Entity(tableName = "intruder_logs")
data class IntruderLogEntity(
    @PrimaryKey val id: String,
    val photoPath: String?,
    val latitude: Double?,
    val longitude: Double?,
    val timestamp: Long,
    val attemptCount: Int,
    val pinEntered: String = ""
)

@Dao
interface IntruderLogDao {

    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<IntruderLogEntity>>

    @Query("SELECT COUNT(*) FROM intruder_logs")
    fun getLogCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: IntruderLogEntity)

    @Query("DELETE FROM intruder_logs")
    suspend fun clearAllLogs()

    @Delete
    suspend fun deleteLog(log: IntruderLogEntity)
}
