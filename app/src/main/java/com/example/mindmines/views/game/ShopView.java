package com.example.mindmines.views.game;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.game.expeditions.ExpeditionLocation;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.managers.EquipManager;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.adapters.DialogAdapter;
import com.example.mindmines.views.adapters.IntervalPickerAdapter;
import com.example.mindmines.views.adapters.LocationAdapter;
import com.example.mindmines.views.game.expedition.ExpeditionView;
import com.example.mindmines.views.utils.ViewsUtils;
import com.google.android.material.button.MaterialButton;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
