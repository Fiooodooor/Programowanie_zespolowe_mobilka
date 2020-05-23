package com.votedesk.ui.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.RestApiCall;
import com.votedesk.data.Result;
import com.votedesk.data.model.SingleEnvironment;

import java.util.ArrayList;

public class EnvironmentsList extends Fragment {
    //private MutableLiveData<ArrayList<SingleEnvironment>> envList = new MutableLiveData<>();
    private ArrayList<SingleEnvironment> envList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        return inflater.inflate(R.layout.part_environments_grid, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

   //     MainCoordinatorActivity mActivity = (MainCoordinatorActivity) getActivity();
     //   if(mActivity != null) {
  //          mActivity.setTitle(R.string.env_title);
   //     }

        final ProgressBar localPBar = view.findViewById(R.id.envProgressbarLoading);
        final GridView envView = view.findViewById(R.id.gridViewEnv);
        localPBar.setVisibility(View.VISIBLE);
        envList = new ArrayList<>();
        /*envList.observe(this, new Observer<ArrayList<SingleEnvironment>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SingleEnvironment> environmentList) {
                if (environmentList == null) {
                    return;
                }
                envProgBar.setVisibility(View.GONE);
            }
        });*/


        final EnvListViewAdapter envViewAdapter = new EnvListViewAdapter(view.getContext(), envList);
        envView.setAdapter(envViewAdapter);

        AsyncTask<Void, Void, String> aEnvTask = new EnvNetworkOperation(envView, localPBar);
        aEnvTask.execute();

        envView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(!envList.get(position).getProjectList().isEmpty()) {
                    Bundle result = new Bundle();
                    result.putInt("envNumber", position);
                    NavHostFragment.findNavController(EnvironmentsList.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment, result);
                }
                else {
                    String emptyEnv = getString(R.string.env_no_projects);
                    Toast.makeText(getActivity(), emptyEnv, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class EnvNetworkOperation extends AsyncTask<Void, Void, String> {
        private RestApiCall restApiData;
        private Result<ArrayList<SingleEnvironment>> lResult;
        private GridView viewAdapter;
        private ProgressBar envProgressBar;

        public EnvNetworkOperation(GridView theEnvView, ProgressBar envProgressBar ) {
            viewAdapter = theEnvView;
            this.envProgressBar = envProgressBar;
            restApiData = new RestApiCall();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                Result<ArrayList<SingleEnvironment>> result = restApiData.ListEnvironments();
                if (result instanceof Result.Success) {
                    envList = (((Result.Success<ArrayList<SingleEnvironment>>) result).getData());
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
                ArrayList<SingleEnvironment> data = ((Result.Success<ArrayList<SingleEnvironment>>) lResult).getData();
                ((EnvListViewAdapter) viewAdapter.getAdapter()).setData(data);
                viewAdapter.invalidateViews();
                ((EnvListViewAdapter) viewAdapter.getAdapter()).notifyDataSetChanged();
                ((EnvListViewAdapter) viewAdapter.getAdapter()).notifyDataSetInvalidated();
                envList = data;
            } else {
                envList = new ArrayList<SingleEnvironment>();
            }
            envProgressBar.setVisibility(View.GONE);
            MainCoordinatorActivity activity = (MainCoordinatorActivity) getActivity();
            activity.setGlobalEnvList(envList);
        }
    }
}
