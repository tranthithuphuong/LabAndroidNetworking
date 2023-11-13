package com.example.lab3.bai1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.lab3.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contactsList;

    public ContactAdapter(Context context, ArrayList<Contact> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHoder{
        TextView tvname, tvEmail, tvMobile;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHoder viewHoder;
        if (convertView == null){
            viewHoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHoder.tvname = convertView.findViewById(R.id.name);
            viewHoder.tvEmail = convertView.findViewById(R.id.email);
            viewHoder.tvMobile = convertView.findViewById(R.id.mobile);
            convertView.setTag(viewHoder);

        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        Contact contact = contactsList.get(position);
        viewHoder.tvname.setText(contact.getName());
        viewHoder.tvEmail.setText(contact.getEmail());
        viewHoder.tvMobile.setText(contact.getMobile());
        return convertView;


    }
}
