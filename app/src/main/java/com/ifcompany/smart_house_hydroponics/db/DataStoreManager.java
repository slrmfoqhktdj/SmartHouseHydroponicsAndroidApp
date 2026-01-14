package com.ifcompany.smart_house_hydroponics.db;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ifcompany.smart_house_hydroponics.db.encryption.EncryptionManager;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceAppointListModel;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;


public class DataStoreManager {
    private static final String TAG = "DataStoreManager";
    private static final String DATASTORE_NAME = "smart_house_prefs";

    private static DataStoreManager instance;
    private RxDataStore<Preferences> dataStore;
    private Gson gson;

    private EncryptionManager encryptionManager;

    // Preference Keys
    private static final Preferences.Key<String> KEY_FCM_TOKEN =
            PreferencesKeys.stringKey("fcm_token");
    private final String KEY_DEVICE_LIST = "device_list";

    private DataStoreManager(Context context) {
        this.dataStore = new RxPreferenceDataStoreBuilder(
                context.getApplicationContext(),
                DATASTORE_NAME
        ).build();
        this.gson = new GsonBuilder().create();
        this.encryptionManager = EncryptionManager.getInstance();
    }

    public static synchronized DataStoreManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataStoreManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 값 암호화 (필요한 경우에만)
     */
    private String encryptValue(String value) {
        if (value == null) {
            return value;
        }
        return encryptionManager.encrypt(value);
    }

    /**
     * 값 복호화 (필요한 경우에만)
     */
    private String decryptValue(String value) {
        if (value == null) {
            return value;
        }
        return encryptionManager.decrypt(value);
    }

    /**
     * 문자열 저장 (비동기)
     */
    public interface SaveCallback {
        void onSuccess();
        void onError(Throwable t);
    }

