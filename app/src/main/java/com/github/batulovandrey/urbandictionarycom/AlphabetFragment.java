package com.github.batulovandrey.urbandictionarycom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.urbandictionarycom.adapter.AlphabetAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlphabetFragment extends Fragment {

    private static final String EXTRA_ALPHABET_LIST = "extra_alphabet_list";

    private List<String> mAlphabetList;
    private OnLetterClickListener mListener;
    private RecyclerView mRecyclerView;
    private AlphabetAdapter mAdapter;

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
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AlphabetAdapter(mAlphabetList);
        mRecyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public interface OnLetterClickListener {

        void onLetterClick(String letter);
    }
}