package com.example.proektdva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Item> arrayList;
    ArrayList<Item> arrayListFull;

    public MyAdapter(Context context, ArrayList<Item> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        arrayListFull = new ArrayList<>(arrayList);
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row, null);
        TextView imeK = (TextView) convertView.findViewById(R.id.imeKompanija);
        TextView adresaK = (TextView) convertView.findViewById(R.id.adresaKompanija);
        TextView telefonK = (TextView) convertView.findViewById(R.id.telefonKompanija);
        TextView websK = (TextView) convertView.findViewById(R.id.webSKompanija);

        Item item = arrayList.get(position);
        imeK.setText(item.getItemName());
        adresaK.setText(item.getItemAddress());
        telefonK.setText(item.getItemPhone());
        websK.setText(item.getItemWebsite());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Item> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(arrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Item item : arrayListFull){
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
