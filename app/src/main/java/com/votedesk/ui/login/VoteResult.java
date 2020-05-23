package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleProject;

public class VoteResult extends Fragment {
    private int envNumber;
    private int proNumber;
    private int voteMark;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.part_vote_result, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.envNumber = getArguments().getInt("mEnvNumber");
        this.proNumber = getArguments().getInt("mProjectNumber");
        this.voteMark = getArguments().getInt("mVoteMark");
        AsyncTask<Void, Void, String> aVoteResult = new VoteResult.VoteNetworkOperation("" + proNumber, voteMark);
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

    public class VoteNetworkOperation extends AsyncTask<Void, Void, String> {
        private String localProjectId;
        private int localVoteMark;
        private RestApiCall restApiData;
        private Result<Boolean> lResult;

        public VoteNetworkOperation(String projectId, int theVoteMark) {
            localProjectId = projectId;
            localVoteMark = theVoteMark;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                Result<Boolean> result = restApiData.ProjectVote(localProjectId, localVoteMark);
                this.lResult = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

         //   if (lResult instanceof Result.Success) {
        //        nodeProject = ((Result.Success<SingleProject>) lResult).getData();
         //       onDetailsDataLoaded();
          //  }
            //localDetailsProgressBar.setVisibility(View.GONE);
        }
    }
}