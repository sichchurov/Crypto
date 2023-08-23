package com.example.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getCoinList(): LiveData<List<CoinDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getInfoAboutCoin(fSym: String): LiveData<CoinDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(coinDbModelList: List<CoinDbModel>)
}
