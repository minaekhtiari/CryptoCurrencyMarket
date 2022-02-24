package com.example.coinmarket.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.coinmarket.R;
import com.example.coinmarket.Repository.AppRepository;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.RoomDataBase.MarketEntity;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class AppViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Integer>> mutableLiveData = new MutableLiveData<>();

    @Inject
    AppRepository appRepository;

    @Inject
    public AppViewModel(@NonNull Application application) {
        super(application);
        getPagerImgs();
    }

    public Observable<AllMarketModel> MarketFutureCall() {
        return appRepository.marketListFutureCall();
    }

    public void InsertAllMarketModell(AllMarketModel allMarketModel) {
        appRepository.InsertAllMarket(allMarketModel);
    }

    MutableLiveData<ArrayList<Integer>> getPagerImgs() {
        ArrayList<Integer> imgs = new ArrayList();
        imgs.add(R.drawable.top_view_pager_1);
        imgs.add(R.drawable.top_view_pager_2);
        imgs.add(R.drawable.top_view_pager_3);
        imgs.add(R.drawable.top_view_pager_4);
        imgs.add(R.drawable.top_view_pager_5);


        mutableLiveData.postValue(imgs);
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<Integer>> getMutableLiveData() {
        return mutableLiveData;
    }


    public Flowable<MarketEntity> getAllMarketData() {
        return appRepository.getAllMarketData();
    }
}
