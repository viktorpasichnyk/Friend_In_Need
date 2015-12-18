package com.callisto.friendinneed;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pasichnyk on 15.12.2015.
 */
public class SharedPreference {

    public static final String CONTACTS_SET_KEY = "contacts3";
    public static final String PREFS_NAME = "PRODUCT_APP";

    public SharedPreference(){
        super();
    }

    public void saveNumber(Context context, List<String> listNumbers){
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String gsonNumbers = gson.toJson(listNumbers);
        editor.putString(CONTACTS_SET_KEY ,gsonNumbers);
        editor.commit();
    }

    public void addNumber(Context context, String numberString){
        List<String> numbers = getNumbers(context);
        if (numbers == null)
            numbers = new ArrayList<String>();
        numbers.add(numberString);
        saveNumber(context, numbers);
    }


    public void removeNumber(Context context, String number) {
        ArrayList<String> numbers = getNumbers(context);
        if (numbers != null) {
            numbers.remove(number);
            saveNumber(context, numbers);
        }
    }


    public ArrayList<String> getNumbers(Context context) {
        SharedPreferences settings;
        List<String> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(CONTACTS_SET_KEY)) {
            String jsonFavorites = settings.getString(CONTACTS_SET_KEY, null);
            Gson gson = new Gson();
            String[] nubmers = gson.fromJson(jsonFavorites,
                    String[].class);

            favorites = Arrays.asList(nubmers);
            favorites = new ArrayList<String>(favorites);
        } else
            return null;

        return (ArrayList<String>) favorites;
    }
}


