package com.example.exp3_menu.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exp3_menu.R;
import com.example.exp3_menu.databinding.FragmentGalleryBinding;

import java.util.Random;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private FragmentGalleryBinding binding;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        button = (Button) root.findViewById(R.id.roll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = genRand(root);
                TextView result = (TextView) root.findViewById(R.id.result);
                result.setText(num);
            }
        });

        return root;
    }

    private String genRand(View root) {

        SeekBar seek = (SeekBar) root.findViewById(R.id.seekBar);
        Random rand = new Random();
        return Integer.toString(rand.nextInt(seek.getProgress()) + 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {

    }
}