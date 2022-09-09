package com.example.exp4;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentFirst extends Fragment {

    private FragmentFirstListener listen;
    private EditText editText;
    private Button button;

    public interface FragmentFirstListener {
        void onInputFirstSent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        editText = v.findViewById(R.id.text_box);
        button = v.findViewById(R.id.enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence input = editText.getText();
                listen.onInputFirstSent(input);
            }
        });

        return v;
    }

    public void updateText(CharSequence text) {
        editText.setText(text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentFirstListener) {
            listen = (FragmentFirstListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listen = null;
    }
}
