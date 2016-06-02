package com.udacity.gradle.builditbigger;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;



public abstract class BaseActivity extends FragmentActivity {

    protected ProgressDialog progress;
    protected ActionBar actionBar;
    protected Fragment currentFragment;

    public BaseActivity() {
        progress = ProgressDialog.newInstance (R.string.dlg_wait_please);
    }

    public synchronized void showProgress() {
        // Fragment transactions are committed asynchronously. Make sure last hide Operation is complete.
        getFragmentManager().executePendingTransactions();

        if (!progress.isAdded()) {
            progress.show(getFragmentManager(), null);
        }
    }

    public synchronized void showProgress(int text_id) {
        // Fragment transactions are committed asynchronously. Make sure last hide Operation is complete.
        getFragmentManager().executePendingTransactions();
        progress = ProgressDialog.newInstance (text_id);

        if (!progress.isAdded()) {
            progress.show(getFragmentManager(), null);
        }

    }

    public synchronized void setProgressMessage (final int msgId) {
        if (progress.isAdded()) {
            final android.app.ProgressDialog progDlg = (android.app.ProgressDialog)progress.getDialog();
            Runnable changeMessage = new Runnable() {
                @Override
                public void run() {
                    progDlg.setMessage(getString(msgId));
                }
            };
            runOnUiThread(changeMessage);
        }
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            // Revert to original Progress Message.
            final android.app.ProgressDialog progDlg = (android.app.ProgressDialog) progress.getDialog();
            if (progDlg != null) {
                Runnable changeMessage = new Runnable() {
                    @Override
                    public void run() {
                        progDlg.setMessage(getString(R.string.dlg_wait_please));
                    }
                };
                runOnUiThread(changeMessage);
            }
            progress.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        actionBar = getActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void navigateToParent() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        if (intent == null) {
            finish();
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }



    private FragmentTransaction buildTransaction() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return transaction;
    }

    public void hideActionBarProgress() {
        setVisibilityActionBarProgress(false);
    }

    public void showActionBarProgress() {
        setVisibilityActionBarProgress(true);
    }

    public void setVisibilityActionBarProgress(boolean visibility) {
        setProgressBarIndeterminateVisibility(visibility);
    }
}