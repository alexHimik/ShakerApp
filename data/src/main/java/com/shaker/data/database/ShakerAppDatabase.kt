package com.shaker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shaker.data.database.dao.CocktailCategoryDao
import com.shaker.data.database.dao.CocktailDao
import com.shaker.data.database.entity.CocktailCategoryEntity
import com.shaker.data.database.entity.CocktailEntity

@Database(
    version = 1,
    entities = [
        CocktailEntity::class,
        CocktailCategoryEntity::class
    ])
abstract class ShakerAppDatabase : RoomDatabase() {

    abstract fun getCocktailCategoryDao(): CocktailCategoryDao

    abstract fun getCocktailDao(): CocktailDao
}