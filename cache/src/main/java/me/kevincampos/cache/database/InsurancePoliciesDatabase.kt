package me.kevincampos.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.kevincampos.cache.dao.PolicyEventDao
import me.kevincampos.cache.model.PolicyEventEntity

@Database(entities = [PolicyEventEntity::class], version = 1)
abstract class InsurancePoliciesDatabase : RoomDatabase() {

    abstract fun policyEventDao(): PolicyEventDao

    companion object {

        private var INSTANCE: InsurancePoliciesDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): InsurancePoliciesDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            InsurancePoliciesDatabase::class.java,
                            "insurance_policies.db"
                        ).build()
                    }
                    return INSTANCE as InsurancePoliciesDatabase
                }
            }
            return INSTANCE as InsurancePoliciesDatabase
        }
    }

}