package com.example.mindmines.views.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.mindmines.R;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.managers.EquipManager;
import com.example.mindmines.views.adapters.DialogAdapter;

public class ShopView extends DialogAdapter {
    public ShopView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public void startShop(Equipment eq) {
        buildDialog(R.layout.equipment_shop_dialog);
        loadUI(eq);

        AlertDialog dialog = builder.create();
        Button buyEquipmentBtn = dialogView.findViewById(R.id.create_expedition_button);
        buyEquipmentBtn.setOnClickListener(v -> {

            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadUI(Equipment eq) {
        Equipment newEq = EquipManager.getInstance().upgrade(eq);
    }
}
