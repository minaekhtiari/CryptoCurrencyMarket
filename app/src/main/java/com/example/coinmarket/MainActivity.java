package com.example.coinmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.app.usage.NetworkStatsManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.PopupMenu;


import com.example.coinmarket.ViewModel.AppViewModel;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    NavHostFragment navHostFragment;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    public DrawerLayout drawerLayout;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    @Inject
    ConnectivityManager connectivityManager;
    @Inject
    NetworkRequest networkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        drawerLayout = activityMainBinding.drawerlayout;
        //Initialize CompositeDisposable for dispose observer
        compositeDisposable = new CompositeDisposable();

        //setupNavigation
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.host_fragmentContainerView);
        navController = navHostFragment.getNavController();
        //drawer
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.marketFragment, R.id.bookmarkFragment)
                .setOpenableLayout(activityMainBinding.drawerlayout)
                .build();
        NavigationUI.setupWithNavController(activityMainBinding.navigationView, navController);

        //setupBottomBar
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.bottom_nav_menu);
        Menu menu = popupMenu.getMenu();

        activityMainBinding.bottomNavigation.setupWithNavController(menu, navController);

        //ViewModel
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        //Check Internet Connectivity
        checkConnection();


        //CallApis
        callApis();


    }

    private void checkConnection() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                super.onAvailable(network);
                Log.e("TAG", "onAvailable: ");
            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                super.onLost(network);
                Log.e("TAG", "onLost: ");
                Snackbar.make(activityMainBinding.mainActivity, "No Connection", 3000).show();
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    private void callApis() {
        Observable.interval(20, TimeUnit.SECONDS)
                .flatMap(n -> appViewModel.MarketFutureCall())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllMarketModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Log.e("time", "onSubscribe time: ");
                        }
                    }

                    @Override
                    public void onNext(@NonNull AllMarketModel allMarketModel) {

                        Log.e("TAG", "onNext: " + allMarketModel.getRootData().getCryptoCurrencyList().get(0).getName());
                        Log.e("TAG", "onNext: " + allMarketModel.getRootData().getCryptoCurrencyList().get(1).getName());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("TAG", "onComplete: ");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //avoid MemoryLeak
        compositeDisposable.clear();
    }
}