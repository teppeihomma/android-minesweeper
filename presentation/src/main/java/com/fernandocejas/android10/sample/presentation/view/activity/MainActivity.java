package com.fernandocejas.android10.sample.presentation.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerMainComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MainComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.GameFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.StartFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent>,
        StartFragment.OnFragmentInteractionListener,
        GameFragment.OnFragmentInteractionListener {

    private MainComponent mainComponent;

    @Bind(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            addFragment(R.id.container, StartFragment.newInstance());
        }

        this.initializeInjector();
    }

    private void initializeInjector() {
        this.mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void navigateToGameFragment(int level) {
        addFragment(R.id.container, GameFragment.newInstance(level));
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }
}
