package com.example.mindmines.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public abstract class DialogAdapter {
    protected final Context context;
    protected final LayoutInflater layoutInflater;
    protected final AlertDialog.Builder builder;
    protected View dialogView;

    public DialogAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.builder = new AlertDialog.Builder(context);
    }

    public void buildDialog(int id) {
        dialogView = layoutInflater.inflate(id, null);
        builder.setView(dialogView);
    }
}
