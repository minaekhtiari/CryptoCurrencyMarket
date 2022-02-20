package com.example.coinmarket.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coinmarket.MainActivity;
import com.example.coinmarket.R;
import com.example.coinmarket.databinding.FragmentMarketBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MarketFragment extends Fragment {

    FragmentMarketBinding fragmentMarketBinding;
    MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
         mainActivity= (MainActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view);
    }

    private void setToolbar(View view) {
        //navController
        NavController navController= Navigation.findNavController(view);
        //drawer
     AppBarConfiguration   appBarConfiguration = new AppBarConfiguration.Builder(R.id.marketFragment)
                .setOpenableLayout(mainActivity.drawerLayout).build();
     //toolbar
        Toolbar toolbar=view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.marketFragment){
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                    toolbar.setTitle((R.string.market_fragment));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMarketBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_market,container,false);
        // Inflate the layout for this fragment
        return fragmentMarketBinding.getRoot(); //inflater.inflate(R.layout.fragment_market, container, false);
    }
}