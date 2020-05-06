package com.votedesk.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.votedesk.R;
import com.votedesk.data.model.SingleEnvironment;

import java.util.ArrayList;
import java.util.HashMap;

public class EnvListViewAdapter extends BaseAdapter {
    private ArrayList<SingleEnvironment> mEnvList;
    private Context mContext;

    EnvListViewAdapter(Context context, final ArrayList<SingleEnvironment> theData) {
        mEnvList = theData;
        mContext = context;
    }

    public void setData(final ArrayList<SingleEnvironment> theData) {
        mEnvList = theData;
    }
    public ArrayList<SingleEnvironment> getData() {
        return mEnvList;
    }

    @Override
    public int getCount() {
        return mEnvList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEnvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mEnvList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.env_single_row, parent, false);
        TextView textEnvView = (TextView) rowView.findViewById(R.id.envName);
        TextView textDescView = (TextView) rowView.findViewById(R.id.envDesc);

        textEnvView.setText(mEnvList.get(position).getName());
        textDescView.setText("the id is: " + mEnvList.get(position).getId());

        return rowView;
    }
}
