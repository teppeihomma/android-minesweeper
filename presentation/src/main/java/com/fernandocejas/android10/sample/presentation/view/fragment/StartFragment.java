package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MainComponent;
import com.fernandocejas.android10.sample.presentation.presenter.StartPresenter;
import com.fernandocejas.android10.sample.presentation.view.StartView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartFragment extends BaseFragment implements StartView {

    @Inject
    StartPresenter startPresenter;

    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }

    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        this.getComponent(MainComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_start, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.startPresenter.setView(this);
        if (savedInstanceState == null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.button_low_level)
    void onLowLevelClicked() {
        startPresenter.onLowLevelClicked();
    }

    @OnClick(R.id.button_middle_level)
    void onMiddleLevelClicked() {
        startPresenter.onMiddleLevelClicked();
    }

    @OnClick(R.id.button_high_level)
    void onHighLevelClicked() {
        startPresenter.onHighLevelClicked();
    }

    @Override
    public void navigateToGame(int level) {
        mListener.navigateToGameFragment(level);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void navigateToGameFragment(int level);
    }
}
