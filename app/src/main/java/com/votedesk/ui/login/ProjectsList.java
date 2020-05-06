package com.votedesk.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.votedesk.R;
import com.votedesk.data.model.SingleProject;

import java.util.ArrayList;

public class ProjectsList extends Fragment {
    private ArrayList<SingleProject> projectList;
    private int envNumber;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.part_projects_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView projectView = (ListView) view.findViewById(R.id.listViewProject);
        envNumber = getArguments().getInt("envNumber");

        MainCoordinatorActivity activity = (MainCoordinatorActivity) getActivity();
        if(activity != null) {
            activity.setTitle(R.string.pro_title);
            projectList = activity.getGlobalEnvList().get(envNumber).getProjectList();
        }
        else {
            projectList = new ArrayList<>();
        }
        final ProjectsListViewAdapter projectsListAdapter = new ProjectsListViewAdapter(view.getContext(), projectList);
        projectView.setAdapter(projectsListAdapter);

        projectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle result = new Bundle();
                result.putInt("mEnvNumber", envNumber);
                result.putInt("mProjectNumber", position);
                NavHostFragment.findNavController(ProjectsList.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment, result);
            }
        });
    }
}
