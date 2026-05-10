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

public class ShopView extends DialogAdapter {
    private Char ch;
    private final EquipManager equipManager;

    public ShopView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
        equipManager = EquipManager.getInstance(context);
    }

    public void startShop(Equipment eq, Char ch, SlotType type) { // , Runnable onEquipmentChanged
        buildDialog(R.layout.equipment_shop_dialog);
        this.ch = ch;
        Equipment upgradedEq = loadUI(eq, type);

        AlertDialog dialog = builder.create();
        Button buyEquipmentBtn = dialogView.findViewById(R.id.save_equipment_button);
        buyEquipmentBtn.setOnClickListener(v -> buyEquipment(eq, upgradedEq, type));
        dialog.show();
    }

    private Equipment loadUI(Equipment eq, SlotType type) {
        MaterialButton currentEqBtn = dialogView.findViewById(R.id.equipment_btn1);
        MaterialButton upgradedEqBtn = dialogView.findViewById(R.id.equipment_btn2);
        ImageView arrowView = dialogView.findViewById(R.id.arrow);
        TextView equipName = dialogView.findViewById(R.id.equip_name_text_value);

        if (eq == null) {
            eq = equipManager.getDefaultSlot(type);
        }
        currentEqBtn.setIcon(getIcon(eq));

        arrowView.setImageResource(R.drawable.ic_arrow_right);

        String t = "Улучшить до Lvl " + (eq.getLevel() + 1) + "?";
        equipName.setText(t);

        Equipment newEq = equipManager.upgrade(EquipFactory.getInstance().copyEquipment(eq));
        upgradedEqBtn.setIcon(getIcon(newEq));
        return newEq;
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
