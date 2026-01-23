package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void editCity(City city);
    }

    private EditCityDialogListener listener;
    private City city;

    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCity = view.findViewById(R.id.edit_text_city_text);
        EditText editProvince = view.findViewById(R.id.edit_text_province_text);

        city = (City) requireArguments().getSerializable("city");

        editCity.setText(city.getName());
        editProvince.setText(city.getProvince());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder.setTitle("Edit City").setView(view).setNegativeButton("Cancel", null).setPositiveButton("Save", (dialog, which) -> {
                    city.setName(editCity.getText().toString());
                    city.setProvince(editProvince.getText().toString());
                    listener.editCity(city);
                }).create();
    }

}
