package com.example.techwheelsservicecentre;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePicker extends DialogFragment   {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, (int) System.currentTimeMillis());
        c.set(Calendar.MINUTE, (int) System.currentTimeMillis());
        c.set(Calendar.SECOND, (int) System.currentTimeMillis());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);



        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),year,month,day);
    }
}
