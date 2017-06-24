package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MainComponent;
import com.fernandocejas.android10.sample.presentation.presenter.GamePresenter;
import com.fernandocejas.android10.sample.presentation.view.GameView;
import com.fernandocejas.android10.sample.presentation.view.adapter.GameBoardAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameFragment extends BaseFragment implements GameView, AdapterView.OnItemClickListener {

    private static final String ARG_LEVEL = "level";

    @Inject
    GamePresenter gamePresenter;

    private int level;

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.toggle_button_flag)
    ToggleButton toggleButton;
    @Bind(R.id.text_view_level)
    TextView textViewLevel;
    @Bind(R.id.grid_view)
    GridView gridView;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(int level) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            level = getArguments().getInt(ARG_LEVEL);
        }
        this.getComponent(MainComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, fragmentView);

        gridView.setOnItemClickListener(this);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.gamePresenter.setView(this);
        if (savedInstanceState == null) {
        }
        gridView.setAdapter(new GameBoardAdapter(getActivity()));
        gamePresenter.start(currentLevel());
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

    @Override
    public void navigateToStart() {
        // TODO
    }

    @Override
    public void showGameOver() {
        // TODO
        Toast.makeText(getActivity(), "Game over", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGameClear() {
        // TODO
        Toast.makeText(getActivity(), "Clear!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateGameBoard(GameBoard gameBoard) {
        gridView.setNumColumns(gameBoard.getWidth());
        GameBoardAdapter adapter = (GameBoardAdapter) gridView.getAdapter();
        adapter.setGameBoard(gameBoard);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int x = position % gridView.getNumColumns();
        int y = position / gridView.getNumColumns();
        if (toggleButton.isChecked()) {
            // FIXME y <=> x
            gamePresenter.flag(y, x);
        }
        else {
            // FIXME y <=> x
            gamePresenter.open(y, x);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private int currentLevel() {
        final Bundle args = getArguments();
        return args.getInt(ARG_LEVEL);
    }
}
