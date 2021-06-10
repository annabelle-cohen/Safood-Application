package com.example.safood.Fargments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.safood.Common.Common;
import com.example.safood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefinitionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinitionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CheckBox vega,infant,gluFree,pets,lacFree;
    private TextInputEditText edtPhone;
    private Button updateBtn,selectAllBtn;
    private FloatingActionButton editBtn;
    private LinearLayout checkLayOut,Buttons;
    private UpdateUserListener mListener;
    private ArrayList<String> results;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public interface  UpdateUserListener{
        void updateChoice(String choice);
    }

    public DefinitionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefinitionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefinitionsFragment newInstance(String param1, String param2) {
        DefinitionsFragment fragment = new DefinitionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_definitions,container,false);

        //Text
        edtPhone = view.findViewById(R.id.userPhone);

        //initial results array
        results = new ArrayList<>();

        //LinearLayout
        checkLayOut = view.findViewById(R.id.checkboxDefintions);
        Buttons = view.findViewById(R.id.ButtonsDef);

        //checkbox options
        vega = view.findViewById(R.id.vegetarianism);
        infant = view.findViewById(R.id.infant);
        gluFree =view.findViewById(R.id.gluten_free);
        pets = view.findViewById(R.id.pets);
        lacFree =view.findViewById(R.id.lactose_free);

        //buttons
        editBtn=view.findViewById(R.id.floating_button_edit_def);
        selectAllBtn = view.findViewById(R.id.btnSelectAll2);
        updateBtn = view.findViewById(R.id.btnContinue2);

        //check who is already chose
        this.updateFirst();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editBtn.setVisibility(View.INVISIBLE);
                checkLayOut.setVisibility(View.VISIBLE);
                vega.setVisibility(View.VISIBLE);
                infant.setVisibility(View.VISIBLE);
                gluFree.setVisibility(View.VISIBLE);
                pets.setVisibility(View.VISIBLE);
                lacFree.setVisibility(View.VISIBLE);
                Buttons.setVisibility(View.VISIBLE);
            }
        });

        selectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vega.setChecked(true);
                infant.setChecked(true);
                gluFree.setChecked(true);
                pets.setChecked(true);
                lacFree.setChecked(true);
                if(!results.contains(getString(R.string.Vegetarianism))){
                    results.add(getString(R.string.Vegetarianism));
                }
                if(!results.contains(getString(R.string.Infant))){
                    results.add(getString(R.string.Infant));
                }
                if(!results.contains(getString(R.string.Gluten_free))){
                    results.add(getString(R.string.Gluten_free));
                }

                if(!results.contains(getString(R.string.Pets))){
                    results.add(getString(R.string.Pets));
                }

                if(!results.contains(getString(R.string.Lactose_free))){
                    results.add(getString(R.string.Lactose_free));
                }

            }
        });

        vega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vega.isChecked()){
                    results.add(getString(R.string.Vegetarianism));
                }else{
                    results.remove(getString(R.string.Vegetarianism));
                }
            }
        });

        infant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infant.isChecked()){
                    results.add(getString(R.string.Infant));
                }else{
                    results.remove(getString(R.string.Infant));
                }
            }
        });

        gluFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gluFree.isChecked()){
                    results.add(getString(R.string.Gluten_free));
                }else{
                    results.remove(getString(R.string.Gluten_free));
                }
            }
        });

        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pets.isChecked()){
                    results.add(getString(R.string.Pets));
                }else{
                    results.remove(getString(R.string.Pets));
                }
            }
        });
        lacFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lacFree.isChecked()){
                    results.add(getString(R.string.Lactose_free));
                }else{
                    results.remove(getString(R.string.Lactose_free));
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            /**need to think how to implements jason file*/
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                Log.d("res size",results.size()+" ------------------------------------------------------------- ");
                if(results.size()>0) {
                    for (String s : results)
                        stringBuilder.append(s + " ");

                    Common.currentUser.setChoice(stringBuilder.toString());
                }else{
                    Common.currentUser.setChoice(getString(R.string.def));
                }
                DefinitionsFragment.this.mListener.updateChoice(Common.currentUser.getChoice());
                editBtn.setVisibility(View.VISIBLE);
                checkLayOut.setVisibility(View.INVISIBLE);
                vega.setVisibility(View.INVISIBLE);
                infant.setVisibility(View.INVISIBLE);
                gluFree.setVisibility(View.INVISIBLE);
                pets.setVisibility(View.INVISIBLE);
                lacFree.setVisibility(View.INVISIBLE);
                Buttons.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try{
            UpdateUserListener listener = (UpdateUserListener)context;
            if(listener != null){
                registerListener(listener);
            }
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + getResources().getString(R.string.mustImplementEditProfile));
        }
    }

    public void registerListener(UpdateUserListener listener) {
        this.mListener = listener;
    }

    void updateFirst(){

        String [] unMarshallin = Common.currentUser.getChoice().split(" ");

        for(int i = 0 ;i<unMarshallin.length;i++){
          if(unMarshallin[i].equals(getString(R.string.Vegetarianism))){
              vega.setChecked(true);
          }
          if(unMarshallin[i].equals(getString(R.string.Infant))){
              infant.setChecked(true);
          }

          if(unMarshallin[i].equals("Gluten")){
                gluFree.setChecked(true);
            }

          if(unMarshallin[i].equals(getString(R.string.Pets))){
              pets.setChecked(true);
          }

          if(unMarshallin[i].equals("Lactose")){
              lacFree.setChecked(true);
          }
        }
    }
}