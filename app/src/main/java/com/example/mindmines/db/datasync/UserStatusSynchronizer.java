package com.example.mindmines.db.datasync;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import com.example.mindmines.models.UserStatus;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.views.observers.UserStatusObserver;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;

public class UserStatusSynchronizer implements DataSynchronizer, UserStatusObserver {
    private final String TAG = "Debug UserStatusSynchronizer";
    private final RxDataStore<Preferences> dataStore;
    private final Executor executor = MoreExecutors.directExecutor();

    // Ключи для каждого поля
    private static final Preferences.Key<Integer> USER_ID = PreferencesKeys.intKey("user_id");
    private static final Preferences.Key<Integer> LEVEL =
            PreferencesKeys.intKey("level");
    private static final Preferences.Key<Long> EXPERIENCE =
            PreferencesKeys.longKey("experience");
    private static final Preferences.Key<Long> MAX_EXPERIENCE =
            PreferencesKeys.longKey("max_experience");

    private static final Preferences.Key<Long> COINS=
            PreferencesKeys.longKey("coins");

    public UserStatusSynchronizer(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "user_status").build();
    }

    public void loadFromDB() {
        this.load().subscribe(new io.reactivex.rxjava3.core.SingleObserver<UserStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull UserStatus userStatus) {
                Log.d(TAG, "onLoadSuccess: status loaded");
                UserStatusManager.setStatus(userStatus);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d(TAG, String.format("onLoadError: %s", e));
                e.printStackTrace();
            }
        });
    }

    public Single<UserStatus> load() {
        UserStatus defaultStatus = new UserStatus();
        return dataStore.data().firstOrError()
                .map(prefs -> {
                    // Извлекаем значения по ключам, или используем значения по умолчанию
                    int userId = prefs.get(USER_ID) != null ? prefs.get(USER_ID) : defaultStatus.getUserId();
                    int level = prefs.get(LEVEL) != null ? prefs.get(LEVEL) : defaultStatus.getLevel();
                    long exp = prefs.get(EXPERIENCE) != null ? prefs.get(EXPERIENCE) : defaultStatus.getExperience();
                    long maxExp = prefs.get(MAX_EXPERIENCE) != null ? prefs.get(MAX_EXPERIENCE) : defaultStatus.getMaxExperience();
                    long coins = prefs.get(COINS) != null ? prefs.get(COINS) : defaultStatus.getCoins();
                    return new UserStatus(userId, level, exp, maxExp, coins);
                });
    }

    public void saveToDB() {
        UserStatus newStatus = UserStatusManager.getStatus();
        Log.d(TAG, "save start");
        this.save(newStatus).subscribe(new SingleObserver<Preferences>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onSuccess(@NonNull Preferences preferences) {
                Log.d(TAG, "onSaveSuccess: status saved");
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, String.format("onSaveError: %s", e));
            }
        });
    }

    private Single<Preferences> save(UserStatus status) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(USER_ID, status.getUserId());
            mutablePreferences.set(LEVEL, status.getLevel());
            mutablePreferences.set(EXPERIENCE, status.getExperience());
            mutablePreferences.set(MAX_EXPERIENCE, status.getMaxExperience());
            mutablePreferences.set(COINS, status.getCoins());
            return Single.just(mutablePreferences);
        });
    }

    @Override
    public void updateUserStatus() {
        saveToDB();
    }
}
