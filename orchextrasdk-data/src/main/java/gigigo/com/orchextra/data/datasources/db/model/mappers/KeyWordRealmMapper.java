/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.mappers.MapperUtils;

import gigigo.com.orchextra.data.datasources.db.model.KeyWordRealm;
import io.realm.RealmList;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class KeyWordRealmMapper implements Mapper<String, KeyWordRealm> {

    @Override
    public KeyWordRealm modelToExternalClass(String s) {
        KeyWordRealm keyWordRealm = new KeyWordRealm(s);
        return keyWordRealm;
    }

    @Override
    public String externalClassToModel(KeyWordRealm keyWordRealm) {
        return keyWordRealm.getKeyword();
    }

    @Deprecated
    public RealmList<KeyWordRealm> stringKeyWordsToRealmList(List<String> keywords) {

        RealmList<KeyWordRealm> keyWordRealms = new RealmList<>();
        if (keywords == null) {
            return keyWordRealms;
        }

        for (String keyword : keywords) {
            keyWordRealms.add(MapperUtils.checkNullDataRequest(this, keyword));
        }
        return keyWordRealms;
    }

    @Deprecated
    public List<String> realmKeyWordsToStringList(RealmList<KeyWordRealm> keyWordRealms) {

        List<String> keyWords = new ArrayList<>();
        if (keyWordRealms == null) {
            return keyWords;
        }

        for (KeyWordRealm keyWordRealm : keyWordRealms) {
            keyWords.add(MapperUtils.checkNullDataResponse(this, keyWordRealm));
        }
        return keyWords;
    }
}
