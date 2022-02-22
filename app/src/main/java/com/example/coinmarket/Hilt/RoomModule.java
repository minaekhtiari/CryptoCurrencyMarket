package com.example.coinmarket.Hilt;

import android.content.Context;

import com.example.coinmarket.RoomDataBase.AppDataBase;
import com.example.coinmarket.RoomDataBase.DaoRoom;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {
    @Provides
    AppDataBase provideAppDataBase (@ApplicationContext Context context){
        return AppDataBase.getInstance(context);
    }
@Provides
DaoRoom provideDao(AppDataBase appDataBase){
        return appDataBase.dao();
        }
}
