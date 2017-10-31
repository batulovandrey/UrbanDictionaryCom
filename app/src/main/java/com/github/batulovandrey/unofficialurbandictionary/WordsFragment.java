package com.github.batulovandrey.unofficialurbandictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.unofficialurbandictionary.adapter.WordAdapter;
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_WORDS_LIST;

public class WordsFragment extends Fragment implements WordClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<String> mWords;
    private OnWordClickListener mListener;
    private Unbinder mUnbinder;

    public WordsFragment() {
        // Required empty public constructor
    }

    public static WordsFragment newInstance(ArrayList<String> list) {
        WordsFragment fragment = new WordsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(EXTRA_WORDS_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWords = getArguments().getStringArrayList(EXTRA_WORDS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WordAdapter wordAdapter = new WordAdapter(mWords, this);
        mRecyclerView.setAdapter(wordAdapter);
    }

    public void onButtonPressed(String word) {
        if (mListener != null) {
            mListener.onWordClick(word);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWordClickListener) {
            mListener = (OnWordClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWordClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onItemClick(int position) {
        mListener.onWordClick(mWords.get(position));
    }

    public interface OnWordClickListener {

        void onWordClick(String word);
    }
}