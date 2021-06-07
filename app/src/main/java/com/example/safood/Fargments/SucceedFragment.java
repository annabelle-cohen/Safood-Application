package com.example.safood.Fargments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.safood.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SucceedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SucceedFragment extends Fragment {

    public interface FragmentSucceedListener{
        void moveToLogin();
        void moveToMain();
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button moveToLogin;
    private  Button moveToMain;
    private  FragmentSucceedListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SucceedFragment() {
        // Required empty public constructor
    }

    public static SucceedFragment newInstance(String param1, String param2) {
        SucceedFragment fragment = new SucceedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_succeed,container,false);
        moveToLogin = (Button)view.findViewById(R.id.start_now);
        moveToMain = (Button)view.findViewById(R.id.start_later);

        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SucceedFragment.this.mListener.moveToLogin();
            }
        });

        moveToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SucceedFragment.this.mListener.moveToMain();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        try {
            FragmentSucceedListener listener = (FragmentSucceedListener) context;
            if (listener != null)
                this.registerListener(listener);
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + getResources().getString(R.string.mustImplementSuccess));
        }
    }

    public void registerListener(FragmentSucceedListener listener){
        this.mListener = listener;
    }
}