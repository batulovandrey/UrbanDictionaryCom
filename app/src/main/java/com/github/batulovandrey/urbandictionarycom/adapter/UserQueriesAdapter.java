package com.github.batulovandrey.urbandictionarycom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.MainActivity;
import com.github.batulovandrey.urbandictionarycom.R;
import com.github.batulovandrey.urbandictionarycom.bean.UserQuery;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class UserQueriesAdapter extends BaseAdapter {

    private static final String EXTRA_SEARCH_QUERY = "extra_search_query";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<UserQuery> mQueries;
    private Realm mRealm;

    public UserQueriesAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRealm = new RealmManager(context, "queries").getRealm();
        mQueries = mRealm.where(UserQuery.class).findAll();
    }

    @Override
    public int getCount() {
        return mQueries.size();
    }

    @Override
    public Object getItem(int position) {
        return mQueries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        UserQuery query = (UserQuery) getItem(position);
        if (view == null) {
            view = mInflater.inflate(R.layout.query_item, parent, false);
            holder = new ViewHolder();
            holder.query = view.findViewById(R.id.query_text_view);
            holder.query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra(EXTRA_SEARCH_QUERY, holder.query.getText().toString());
                    mContext.startActivity(intent);
                }
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.query.setText(query.getQuery());
        return view;
    }

    public void filter(String text) {
        if (text.length() > 0) {
            mQueries = mRealm.where(UserQuery.class).contains("query", text.toLowerCase()).findAll();
        } else {
            mQueries = mRealm.where(UserQuery.class).findAll();
        }
        notifyDataSetChanged();
    }

    public void saveQueryToRealm(final String query) {
        boolean isExist = false;
        for (UserQuery userQuery : mQueries) {
            if (userQuery.getQuery().equals(query.toLowerCase())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    UserQuery userQuery = realm.createObject(UserQuery.class);
                    userQuery.setQuery(query.toLowerCase());
                }
            });
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView query;
    }
}