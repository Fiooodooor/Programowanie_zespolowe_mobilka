package com.votedesk.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.votedesk.R;
import com.votedesk.data.AsynchDownloadImage;
import com.votedesk.data.model.SingleEnvironment;

import java.util.ArrayList;
import java.util.Locale;

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
        TextView textEnvView = rowView.findViewById(R.id.envSingleRowName);
        TextView textDescView = rowView.findViewById(R.id.envSingleRowDesc);
        ImageView bgEnvIcon = rowView.findViewById(R.id.envSingleRowIcon);

        String singleEnvName;
        if(mEnvList.get(position).getName().length()>14) {
            singleEnvName = mEnvList.get(position).getName().substring(0, 14);
            singleEnvName += "..";
        }
        else {
            singleEnvName = mEnvList.get(position).getName();
        }
        textEnvView.setText(singleEnvName);
        textDescView.setText(String.format(Locale.getDefault(),"%s%d", mContext.getString(R.string.env_project_prepend), mEnvList.get(position).getProjectList().size()));

        if (!mEnvList.get(position).getCoverUri().isEmpty()) {
            new AsynchDownloadImage(bgEnvIcon).execute(mEnvList.get(position).getCoverUri());
        }
        return rowView;
    }
}
