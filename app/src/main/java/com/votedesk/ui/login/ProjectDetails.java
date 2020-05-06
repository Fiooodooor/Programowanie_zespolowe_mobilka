package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleProject;

public class ProjectDetails extends Fragment {
    private int mProjectNumber;
    private int mProjectId;
    private int mEnvNumber;
    private String descParentEnvName;
    private SingleProject nodeProject;

    private static ImageView descEnvironmentImage;
    private static TextView descEnvironmentName;
    private static TextView descProjectName;
    private static TextView descOwnerName;
    private static TextView descVotesValue;
    private static TextView descProjectDescription;

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

        descEnvironmentImage = view.findViewById(R.id.descEnvironmentImage);
        descEnvironmentName = view.findViewById(R.id.descEnvironmentCaption);
        descProjectName = view.findViewById(R.id.descProjectCaption);
        descOwnerName = view.findViewById(R.id.descOwnerCaption);
        descVotesValue = view.findViewById(R.id.descVotesValueText);
        descProjectDescription = view.findViewById(R.id.descProjectInfoCaption);

        final ProgressBar localPBar = view.findViewById(R.id.descProgressBarLoading);
        localPBar.setVisibility(View.VISIBLE);
        MainCoordinatorActivity activity = (MainCoordinatorActivity) getActivity();
        if(activity != null) {
            activity.setTitle(R.string.desc_title);
            mProjectId = activity.getGlobalEnvList().get(mEnvNumber).getProjectList().get(mProjectNumber).getId();
            descParentEnvName = activity.getGlobalEnvList().get(mEnvNumber).getName();
        }
        else {
            mProjectId = 1;
            descParentEnvName = "1";
        }

        AsyncTask<Void, Void, String> aProjectDet = new ProjectDetails.DetailsNetworkOperation("" + mProjectId, localPBar);
        aProjectDet.execute();

        view.findViewById(R.id.descVoteButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProjectDetails.this)
                        .navigate(R.id.action_BackToProjectsList);
            }
        });
    }

    public void onDetailsDataLoaded() {
        //descEnvironmentImage;
        descEnvironmentName.setText(descParentEnvName);
        descProjectName.setText(nodeProject.getName());
        descOwnerName.setText(nodeProject.getOwnerStr());;
        descVotesValue.setText(nodeProject.getVotesStr());
        descProjectDescription.setText(nodeProject.getContent());
    }

    public class DetailsNetworkOperation extends AsyncTask<Void, Void, String> {
        private String localProjectId;
        private ProgressBar localDetailsProgressBar;
        private RestApiCall restApiData;
        private Result<SingleProject> lResult;

        public DetailsNetworkOperation(String projectId, ProgressBar localDetailsProgressBar) {
            localProjectId = projectId;
            this.localDetailsProgressBar = localDetailsProgressBar;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                Result<SingleProject> result = restApiData.ProjectDetails(localProjectId);
                if (result instanceof Result.Success) {
                    nodeProject = (((Result.Success<SingleProject>) result).getData());
                }
                this.lResult = result;
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
                onDetailsDataLoaded();
            }
            localDetailsProgressBar.setVisibility(View.GONE);
        }
    }
}
