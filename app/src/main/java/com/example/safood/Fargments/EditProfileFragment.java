package com.example.safood.Fargments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.safood.Common.Common;
import com.example.safood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    public interface EditProfileListener{
        void updatePassword(String password);

    }
    private TextInputEditText edtPassword,edtPhone;
    private FloatingActionButton fButton;
    private Button updateBtn;
    private EditProfileListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View view =inflater.inflate(R.layout.fragment_edit_profile,container,false);
        edtPhone = view.findViewById(R.id.phone_profile_editor);
        edtPassword = view.findViewById(R.id.password_profile_editor);
        fButton=view.findViewById(R.id.floating_button);
        updateBtn=view.findViewById(R.id.update_user_profile);

        edtPhone.setText(Common.currentUser.getPhone());
        edtPhone.setEnabled(false);

        edtPassword.setText(Common.currentUser.getPassword());
        edtPassword.setEnabled(false);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassword.setEnabled(true);
                fButton.setVisibility(View.INVISIBLE);
                updateBtn.setVisibility(View.VISIBLE);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = edtPassword.getText().toString();
               if(isPasswordValid(newPass)){
                   if(!Common.currentUser.getPassword().equals(newPass)){
                       EditProfileFragment.this.mListener.updatePassword(newPass);
                   }
                   edtPassword.setEnabled(false);
                   edtPhone.setEnabled(false);
                   updateBtn.setVisibility(View.INVISIBLE);
                   fButton.setVisibility(View.VISIBLE);
               }

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        try{
            EditProfileListener listener = (EditProfileListener)context;
            if(listener != null){
                registerListener(listener);
            }
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + getResources().getString(R.string.mustImplementEditProfile));
        }
    }

    public void registerListener(EditProfileFragment.EditProfileListener listener) {
        this.mListener = listener;
    }

    private boolean isPasswordValid(String password){
        boolean isValid =true;

        if(!password.isEmpty()){

            if(isPasswordValidLength(password)){
                edtPassword.setError(null);
            }else {
                edtPassword.setError(getString(R.string.maxPassError));
                return isValid =false;
            }

        }else{
            edtPassword.setError(getString(R.string.error_empty));
            isValid=false;
        }
        return isValid;
    }

    public boolean isPasswordValidLength(String password){
        boolean isValid;
        if(password.length() < 8){
            isValid =false;
        }else{
            isValid = true;
        }
        return isValid;
    }
}