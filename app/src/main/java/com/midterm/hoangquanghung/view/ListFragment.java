package com.midterm.hoangquanghung.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midterm.hoangquanghung.R;
import com.midterm.hoangquanghung.model.Data;
import com.midterm.hoangquanghung.model.DataApi;
import com.midterm.hoangquanghung.viewmodel.DataAdapter;
import com.midterm.hoangquanghung.viewmodel.DataApiService;
import com.midterm.hoangquanghung.viewmodel.DataDao;
import com.midterm.hoangquanghung.viewmodel.DataDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {
    private DataApiService dataApiService;
    private List<Data> data;
    private DataAdapter dataAdapter;
    private DataDao dataDao;
    private RecyclerView rvData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("");
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_data).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                data.clear();
                data.addAll(dataDao.findByName("%" + s + "%"));
                dataAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvData = view.findViewById(R.id.rv_data);
        DataDatabase dogDatabase = DataDatabase.getInstance(getActivity());
        dataDao = dogDatabase.dataDao();
        data = new ArrayList<>();

        if (dataDao.getAllData().size() == 0) {
            dataApiService = new DataApiService();
            dataApiService.getData().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Data>>() {
                @Override
                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Data> data) {
                    data.addAll(data);
                    for(Data item: data) {
                        dataDao.insertAll(item);
                    }
                    dataAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                }
            });
        } else {
            data.addAll(dataDao.getAllData());
        }

        dataAdapter = new DataAdapter(data);
        rvData.setAdapter(dataAdapter);
        rvData.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}