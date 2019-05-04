package me.kevincampos.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.kevincampos.cache.database.PolicyEventsConstants.TABLE_NAME
import me.kevincampos.cache.model.PolicyEventEntity

@Dao
abstract class PolicyEventDao {

    @Query("SELECT * FROM $TABLE_NAME")
    @JvmSuppressWildcards
    abstract fun getPolicyEvents(): List<PolicyEventEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE policyId = :policyId AND type = :type")
    @JvmSuppressWildcards
    abstract fun getPolicyEventsWith(policyId: String, type: String): List<PolicyEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertPolicyEvents(policyEvents: List<PolicyEventEntity>)

}