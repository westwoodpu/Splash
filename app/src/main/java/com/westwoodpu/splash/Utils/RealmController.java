package com.westwoodpu.splash.Utils;

import com.westwoodpu.splash.Models.Photo;

import java.util.List;

import javax.annotation.Nullable;

import io.realm.Realm;

/**
 * realm controller which can save delete and check photo existence
 */

public class RealmController {
    private final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo) {
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto(final Photo photo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo resultPhoto = realm.where(Photo.class).equalTo("id", photo.getId()).findFirst();
                if (resultPhoto != null) resultPhoto.deleteFromRealm();
            }
        });
    }

    public boolean isPhotoExist(String photoId) {
        Photo checkedPhoto = realm.where(Photo.class).equalTo("id", photoId).findFirst();
        return checkedPhoto != null;
    }

    public List<Photo> getPhotos() {
        return realm.where(Photo.class).findAll();
    }
}
