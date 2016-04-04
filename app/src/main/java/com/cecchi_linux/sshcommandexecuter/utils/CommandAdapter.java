package com.cecchi_linux.sshcommandexecuter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cecchi_linux.sshcommandexecuter.R;
import com.cecchi_linux.sshcommandexecuter.model.Command;

import java.util.List;

/**
 * Created by Enri on 01/04/2016.
 */
public class CommandAdapter extends ArrayAdapter<Command> {

    public CommandAdapter(Context context, int textViewResourceId,
                             List<Command> objects) {
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
            convertView = inflater.inflate(R.layout.command_row_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.textViewCommandName);
            viewHolder.info = (TextView)convertView.findViewById(R.id.textViewCommandInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Command command = getItem(position);
        viewHolder.name.setText(command.getCommandName());
        viewHolder.info.setText(command.getStrCommand());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView info;
    }

}
