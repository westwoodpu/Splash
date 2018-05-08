package com.westwoodpu.backGroundMachine.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.westwoodpu.backGroundMachine.Adapters.GlideApp;
import com.westwoodpu.backGroundMachine.Models.Photo;
import com.westwoodpu.backGroundMachine.R;
import com.westwoodpu.backGroundMachine.Utils.Functions;
import com.westwoodpu.backGroundMachine.Utils.RealmController;
import com.westwoodpu.backGroundMachine.Webservices.ApiInterface;
import com.westwoodpu.backGroundMachine.Webservices.ServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nxa16819 on 4/18/2018.
 */

public class FullScreenPhotoActivity extends AppCompatActivity {
    private static final String TAG = FullScreenPhotoActivity.class.getSimpleName();
    @BindView(R.id.activity_fullscreen_photo_username)
    TextView username;
    @BindView(R.id.activity_fullscreen_photo_user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.activity_fullscreen_photo_photo)
    ImageView fullscreenPhoto;
    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    FloatingActionButton fabWallpaper;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton fabFavorite;

    private Unbinder unbinder;
    private Bitmap photoBitmap;
    private RealmController realmController;
    private Photo photo = new Photo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //generate information of photos
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        realmController = new RealmController();
        if (realmController.isPhotoExist(photoId)) {
            fabFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.ic_check_favorited));
        }
    }

    private void getPhoto(String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "success photoURL");
                    photo = response.body();
                    updateUI(photo);
                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void updateUI(Photo photo) {
        try {
            username.setText(photo.getUser().getUsername());
            GlideApp.with(FullScreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall())
                    .into(userAvatar);

            GlideApp.with(FullScreenPhotoActivity.this)
                    .asBitmap()
                    .load(photo.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            fullscreenPhoto.setImageBitmap(resource);
                            photoBitmap = resource;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    public void setFabWallpaper() {
        if (photoBitmap != null) {
            if (Functions.setWallpaper(FullScreenPhotoActivity.this, photoBitmap)) {
                Toast.makeText(this, "Wallpaper is set", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Wallpaper not set", Toast.LENGTH_LONG).show();
            }
        }
        fabMenu.close(true);
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_favorite)
    public void setFabFavorite() {
        if (realmController.isPhotoExist(photo.getId())) {
            realmController.deletePhoto(photo);
            fabFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.ic_check_favorite));
            Toast.makeText(this, "Unfavorite", Toast.LENGTH_LONG).show();
        } else {
            realmController.savePhoto(photo);
            fabFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.ic_check_favorited));
        }
        fabMenu.close(true);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}

