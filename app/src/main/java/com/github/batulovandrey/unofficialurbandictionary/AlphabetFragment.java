package com.github.batulovandrey.unofficialurbandictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetAdapter;
import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_ALPHABET_LIST;

public class AlphabetFragment extends Fragment implements AlphabetClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<String> mAlphabetList;
    private OnLetterClickListener mListener;
    private Unbinder mUnbinder;

    public AlphabetFragment() {
        // Required empty public constructor
    }

    public static AlphabetFragment newInstance(ArrayList<String> list) {
        AlphabetFragment fragment = new AlphabetFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(EXTRA_ALPHABET_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlphabetList = getArguments().getStringArrayList(EXTRA_ALPHABET_LIST);
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
        AlphabetAdapter adapter = new AlphabetAdapter(mAlphabetList, this);
        mRecyclerView.setAdapter(adapter);
    }

    public void onButtonPressed(String letter) {
        if (mListener != null) {
            mListener.onLetterClick(letter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLetterClickListener) {
            mListener = (OnLetterClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLetterClickListener");
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
        mListener.onLetterClick(mAlphabetList.get(position));
    }

    public interface OnLetterClickListener {

        void onLetterClick(String letter);
    }
}