package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleProject;

public class VoteResult extends Fragment {
    private int envNumber;
    private static MutableLiveData<SingleProject> projectData = new MutableLiveData<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.part_vote_result, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int voteMark = 50;
        int proNumber = 1;
        this.envNumber = 1;
        if (getArguments() != null) {
            this.envNumber = getArguments().getInt("mEnvNumber");
            proNumber = getArguments().getInt("mProjectNumber");
            voteMark = getArguments().getInt("mVoteMark");
        }
        final TextView voteDetailsResultText = view.findViewById(R.id.voteDetailsResultText);
        final int finalVoteMark = voteMark;
        projectData.observe(this, new Observer<SingleProject>() {
            @Override
            public void onChanged(@Nullable SingleProject projectUpdated) {
                String stats;
                if (projectUpdated == null) {
                    stats = getString(R.string.voteVoteDetailsSuccessYour) + finalVoteMark;
                    voteDetailsResultText.setText(stats);
                }
                else if(projectUpdated.getId() > 0) {
                    stats = getString(R.string.voteVoteDetailsSuccessYour) + finalVoteMark + "%  ";
                    stats += getString(R.string.voteVoteDetailsSuccessTotal) + projectUpdated.getVotesStr();
                    voteDetailsResultText.setText(stats);
                }
            }
        });

        AsyncTask<Void, Void, SingleProject> aVoteResult = new VoteNetworkOperation("" + proNumber, voteMark);
        aVoteResult.execute();

        Button voteGoBackButton = view.findViewById(R.id.voteGoBack);
        voteGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("envNumber", envNumber);
                NavHostFragment.findNavController(VoteResult.this)
                        .navigate(R.id.action_VoteBackToProjectsList, result);
            }
        });
    }

    public static class VoteNetworkOperation extends AsyncTask<Void, Void, SingleProject> {
        private String localProjectId;
        private int localVoteMark;
        private RestApiCall restApiData;
        private RestApiCall restApiProjectData;

        VoteNetworkOperation(String projectId, int theVoteMark) {
            localProjectId = projectId;
            localVoteMark = theVoteMark;
            restApiData = new RestApiCall();
            restApiProjectData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected SingleProject doInBackground(Void... params) {
            try {
                Result<Boolean> voteResult = restApiData.ProjectVote(localProjectId, localVoteMark);
                if (voteResult instanceof Result.Success) {
                    Result<SingleProject> projectDet = restApiProjectData.ProjectDetails(localProjectId);
                    if(projectDet instanceof Result.Success) {
                        return ((Result.Success<SingleProject>) projectDet).getData();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SingleProject();
        }
        @Override
        protected void onPostExecute(SingleProject result) {
            super.onPostExecute(result);
            if(result.getId() > 0) {
                projectData.postValue(result);
            }
        }
    }
}

