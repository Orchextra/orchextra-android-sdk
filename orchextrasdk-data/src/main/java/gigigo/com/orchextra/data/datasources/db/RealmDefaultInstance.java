package gigigo.com.orchextra.data.datasources.db;

import android.content.Context;

import io.realm.Realm;

public abstract class RealmDefaultInstance {

    public Realm getRealmInstance(Context context) {
        return Realm.getInstance(context);
    }

    public static int getNextKey(Realm realm, Class classType) {
        try {
            return realm.where(classType).max("id").intValue() + 1;
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return 1;
        }
    }
}
