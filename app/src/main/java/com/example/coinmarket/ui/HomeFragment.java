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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coinmarket.Adapters.viewPagerAdapter;
import com.example.coinmarket.MainActivity;
import com.example.coinmarket.R;
import com.example.coinmarket.ViewModel.AppViewModel;
import com.example.coinmarket.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
FragmentHomeBinding fragmentHomeBinding;
MainActivity mainActivity;
AppViewModel appViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity= (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        //viewModel
        appViewModel=new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        setViewPager();

        return fragmentHomeBinding.getRoot();
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
        NavController navController= Navigation.findNavController(view);
        //drawer
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment)
                .setOpenableLayout(mainActivity.drawerLayout).build();
        //toolbar
        Toolbar toolbar=view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.homeFragment){
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                    toolbar.setTitle((R.string.home_fragment));
                }
            }
        });
    }
}