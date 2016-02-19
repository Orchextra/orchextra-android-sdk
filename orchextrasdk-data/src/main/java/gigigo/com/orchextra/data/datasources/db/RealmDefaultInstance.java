package gigigo.com.orchextra.data.datasources.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmDefaultInstance {

    public synchronized Realm createRealmInstance(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config);
    }

    public static int getNextKey(Realm realm, Class classType) {
        try {
            return realm.where(classType).max("id").intValue() + 1;
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return 1;
        }
    }


}