    public void setString(String key, String value, SaveCallback callback) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);

        Disposable d = dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).subscribe(
                preferences -> {
                    Log.d(TAG, "String saved: " + key);
                    if (callback != null) {
                        callback.onSuccess();
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error saving string: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    /**
     * 문자열 저장 (동기 - UI 스레드에서 사용 금지)
     */
    public void setStringSync(String key, String value) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);

        dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).blockingGet();
    }

    // 암호화
    public void setEncryptStringSync(String key, String value) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);
        String encryptedValue = encryptValue(value);

        dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, encryptedValue);
            return Single.just(mutablePrefs);
        }).blockingGet();
    }


    /**
     * 문자열 조회 (비동기)
     */
    public interface GetStringCallback {
        void onSuccess(String value);
        void onError(Throwable t);
    }

    public void getString(String key, String defaultValue, GetStringCallback callback) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);

        Disposable d = dataStore.data().subscribe(
                preferences -> {
                    String value = preferences.get(prefKey);
                    if (callback != null) {
                        callback.onSuccess(value != null ? value : defaultValue);
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error getting string: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    /**
     * 문자열 조회 (동기 - UI 스레드에서 사용 금지)
     */
    public String getStringSync(String key, String defaultValue) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);

        try {
            Preferences prefs = dataStore.data().blockingFirst();
            String value = prefs.get(prefKey);
            return value != null ? value : defaultValue;
        } catch (Exception e) {
            Log.e(TAG, "Error getting string sync: " + key, e);
            return defaultValue;
        }
    }

    //복호화 
    public String getDecryptStringSync(String key, String defaultValue) {
        Preferences.Key<String> prefKey = PreferencesKeys.stringKey(key);

        try {
            Preferences prefs = dataStore.data().blockingFirst();
            String encryptValue = prefs.get(prefKey);
            String value = decryptValue(encryptValue);
            return value != null ? value : defaultValue;
        } catch (Exception e) {
            Log.e(TAG, "Error getting string sync: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 정수 저장
     */
    public void setInt(String key, int value, SaveCallback callback) {
        Preferences.Key<Integer> prefKey = PreferencesKeys.intKey(key);

        Disposable d = dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).subscribe(
                preferences -> {
                    Log.d(TAG, "Int saved: " + key);
                    if (callback != null) {
                        callback.onSuccess();
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error saving int: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    /**
     * 정수 조회
     */
    public interface GetIntCallback {
        void onSuccess(int value);
        void onError(Throwable t);
    }

    public void getInt(String key, int defaultValue, GetIntCallback callback) {
        Preferences.Key<Integer> prefKey = PreferencesKeys.intKey(key);

        Disposable d = dataStore.data().subscribe(
                preferences -> {
                    Integer value = preferences.get(prefKey);
                    if (callback != null) {
                        callback.onSuccess(value != null ? value : defaultValue);
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error getting int: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    /**
     * Long 저장
     */
    public void setLong(String key, long value, SaveCallback callback) {
        Preferences.Key<Long> prefKey = PreferencesKeys.longKey(key);

        Disposable d = dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).subscribe(
                preferences -> {
                    Log.d(TAG, "Long saved: " + key);
                    if (callback != null) {
                        callback.onSuccess();
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error saving long: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    public void setLongSync(String key, long value) {
        Preferences.Key<Long> prefKey = PreferencesKeys.longKey(key);

        dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).blockingGet();
    }

    /**
     * Long 조회
     */
    public interface GetLongCallback {
        void onSuccess(long value);
        void onError(Throwable t);
    }

    public void getLong(String key, long defaultValue, GetLongCallback callback) {
        Preferences.Key<Long> prefKey = PreferencesKeys.longKey(key);

        Disposable d = dataStore.data().subscribe(
                preferences -> {
                    Long value = preferences.get(prefKey);
                    if (callback != null) {
                        callback.onSuccess(value != null ? value : defaultValue);
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error getting long: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    public Long getLongSync(String key, long defaultValue) {
        Preferences.Key<Long> prefKey = PreferencesKeys.longKey(key);

        try {
            Preferences prefs = dataStore.data().blockingFirst();
            Long value = prefs.get(prefKey);
            return value != null ? value : defaultValue;
        } catch (Exception e) {
            Log.e(TAG, "Error getting string sync: " + key, e);
            return defaultValue;
        }
    }

    /**
     * Boolean 저장
     */
    public void setBool(String key, boolean value, SaveCallback callback) {
        Preferences.Key<Boolean> prefKey = PreferencesKeys.booleanKey(key);

        Disposable d = dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).subscribe(
                preferences -> {
                    Log.d(TAG, "Boolean saved: " + key);
                    if (callback != null) {
                        callback.onSuccess();
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error saving boolean: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    public void setBoolSync(String key, boolean value) {
        Preferences.Key<Boolean> prefKey = PreferencesKeys.booleanKey(key);

        dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.set(prefKey, value);
            return Single.just(mutablePrefs);
        }).blockingGet();
    }

    /**
     * Boolean 조회
     */
    public interface GetBoolCallback {
        void onSuccess(boolean value);
        void onError(Throwable t);
    }

    public void getBool(String key, boolean defaultValue, GetBoolCallback callback) {
        Preferences.Key<Boolean> prefKey = PreferencesKeys.booleanKey(key);

        Disposable d = dataStore.data().subscribe(
                preferences -> {
                    Boolean value = preferences.get(prefKey);
                    if (callback != null) {
                        callback.onSuccess(value != null ? value : defaultValue);
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error getting boolean: " + key, throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }

    public Boolean getBoolSync(String key, boolean defaultValue) {
        Preferences.Key<Boolean> prefKey = PreferencesKeys.booleanKey(key);

        try {
            Preferences prefs = dataStore.data().blockingFirst();
            Boolean value = prefs.get(prefKey);
            return value != null ? value : defaultValue;
        } catch (Exception e) {
            Log.e(TAG, "Error getting string sync: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 기기 리스트 저장
     */
    public void setDeviceList(ArrayList<String> value, SaveCallback callback) {
        StringBuilder saveValue = new StringBuilder();
        String between = "//";

        for (int i = 0; i < value.size(); i++) {
            if (i == 0) {
                saveValue.append(value.get(i));
            } else {
                saveValue.append(between).append(value.get(i));
            }
        }

        setString(KEY_DEVICE_LIST, saveValue.toString(), callback);
    }

    public void setDeviceListSync(ArrayList<String> value) {
        StringBuilder saveValue = new StringBuilder();
        String between = "//";

        for (int i = 0; i < value.size(); i++) {
            if (i == 0) {
                saveValue.append(value.get(i));
            } else {
                saveValue.append(between).append(value.get(i));
            }
        }

        setStringSync(KEY_DEVICE_LIST, saveValue.toString());
    }

    /**
     * 기기 리스트 조회
     */
    public interface GetDeviceListCallback {
        void onSuccess(ArrayList<String> devices);
        void onError(Throwable t);
    }

    public void getDeviceList(GetDeviceListCallback callback) {
        getString(KEY_DEVICE_LIST, "", new GetStringCallback() {
            @Override
            public void onSuccess(String value) {
                if (value == null || value.isEmpty()) {
                    if (callback != null) {
                        callback.onSuccess(new ArrayList<>());
                    }
                    return;
                }

                String between = "//";
                ArrayList<String> devices = new ArrayList<>(Arrays.asList(value.split(between)));
                if (callback != null) {
                    callback.onSuccess(devices);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (callback != null) {
                    callback.onError(t);
                }
            }
        });
    }

    /**
     * 기기 ID 조회 (닉네임으로)
     */
    public void getDeviceId(String nick, GetStringCallback callback) {
        getDeviceList(new GetDeviceListCallback() {
            @Override
            public void onSuccess(ArrayList<String> devices) {
                String returnId = null;
                for (String device : devices) {
                    if (device.equals(nick)) {
                        returnId = device;
                        break;
                    }
                }

                if (callback != null) {
                    callback.onSuccess(returnId);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (callback != null) {
                    callback.onError(t);
                }
            }
        });
    }

    /**
     * 기기 예약 정보 저장
     */
    public void setDeviceAppoint(Retrofit_DeviceAppointListModel model, SaveCallback callback) {
        String key = model.getId() + "_apt";
        String value = gson.toJson(model);
        setString(key, value, callback);
    }

    /**
     * 기기 예약 정보 조회
     */
    public interface GetDeviceAppointCallback {
        void onSuccess(Retrofit_DeviceAppointListModel model);
        void onError(Throwable t);
    }

    public void getDeviceAppoint(String id, GetDeviceAppointCallback callback) {
        String key = id + "_apt";

        getString(key, null, new GetStringCallback() {
            @Override
            public void onSuccess(String value) {
                if (value == null) {
                    if (callback != null) {
                        callback.onSuccess(null);
                    }
                    return;
                }

                Retrofit_DeviceAppointListModel model = gson.fromJson(
                        value,
                        Retrofit_DeviceAppointListModel.class
                );

                if (callback != null) {
                    callback.onSuccess(model);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (callback != null) {
                    callback.onError(t);
                }
            }
        });
    }

    /**
     * 모든 데이터 삭제
     */
    public void clearAll(SaveCallback callback) {
        Disposable d = dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePrefs = prefs.toMutablePreferences();
            mutablePrefs.clear();
            return Single.just(mutablePrefs);
        }).subscribe(
                preferences -> {
                    Log.d(TAG, "All data cleared");
                    if (callback != null) {
                        callback.onSuccess();
                    }
                },
                throwable -> {
                    Log.e(TAG, "Error clearing data", throwable);
                    if (callback != null) {
                        callback.onError(throwable);
                    }
                }
        );

        d.dispose();
    }
}
