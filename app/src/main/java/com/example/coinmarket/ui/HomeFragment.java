package com.example.coinmarket.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.coinmarket.Adapters.MainListAdapter;
import com.example.coinmarket.Adapters.TopRecyclerViewAdapter;
import com.example.coinmarket.Adapters.viewPagerAdapter;
import com.example.coinmarket.MainActivity;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.RoomDataBase.MarketEntity;
import com.example.coinmarket.ViewModel.AppViewModel;
import com.example.coinmarket.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    MainActivity mainActivity;
    AppViewModel appViewModel;
    TopRecyclerViewAdapter topRecyclerViewAdapter;
    CompositeDisposable compositeDisposable;
    MainListAdapter mainListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        //viewModel
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        compositeDisposable = new CompositeDisposable();

        setViewPager();
        getAllMarketDataFromDataBase();
        setTabLayout(fragmentHomeBinding.mainList,fragmentHomeBinding.testView);

        return fragmentHomeBinding.getRoot();
    }

    private void setTabLayout(View mainList, View testView) {
        mainListAdapter = new MainListAdapter(this);
        fragmentHomeBinding.viewPager2.setAdapter(mainListAdapter);

        //adopt TabLayout with ViewPager
        new TabLayoutMediator(fragmentHomeBinding.tabLayout,fragmentHomeBinding.viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (position == 0){
                    tab.setText("Top50List");
                }else {
                    tab.setText("TestView");
                }
            }
        }).attach();
    }

    private void setViewPager() {
        appViewModel.getMutableLiveData().observe((LifecycleOwner) getActivity(), new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> images) {
                fragmentHomeBinding.viewPager.setAdapter(new viewPagerAdapter(images));
                fragmentHomeBinding.viewPager.setOffscreenPageLimit(2);

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view);
    }

    private void setToolbar(View view) {
        //navController
        NavController navController = Navigation.findNavController(view);
        //drawer
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment)
                .setOpenableLayout(mainActivity.drawerLayout).build();
        //toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.homeFragment) {
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                    toolbar.setTitle((R.string.home_fragment));
                }
            }
        });
    }

    private void getAllMarketDataFromDataBase() {
        Disposable disposable = (Disposable) appViewModel.getAllMarketData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MarketEntity>() {
                    @Override
                    public void accept(MarketEntity marketEntity) throws Throwable {
                        AllMarketModel allMarketModel = marketEntity.getMarketModel();
                        Log.e("TAG", "accept: " + allMarketModel.getRootData().getCryptoCurrencyList().get(0).getName());
                        ArrayList<DataItem> data = new ArrayList<>();
                        for (int i = 0; i < allMarketModel.getRootData().getCryptoCurrencyList().size(); i++) {
                            DataItem dataItem = allMarketModel.getRootData().getCryptoCurrencyList().get(i);
                            data.add(dataItem);


                        }
                        if (fragmentHomeBinding.TopRecyclerView.getAdapter() != null) {
                            topRecyclerViewAdapter = (TopRecyclerViewAdapter) fragmentHomeBinding.TopRecyclerView.getAdapter();
                            topRecyclerViewAdapter.updateData(data);
                        } else {
                            topRecyclerViewAdapter = new TopRecyclerViewAdapter(data);
                            fragmentHomeBinding.TopRecyclerView.setAdapter(topRecyclerViewAdapter);
                        }

                    }
                });
          compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}