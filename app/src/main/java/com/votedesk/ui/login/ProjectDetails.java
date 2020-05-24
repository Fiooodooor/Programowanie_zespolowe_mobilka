package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.AsynchDownloadImage;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleProject;

import java.text.DateFormat;
import java.util.Locale;

public class ProjectDetails extends Fragment {
    private int mProjectId;
    private int mEnvNumber;
    private String descParentEnvName;

    private ImageView descEnvironmentImage;
    private TextView descEnvironmentName;
    private TextView descProjectName;
    private TextView descOwnerName;
    private TextView descVotesValue;
    private TextView descProjectDescription;
    private SeekBar voteMark;
    private TextView voteMarkText;
    private TextView voteDateText;
    private Button voteButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState )
    {
        return inflater.inflate(R.layout.part_project_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int mProjectNumber = 1;
        if (getArguments() != null) {
            mEnvNumber = getArguments().getInt("mEnvNumber");
            mProjectNumber = getArguments().getInt("mProjectNumber");
        }
        voteMark = view.findViewById(R.id.descProjectSeekBar);
        voteMarkText = view.findViewById(R.id.descProjectSeekBarValue);
        voteButton = view.findViewById(R.id.descVoteButtonId);
        voteDateText = view.findViewById(R.id.descProjectSeekBarDate);
        voteMarkText.setVisibility(View.GONE);
        voteMark.setVisibility(View.GONE);
        voteButton.setVisibility(View.GONE);

        descEnvironmentImage = view.findViewById(R.id.descEnvironmentImage);
        descEnvironmentName = view.findViewById(R.id.descEnvironmentCaption);
        descProjectName = view.findViewById(R.id.descProjectCaption);
        descOwnerName = view.findViewById(R.id.descOwnerCaption);
        descVotesValue = view.findViewById(R.id.descVotesValueText);
        descProjectDescription = view.findViewById(R.id.descProjectInfoCaption);
        Button goBackButton = view.findViewById(R.id.descGoBack);

        final ProgressBar localPBar = view.findViewById(R.id.descProgressBarLoading);
        localPBar.setVisibility(View.VISIBLE);
        MainCoordinatorActivity activity = (MainCoordinatorActivity) getActivity();
        if(activity != null) {
            mProjectId = activity.getGlobalEnvList().get(mEnvNumber).getProjectList().get(mProjectNumber).getId();
            descParentEnvName = activity.getGlobalEnvList().get(mEnvNumber).getName();
        }
        else {
            mProjectId = 1;
            descParentEnvName = "1";
        }
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("envNumber", mEnvNumber);
                NavHostFragment.findNavController(ProjectDetails.this)
                        .navigate(R.id.action_BackToProjectsList, result);
            }
        });
        AsyncTask<Void, Void, SingleProject> aProjectDet = new ProjectDetails.DetailsNetworkOperation("" + mProjectId, localPBar);
        aProjectDet.execute();
    }

    private void onDetailsDataLoaded(SingleProject nodeProject) {
        if (!nodeProject.getCoverUri().isEmpty()) {
            new AsynchDownloadImage(descEnvironmentImage).execute(nodeProject.getCoverUri());
        }

        descEnvironmentName.setText(descParentEnvName);
        descProjectName.setText(nodeProject.getName());
        descOwnerName.setText(nodeProject.getOwnerStr());
        descVotesValue.setText(nodeProject.getVotesStr());
        descProjectDescription.setText(nodeProject.getContent());

        if(nodeProject.isCan_vote()) {
            voteMarkText.setVisibility(View.VISIBLE);
            voteMark.setVisibility(View.VISIBLE);
            voteMark.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {  }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {  }
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String progressText = "" + (progress*10) + "%";
                    voteMarkText.setText(progressText);
                }
            });
            voteButton.setVisibility(View.VISIBLE);
            voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle result = new Bundle();
                    result.putInt("mEnvNumber", mEnvNumber);
                    result.putInt("mProjectNumber", mProjectId);
                    result.putInt("mVoteMark", voteMark.getProgress()*10);
                    NavHostFragment.findNavController(ProjectDetails.this)
                            .navigate(R.id.action_VoteOnProject, result);
                }
            });
        }
        if(nodeProject.isVote_opened()) {
            voteDateText.setText(String.format(Locale.getDefault(),"%s%s", getString(R.string.descVoteEnd), nodeProject.getVote_closing()));
            voteMark.setEnabled(true);
            voteButton.setEnabled(true);
        }
        else {
            voteDateText.setText(String.format(Locale.getDefault(),"%s%s", getString(R.string.descVoteStart), nodeProject.getVote_starting()));
            voteMark.setEnabled(false);
            voteButton.setEnabled(false);
        }
    }

    public class DetailsNetworkOperation extends AsyncTask<Void, Void, SingleProject> {
        private String localProjectId;
        private ProgressBar localDetailsProgressBar;
        private RestApiCall restApiData;

        DetailsNetworkOperation(String projectId, ProgressBar progressBar) {
            localProjectId = projectId;
            localDetailsProgressBar = progressBar;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected SingleProject doInBackground(Void... params) {
            try {
                Result<SingleProject> result = restApiData.ProjectDetails(localProjectId);
                if (result instanceof Result.Success) {
                    return (((Result.Success<SingleProject>) result).getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(SingleProject result) {
            super.onPostExecute(result);

            if (result != null) {
                onDetailsDataLoaded(result);
            }
            localDetailsProgressBar.setVisibility(View.GONE);
        }
    }
}
