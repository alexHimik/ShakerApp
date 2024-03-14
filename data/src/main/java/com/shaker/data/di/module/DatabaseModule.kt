package com.shaker.data.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shaker.data.database.ShakerAppDatabase
import com.shaker.data.database.dao.CocktailCategoryDao
import com.shaker.data.database.dao.CocktailDao
import com.shaker.data.di.scope.DataScope
import dagger.Module
import dagger.Provides

@Suppress("TooManyFunctions")
@Module
class DatabaseModule {

    @DataScope
    @Provides
    fun provideDatabase(context: Context): ShakerAppDatabase {
        return Room.databaseBuilder(
            context,
            ShakerAppDatabase::class.java,
            "shaker_app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCocktailCategoryDao(database: ShakerAppDatabase): CocktailCategoryDao = database.getCocktailCategoryDao()

    @Provides
    fun provideCocktailDao(database: ShakerAppDatabase): CocktailDao = database.getCocktailDao()
}