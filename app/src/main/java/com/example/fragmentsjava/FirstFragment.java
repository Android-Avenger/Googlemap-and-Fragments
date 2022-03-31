package com.example.fragmentsjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentsjava.databinding.FragmentFirstBinding;
import com.google.android.gms.maps.GoogleMap;


public class FirstFragment extends Fragment  {

    private FragmentFirstBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = FragmentFirstBinding.inflate(inflater);

        mBinding.fragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment);
            }
        });

        return mBinding.getRoot();
    }
}