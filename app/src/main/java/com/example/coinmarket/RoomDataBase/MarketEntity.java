package com.example.coinmarket.RoomDataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.coinmarket.PojoModels.AllMarketModel;

@Entity(tableName = "MarketList")
public class MarketEntity {

   @PrimaryKey
    public int primaryKey;

  @ColumnInfo
    public AllMarketModel marketModel;

    public MarketEntity(AllMarketModel marketModel) {
        this.marketModel = marketModel;
    }



    public int getPrimaryKey() {
        return primaryKey;
    }

    public AllMarketModel getMarketModel() {
        return marketModel;
    }
}
