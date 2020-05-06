package com.votedesk.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;

import com.votedesk.data.LoginRepository;
import com.votedesk.data.Result;
import com.votedesk.data.model.LoggedInUser;
import com.votedesk.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        if(loginRepository.isInProgress())
            return;

        AsyncTask<Void, Void, String> aTask = new LoginNetworkOperation(loginRepository, username, password);
        aTask.execute();
    }

    public class LoginNetworkOperation extends AsyncTask<Void, Void, String> {
        private LoginRepository loginRepository;
        private Result<LoggedInUser> lResult;
        private String login;
        private String password;

        public LoginNetworkOperation(LoginRepository loginRepository, String login, String password) {
            this.loginRepository = loginRepository;
            this.login = login;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                this.lResult = loginRepository.login(login, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //lResult

            if (lResult instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) lResult).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(), data.getWrappedTokenId())));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else {
            return !username.trim().isEmpty();
        }
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
