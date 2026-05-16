package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.mindmines.services.managers.CharManager;
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
    private MaterialButton buyEquipmentBtn;
    private TextView levelWarningTv;
    private TextView coinWarningTv;
    private ShopEquipChoiceAdapter shopChoiceAdapter;
    private final Resources resources;
    private final CharObserver chProxy;
    private final CharRepository rep;
    private final CharManager chm;
    private final UserStatusManager usm;

    public ShopView(Context context, LayoutInflater layoutInflater, Resources resources) {
        super(context, layoutInflater);
        equipManager = EquipManager.getInstance(context);
        this.resources = resources;
        chProxy = upd -> {updateEq(upd); loadUI();};

        rep = RepositoryService.getCharRepository();
        chm = CharManager.getInstance(context);
        usm = UserStatusManager.getInstance(context);
    }

    public void startShop(Integer chId, SlotType type) {
        rep.subscribe(chProxy);

        buildDialog(R.layout.equipment_shop_dialog);
        this.ch = rep.get(chId);
        this.type = type;
        this.eq = ch.getEquipment().getBySlot(type);
        if (eq != null) Log.d("Debug ShopView", "startShop: " + eq.getLevel());

        buyEquipmentBtn = dialogView.findViewById(R.id.save_equipment_button);
        levelWarningTv = dialogView.findViewById(R.id.too_low_level_char_warning);
        coinWarningTv = dialogView.findViewById(R.id.too_few_coins_userStatus_warning);

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

        buyEquipmentBtn.setText("—");
        buyEquipmentBtn.setOnClickListener(v -> {
            Equipment picked = shopChoiceAdapter.getSelectedEquipment();
            if (picked == null) {
                Toast.makeText(context, context.getString(R.string.shop_select_equipment_first_toast), Toast.LENGTH_SHORT).show();
                return;
            }
            buyEquipment(picked, eq.getSlotType());
            canBuyUpdate(picked);
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

        currentEqBtn.setIcon(getIcon(eq));
        arrowView.setImageResource(R.drawable.ic_arrow_right);
        String t = "Улучшить до Lvl " + (upgradedEq.getLevel()) + "?";
        equipName.setText(t);

        upgradedEqBtn.setIcon(getIcon(upgradedEq));

        buyEquipmentBtn.setText(String.valueOf(upgradedEq.getPrice()));
        buyEquipmentBtn.setOnClickListener(v -> buyEquipment(upgradedEq, eq.getSlotType()));
        canBuyUpdate(upgradedEq);
    }

    public void canBuyUpdate(Equipment eq) {
        if (chm.cantBuyEquipment(ch, eq)) {
            buyEquipmentBtn.setEnabled(false);
            levelWarningTv.setVisibility(View.VISIBLE);
            coinWarningTv.setVisibility(View.GONE);
        } else if (usm.cantBuyEquipment(eq)) {
            buyEquipmentBtn.setEnabled(false);
            levelWarningTv.setVisibility(View.GONE);
            coinWarningTv.setVisibility(View.VISIBLE);
        } else {
            buyEquipmentBtn.setEnabled(true);
            levelWarningTv.setVisibility(View.GONE);
            coinWarningTv.setVisibility(View.GONE);
        }
    }

    protected void buyEquipment(Equipment upgradedEq, SlotType type) {
        if (!checkAvailable(upgradedEq)) return;

        EquipManager.getInstance(context).buyEquipment(ch, upgradedEq, type);
    }

    protected boolean checkAvailable(Equipment upgradedEq) {
        if (chm.cantBuyEquipment(ch, upgradedEq)) {
            Toast.makeText(context,
                    "Персонаж слишком слаб и не может использовать это снаряжение.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (usm.cantBuyEquipment(upgradedEq)) {
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
