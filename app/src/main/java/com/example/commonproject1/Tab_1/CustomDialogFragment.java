package com.example.commonproject1.Tab_1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.commonproject1.R;

public class CustomDialogFragment extends DialogFragment {

    public CustomDialogFragment() {    }
    private String name,number;
    private boolean isValid = false;
    private EditText input_Name, input_Number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.popup_input, null);
        input_Name = view.findViewById(R.id.input_name);
        input_Number = view.findViewById(R.id.input_number);
        builder.setView(view);
        builder.setPositiveButton(R.string.commit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        name = input_Name.getText().toString();
                        number = input_Number.getText().toString();
                        if (name.matches("") | number.matches("")) {
                            Toast.makeText(getActivity(), "Enter both Name and Number", Toast.LENGTH_LONG).show();
                        }
                        else { isValid = true; }
                    }
                });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }

    public String getInputName() { return name; }
    public String getInputNumber() { return number; }
    public boolean isValid() { return isValid; }
}
