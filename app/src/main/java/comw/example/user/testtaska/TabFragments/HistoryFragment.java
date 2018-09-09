package comw.example.user.testtaska.TabFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import comw.example.user.testtaska.Adapter.MyListCursorAdapter;
import comw.example.user.testtaska.DataBase.DataBaseProvider;
import comw.example.user.testtaska.Body.ImageLinkObject;
import comw.example.user.testtaska.R;

public class HistoryFragment extends Fragment {
    final Uri LINK_URI = Uri
            .parse("content://comw.example.user.testbigdigappa.DataBase/linksImageTab");
    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyListCursorAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history,container,false);
        String sortBy = sharedPreferences.getString("sort",DataBaseProvider.COLUMN_LINK);

        recyclerView = v.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyListCursorAdapter(getContext(),getCursor(sortBy));
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

        setHasOptionsMenu(true);

        return v;
    }

    public Cursor getCursor(String sortBy) {
        Cursor cursor = getContext().getContentResolver().query(LINK_URI,null,null,null,sortBy);
        return cursor;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("sort", Activity.MODE_PRIVATE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_sort_status):

                sharedPreferences.edit().putString("sort", DataBaseProvider.COLUMN_STATUS).commit();
                restartFragment();
                break;

            case (R.id.action_sort_time):
                sharedPreferences.edit().putString("sort", DataBaseProvider.COLUMN_TIME_OF_USE + " DESC").commit();
                restartFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void restartFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new HistoryFragment()).commit();
    }
}