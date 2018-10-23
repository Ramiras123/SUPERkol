package com.example.ramiras.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends Fragment {
    TestView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new TestView(getContext());
        return view;
    }

    void clear() {
        view.drawThread.lock.lock();
        view.drawThread.coords.clear();
        view.drawThread.lock.unlock();
    }
}
