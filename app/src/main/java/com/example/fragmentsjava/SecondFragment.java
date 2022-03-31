package com.example.fragmentsjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentsjava.databinding.FragmentSecondBinding;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding mBinding;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ){
        mBinding = FragmentSecondBinding.inflate(getLayoutInflater());
        mBinding.fragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_thirdFragment);
            }
        });
        return mBinding.getRoot();
    }
}