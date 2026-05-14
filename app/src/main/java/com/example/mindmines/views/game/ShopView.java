package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.factories.EquipFactory;
import com.example.mindmines.services.managers.EquipManager;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.adapters.DialogAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShopView extends DialogAdapter {
    private final EquipManager equipManager;
    private Char ch;

    public ShopView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
        equipManager = EquipManager.getInstance(context);
    }

    public void startShop(Equipment eq, Char ch, SlotType type) {
        buildDialog(R.layout.equipment_shop_dialog);
        this.ch = ch;
        loadUI(eq, type);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void loadUI(Equipment eq, SlotType type) {
        if (eq == null) {
            eq = equipManager.getDefaultSlot(type);
        }

        List<Equipment> nextPaths = equipManager.getUpgrades(EquipFactory.getInstance().copyEquipment(eq));
        if (nextPaths == null) return;
        if (nextPaths.size() != 1) {
            loadPathChoice();
        } else {
            loadPathUpgrade(eq, nextPaths);
        }
    }

    protected void loadPathChoice() {

    }

    protected void loadPathUpgrade(Equipment eq, List<Equipment> nextPaths) {
        MaterialButton currentEqBtn = dialogView.findViewById(R.id.equipment_btn1);
        MaterialButton upgradedEqBtn = dialogView.findViewById(R.id.equipment_btn2);
        ImageView arrowView = dialogView.findViewById(R.id.arrow);
        TextView equipName = dialogView.findViewById(R.id.equip_name_text_value);

        Equipment upgradedEq = nextPaths.get(0);

        currentEqBtn.setIcon(getIcon(eq));
        arrowView.setImageResource(R.drawable.ic_arrow_right);
        String t = "Улучшить до Lvl " + (eq.getLevel() + 1) + "?";
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
        RepositoryService.getCharRepository().update(ch);
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
}
