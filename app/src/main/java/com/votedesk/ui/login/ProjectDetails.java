package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleProject;

import java.util.ArrayList;

public class ProjectDetails extends Fragment {
    private int mProjectNumber;
    private int mProjectId;
    private int mEnvNumber;
    private SingleProject nodeProject;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState )
    {
        return inflater.inflate(R.layout.part_project_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEnvNumber = getArguments().getInt("mEnvNumber");
        mProjectNumber = getArguments().getInt("mProjectNumber");

        MainCoordinatorActivity activity = (MainCoordinatorActivity) getActivity();
        if(activity != null) {
            activity.setTitle(R.string.desc_title);
            mProjectId = activity.getGlobalEnvList().get(mEnvNumber).getProjectList().get(mProjectNumber).getId();
        }
        else {
            mProjectId = 1;
        }

        AsyncTask<Void, Void, String> aProjectDet = new ProjectDetails.DetailsNetworkOperation("" + mProjectId);
        aProjectDet.execute();

        view.findViewById(R.id.descVoteButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProjectDetails.this)
                        .navigate(R.id.action_BackToProjectsList);
            }
        });
    }

    public class DetailsNetworkOperation extends AsyncTask<Void, Void, String> {
        private String localProjectId;
        private RestApiCall restApiData;
        private Result<SingleProject> lResult;

        public DetailsNetworkOperation(String projectId) {
            localProjectId = projectId;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                this.lResult = restApiData.ProjectDetails(localProjectId);
                if (this.lResult instanceof Result.Success) {
                    nodeProject = (((Result.Success<SingleProject>) this.lResult).getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (lResult instanceof Result.Success) {
                nodeProject = ((Result.Success<SingleProject>) lResult).getData();
            }
        }
    }
}
