package com.example.coinmarket.Repository;


import android.util.Log;

import com.example.coinmarket.Retrofit.RequestApi;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.RoomDataBase.DaoRoom;
import com.example.coinmarket.RoomDataBase.MarketEntity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppRepository {

    RequestApi requestApi;
    DaoRoom dao;


    public AppRepository(RequestApi requestApi, DaoRoom dao) {
        this.requestApi = requestApi;
        this.dao=dao;

    }
    public Observable<AllMarketModel> marketListFutureCall(){
        return requestApi.makeMarketLatestListCall();
    }
    public void InsertAllMarket(AllMarketModel allMarketModel){
        Completable.fromAction(()-> dao.insert(new MarketEntity(allMarketModel)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("insertAllMarket", "onSubscribe: ok");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("insertAllMarket", "onComplete: ok");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("insertAllMarket", "onError: " + e.getMessage());
                    }
                });

    }
}
