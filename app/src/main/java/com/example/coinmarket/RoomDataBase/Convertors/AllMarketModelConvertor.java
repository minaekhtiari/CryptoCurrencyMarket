package com.example.coinmarket.RoomDataBase.Convertors;

import androidx.room.TypeConverter;

import com.example.coinmarket.PojoModels.AllMarketModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class AllMarketModelConvertor {
    @TypeConverter
    public String toJson(AllMarketModel allMarketModel) {
        if (allMarketModel == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<AllMarketModel>() {}.getType();
        String json = gson.toJson(allMarketModel, type);
        return json;
    }

    @TypeConverter
    public AllMarketModel toClassObject(String allMarket) {
        if (allMarket == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<AllMarketModel>() {}.getType();
        AllMarketModel allMarketModel = gson.fromJson(allMarket, type);
        return allMarketModel;
    }
}
