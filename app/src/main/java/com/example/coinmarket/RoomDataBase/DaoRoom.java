package com.example.coinmarket.RoomDataBase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface DaoRoom {
    //OnConflictStrategy.REPLACE (if new data received  replace it)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MarketEntity marketEntity);

    @Query("SELECT * FROM MarketList")
    //Flowable : changes in DB =Change in UI
    Flowable<MarketEntity> getAllMarketList();
}
