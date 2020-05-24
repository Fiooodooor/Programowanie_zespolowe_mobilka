package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;


public class VoteResult extends Fragment {
    private int envNumber;

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

        AsyncTask<Void, Void, Boolean> aVoteResult = new VoteNetworkOperation("" + proNumber, voteMark);
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

    public static class VoteNetworkOperation extends AsyncTask<Void, Void, Boolean> {
        private String localProjectId;
        private int localVoteMark;
        private RestApiCall restApiData;

        VoteNetworkOperation(String projectId, int theVoteMark) {
            localProjectId = projectId;
            localVoteMark = theVoteMark;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Result<Boolean> result = restApiData.ProjectVote(localProjectId, localVoteMark);
                if (result instanceof Result.Success) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }
    }
}