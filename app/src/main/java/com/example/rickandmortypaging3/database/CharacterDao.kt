//package com.example.rickandmortypaging3.database
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.rickandmortypaging3.network.model.PojoResult
//
//@Dao
//interface CharacterDao {
//    @Query("SELECT * FROM character_table")
//    fun getAllCharacter(): LiveData<List<PojoResult>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllCharacter(list: List<PojoResult>)
//}