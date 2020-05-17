package com.votedesk.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.votedesk.R;
import com.votedesk.data.AsynchDownloadImage;
import com.votedesk.data.model.SingleProject;

import java.util.ArrayList;

public class ProjectsListViewAdapter extends BaseAdapter {
    private ArrayList<SingleProject> mProjectList;
    private Context mContext;

    ProjectsListViewAdapter(Context context, final ArrayList<SingleProject> theData) {
        mProjectList = theData;
        mContext = context;
    }
    static class ViewHolderItem {
        TextView textProView;
        TextView textDescView;
    }
    public void setData(final ArrayList<SingleProject> theData) {
        mProjectList = theData;
    }
    public ArrayList<SingleProject> getData() {
        return mProjectList;
    }

    @Override
    public int getCount() {
        return mProjectList.size();
    }

    @Override
    public SingleProject getItem(int position) {
        return mProjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mProjectList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowProjView = inflater.inflate(R.layout.pro_single_row, parent, false);
        TextView textProView = (TextView) rowProjView.findViewById(R.id.proName);
        TextView textDescView = (TextView) rowProjView.findViewById(R.id.proDesc);
        ImageView imageProView = (ImageView) rowProjView.findViewById(R.id.proIcon);

        textProView.setText(mProjectList.get(position).getName());
        textDescView.setText(mProjectList.get(position).getContent());

        if (!mProjectList.get(position).getCoverUri().isEmpty()) {
            new AsynchDownloadImage(imageProView).execute(mProjectList.get(position).getCoverUri());
        }

        return rowProjView;
    }
}
