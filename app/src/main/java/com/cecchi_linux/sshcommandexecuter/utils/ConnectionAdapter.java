package com.cecchi_linux.sshcommandexecuter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cecchi_linux.sshcommandexecuter.R;
import com.cecchi_linux.sshcommandexecuter.model.MyConnection;

import java.util.List;


/**
 * Created by Enri on 31/03/2016.
 */
public class ConnectionAdapter extends ArrayAdapter<MyConnection> {

    public ConnectionAdapter(Context context, int textViewResourceId,
                                 List<MyConnection> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.connection_row_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.textViewConnectionName);
            viewHolder.info = (TextView)convertView.findViewById(R.id.textViewConnectionInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyConnection connection = getItem(position);
        viewHolder.name.setText(connection.getConnectionName());
        viewHolder.info.setText(connection.getAddress());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView info;
    }

}
