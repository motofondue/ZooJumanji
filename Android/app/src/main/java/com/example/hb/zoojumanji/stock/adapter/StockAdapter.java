package com.example.hb.zoojumanji.stock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hb.zoojumanji.R;
import com.example.hb.zoojumanji.stock.Stock;

import java.util.List;

/**
 * Created by jonas on 07/06/2016.
 */
public class StockAdapter extends ArrayAdapter<Stock> {


    // Default constructor
    public StockAdapter(Context context, int resource, int textViewResourceId, List<Stock> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Generate specific view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_stock_item, null);

        // Get animal from position
        Stock stock = getItem(position);

        // Get multiple elements
        TextView id_text = (TextView) view.findViewById(R.id.stock_id);
        TextView name_text = (TextView) view.findViewById(R.id.stock_name);

        // Insert values
        id_text.setText(String.valueOf(stock.getId()));
        name_text.setText(stock.getName());

        return view;
    }
}