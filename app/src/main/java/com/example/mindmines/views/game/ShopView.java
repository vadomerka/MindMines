package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.factories.EquipFactory;
import com.example.mindmines.services.managers.EquipManager;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.observers.CharObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.CharRepository;
import com.example.mindmines.views.adapters.DialogAdapter;
import com.example.mindmines.views.adapters.ShopEquipChoiceAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShopView extends DialogAdapter {
    private final EquipManager equipManager;
    private Char ch;
    private Equipment eq;
    private SlotType type;
    private ShopEquipChoiceAdapter shopChoiceAdapter;
    private final Resources resources;
    private CharObserver chProxy;
    private CharRepository rep;

    public ShopView(Context context, LayoutInflater layoutInflater, Resources resources) {
        super(context, layoutInflater);
        equipManager = EquipManager.getInstance(context);
        this.resources = resources;
        chProxy = upd -> {updateEq(upd); loadUI();};

        rep = RepositoryService.getCharRepository();
    }

    public void startShop(Integer chId, SlotType type) {
        rep.subscribe(chProxy);

        buildDialog(R.layout.equipment_shop_dialog);
        this.ch = rep.get(chId);
        this.type = type;
        this.eq = ch.getEquipment().getBySlot(type);
        loadUI();

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void updateEq(List<Char> upd) {
        if (upd == null || upd.isEmpty()) return;
        ch = upd.get(0); // CharFactory.getInstance().copy();
        eq = ch.getEquipment().getBySlot(type);
    }

    protected void loadUI() {
        if (eq == null) {
            eq = equipManager.getDefaultSlot(type);
        }

        Log.d("Debug ShopView", "loadUI: " + eq.getLevel());
        List<Equipment> nextPaths = equipManager.getUpgrades(EquipFactory.getInstance().copyEquipment(eq));
        if (nextPaths == null) return;
        if (nextPaths.size() != 1) {
            loadPathChoice(eq, nextPaths);
        } else {
            loadPathUpgrade(eq, nextPaths);
        }
    }

    protected void loadPathChoice(Equipment eq, List<Equipment> nextPaths) {
        View upgradeRow = dialogView.findViewById(R.id.shop_upgrade_row);
        RecyclerView recyclerView = dialogView.findViewById(R.id.shop_equipment_recycler);
        upgradeRow.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        TextView equipName = dialogView.findViewById(R.id.equip_name_text_value);
        equipName.setText(context.getString(R.string.shop_select_first_equipment));

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        shopChoiceAdapter = new ShopEquipChoiceAdapter(nextPaths, selected -> {
            MaterialButton buyEquipmentBtn = dialogView.findViewById(R.id.save_equipment_button);
            buyEquipmentBtn.setText(String.valueOf(selected.getPrice()));
        },
                resources.getColor(R.color.green, context.getTheme()),
                resources.getColor(R.color.purple_background_color, context.getTheme()));
        recyclerView.setAdapter(shopChoiceAdapter);

        MaterialButton buyEquipmentBtn = dialogView.findViewById(R.id.save_equipment_button);
        buyEquipmentBtn.setText("—");
        buyEquipmentBtn.setOnClickListener(v -> {
            Equipment picked = shopChoiceAdapter.getSelectedEquipment();
            if (picked == null) {
                Toast.makeText(context, context.getString(R.string.shop_select_equipment_first_toast), Toast.LENGTH_SHORT).show();
                return;
            }
            buyEquipment(eq, picked, eq.getSlotType());
        });
    }

    protected void loadPathUpgrade(Equipment eq, List<Equipment> nextPaths) {
        View upgradeRow = dialogView.findViewById(R.id.shop_upgrade_row);
        RecyclerView recyclerView = dialogView.findViewById(R.id.shop_equipment_recycler);
        upgradeRow.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        MaterialButton currentEqBtn = dialogView.findViewById(R.id.equipment_btn1);
        MaterialButton upgradedEqBtn = dialogView.findViewById(R.id.equipment_btn2);
        ImageView arrowView = dialogView.findViewById(R.id.arrow);
        TextView equipName = dialogView.findViewById(R.id.equip_name_text_value);

        Equipment upgradedEq = nextPaths.get(0);
        Log.d("Debug ShopView", "loadPathUpgrade: " + upgradedEq.getLevel());

        currentEqBtn.setIcon(getIcon(eq));
        arrowView.setImageResource(R.drawable.ic_arrow_right);
        String t = "Улучшить до Lvl " + (upgradedEq.getLevel()) + "?";
        equipName.setText(t);

        upgradedEqBtn.setIcon(getIcon(upgradedEq));

        Button buyEquipmentBtn = dialogView.findViewById(R.id.save_equipment_button);
        buyEquipmentBtn.setText(String.valueOf(upgradedEq.getPrice()));
        buyEquipmentBtn.setOnClickListener(v -> buyEquipment(eq, upgradedEq, eq.getSlotType()));
    }

    protected void buyEquipment(Equipment eq, Equipment upgradedEq, SlotType type) {
        if (!checkAvailable(upgradedEq)) return;

        ch.unEquip(type);
        ch.equip(upgradedEq);
        Log.d("Debug ShopView", "loadPathUpgrade: " + ch.getEquipment().getBySlot(type).getLevel());
        rep.update(ch);
    }

    protected boolean checkAvailable(Equipment upgradedEq) {
        if (ch.getStatus().getLevel() < upgradedEq.getLevel()) {
            Toast.makeText(context,
                    "Персонаж слишком слаб и не может использовать это снаряжение.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Long coins = UserStatusManager.getInstance(context).getStatus().getCoins();
        if (coins < upgradedEq.getPrice()) {
            Toast.makeText(context, "У вас недостаточно монет.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getIcon(Equipment eq) {
        return context.getDrawable(Integer.parseInt(eq.getImage()));
    }

    @Override
    protected void dismiss() {
        rep.unsubscribe(chProxy);
    }
}
