package com.example.coinmarket.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.coinmarket.RoomDataBase.Convertors.AllMarketModelConvertor;

@TypeConverters({AllMarketModelConvertor.class})
@Database(entities = {MarketEntity.class},version =1)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DataBaseName = "AppDatabase";
    private static AppDataBase instance;
    public abstract DaoRoom dao();

    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,DataBaseName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}


//fallbackToDestructiveMigration :avoid some crashes because of version problems (recreate DB)
