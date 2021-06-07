package com.example.safood.Fargments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safood.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeContentFragment extends Fragment {

    public interface FragmentHomeContentListener{
        void moveToFoodNews();
        void moveToAllNotifications();
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView food_news;
    private CardView all_notifications;
    private FragmentHomeContentListener mListener;

    public HomeContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeContentFragment newInstance(String param1, String param2) {
        HomeContentFragment fragment = new HomeContentFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_content,container,false);

        food_news = (CardView)view.findViewById(R.id.foodNews);
        all_notifications= (CardView)view.findViewById(R.id.notifications);

        food_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeContentFragment.this.mListener.moveToFoodNews();
            }
        });

        all_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeContentFragment.this.mListener.moveToAllNotifications();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try{
            FragmentHomeContentListener listener = (FragmentHomeContentListener)context;

            if(listener != null){
                this.registerListener(listener);
            }
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + getResources().getString(R.string.mustImplement));
        }
    }

    public void registerListener(FragmentHomeContentListener listener) {
        this.mListener = listener;
    }
}