package com.example.coinmarket.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.coinmarket.Adapters.MainListRecyclerViewAdapter;
import com.example.coinmarket.MainActivity;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.ViewModel.AppViewModel;
import com.example.coinmarket.databinding.FragmentMarketBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketFragment extends Fragment {

    FragmentMarketBinding fragmentMarketBinding;
    MainActivity mainActivity;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    MainListRecyclerViewAdapter mainListRecyclerViewAdapter;
    List<DataItem> data;
    ArrayList<DataItem> searchList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }


    private void setToolbar(View view) {
        //navController
        NavController navController = Navigation.findNavController(view);
        //drawer
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.marketFragment)
                .setOpenableLayout(mainActivity.drawerLayout).build();
        //toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.marketFragment) {
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                    toolbar.setTitle((R.string.market_fragment));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMarketBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false);

        compositeDisposable = new CompositeDisposable();
        searchList=new ArrayList<>();
        //viewModel
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        setRecyclerView();
        SearchEditText();


        return fragmentMarketBinding.getRoot(); //inflater.inflate(R.layout.fragment_market, container, false);


    }

    private void SearchEditText() {
        fragmentMarketBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchList(s.toString());
                fragmentMarketBinding.searchEditText.clearFocus();
                fragmentMarketBinding.frameLayoutMarket.isFocused();
            }
        });

        fragmentMarketBinding.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(fragmentMarketBinding.searchEditText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view);


    }

    private void setRecyclerView() {
        Disposable disposable = appViewModel.getAllMarketData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(roomMarketEntity -> {
                    AllMarketModel allMarketModel = roomMarketEntity.getMarketModel();
                    data = allMarketModel.getRootData().getCryptoCurrencyList();



                    try {
                        ArrayList<DataItem> dataItems = new ArrayList<>();

                            for (int i = 0;i < 50;i++){
                                dataItems.add(data.get(i));
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            fragmentMarketBinding.marketRecyclerView.setLayoutManager(linearLayoutManager);
                            Bundle bundle=new Bundle();
                            if (fragmentMarketBinding.marketRecyclerView.getAdapter() == null) {
                                mainListRecyclerViewAdapter = new MainListRecyclerViewAdapter(dataItems);
                                fragmentMarketBinding.marketRecyclerView.setAdapter(mainListRecyclerViewAdapter);
//                                fragmentMarketBinding.marketRecyclerView.setAdapter(new MainListRecyclerViewAdapter(
//                                        dataItems, new MainListRecyclerViewAdapter.OnItemClickListener() {
//                                    @Override public void onItemClick(DataItem item) {
//                                        mainListRecyclerViewAdapter.getItemPosition();
//                                        Bundle bundle=new Bundle();
//                                        bundle.putParcelable("dataItemModel",);
//
//                                        Navigation.findNavController(getView())
//                                                .navigate(R.id.action_marketFragment_to_detailFragment);
//
//                                    }
//                                }));


                                mainListRecyclerViewAdapter.setOnItemClickListener
                                        (new MainListRecyclerViewAdapter.onRecyclerViewItemClickListener() {
                                    @Override
                                    public void onItemClickListener(View view, int position) {

                                        bundle.putParcelable("dataItemModel",dataItems.get(position));
                                        Navigation.findNavController(getView())
                                                .navigate(R.id.action_marketFragment_to_detailFragment,bundle);

                                  Log.e    ( "bundle" ,dataItems.get(position).getName());
                                    }
                                });
                            } else {
                                mainListRecyclerViewAdapter = (MainListRecyclerViewAdapter)
                                        fragmentMarketBinding.marketRecyclerView.getAdapter();
                                mainListRecyclerViewAdapter.updateData(dataItems);
                            }





                    } catch (Exception e) {
                        Log.e("exception", "setupRecyclerView: " + e.getMessage());
                    }

                });
        compositeDisposable.add(disposable);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
    private void searchList(String searchText){
        searchList.clear();
        for (DataItem item : data){
            if (item.getSymbol().toLowerCase().contains(searchText.toLowerCase()) ||
                    item.getName().toLowerCase().contains(searchText.toLowerCase())){
                searchList.add(item);
                mainListRecyclerViewAdapter.updateData(searchList);


            }
        }

    }
}