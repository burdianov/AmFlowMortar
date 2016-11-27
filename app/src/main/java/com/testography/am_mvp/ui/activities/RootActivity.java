package com.testography.am_mvp.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testography.am_mvp.BuildConfig;
import com.testography.am_mvp.R;
import com.testography.am_mvp.di.DaggerService;
import com.testography.am_mvp.di.components.AppComponent;
import com.testography.am_mvp.di.modules.PicassoCacheModule;
import com.testography.am_mvp.di.modules.RootModule;
import com.testography.am_mvp.di.scopes.RootScope;
import com.testography.am_mvp.flow.TreeKeyDispatcher;
import com.testography.am_mvp.mvp.presenters.RootPresenter;
import com.testography.am_mvp.mvp.views.IRootView;
import com.testography.am_mvp.mvp.views.IView;
import com.testography.am_mvp.ui.screens.catalog.CatalogScreen;
import com.testography.am_mvp.utils.RoundedAvatarDrawable;

import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class RootActivity extends AppCompatActivity implements IRootView,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorContainer;
    @BindView(R.id.root_frame)
    FrameLayout mRootFrame;

    protected ProgressDialog mProgressDialog;

    @Inject
    RootPresenter mRootPresenter;

    private AlertDialog.Builder exitDialog;
    private ArrayList<Integer> mNavSet = new ArrayList<>();

    private int mActiveNavItem = 1;

//    private ActionBarDrawerToggle mToggle;


    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new CatalogScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();

        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild
                (getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name) ? rootActivityScope.getService
                (name) : super.getSystemService(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ButterKnife.bind(this);

        RootComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);

        initToolbar();
        initDrawer();
        initExitDialog();
        mRootPresenter.takeView(this);
        mRootPresenter.initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.badge_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        TextView tv = (TextView) notifCount.findViewById(R.id.items_in_cart_txt);
        tv.setText("8");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView();
        super.onDestroy();
    }

    private void initExitDialog() {
        exitDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.close_app)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
    }

    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed()
                && !Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//            mDrawer.closeDrawer(GravityCompat.START);
//        } else {
//            if (mFragmentManager.getBackStackEntryCount() == 0) {
//                exitDialog.show();
//            } else {
//                super.onBackPressed();
//                int activeItem = 0;
//                mNavSet.remove(mNavSet.size() - 1);
//                if (mNavSet.size() > 0) {
//                    activeItem = mNavSet.get(mNavSet.size() - 1);
//                } else if (mNavSet.size() == 0) {
//                    activeItem = 1;
//                }
//                mNavigationView.getMenu().getItem(activeItem).setChecked(true);
//            }
//        }
//    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer);

        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        setupRoundedAvatar();
    }

    private void initToolbar() {

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_account:
                mActiveNavItem = 0;
                break;
            case R.id.nav_catalog:
                mActiveNavItem = 1;
                break;
            case R.id.nav_favorites:
                mActiveNavItem = 2;
                break;
            case R.id.nav_orders:
                mActiveNavItem = 3;
                break;
            case R.id.nav_notifications:
                mActiveNavItem = 4;
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        mNavSet.add(mActiveNavItem);

        return true;
    }

    private void setupRoundedAvatar() {
        ImageView avatar = (ImageView) mNavigationView.getHeaderView(0)
                .findViewById(R.id.user_avatar_iv);
        InputStream resource = getResources().openRawResource(R.raw.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeStream(resource);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            avatar.setBackgroundDrawable(new RoundedAvatarDrawable(bitmap));
        } else {
            avatar.setBackground(new RoundedAvatarDrawable(bitmap));
        }
    }

    //region ==================== IRootView ===================

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorContainer, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error_message));
            // TODO: 22-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable
                    (Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return null;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
    //endregion

    //region ==================== DI ===================

    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule
            .class, PicassoCacheModule.class})
    @RootScope
    public interface RootComponent {
        void inject(RootActivity activity);
        void inject(SplashActivity activity);

        RootPresenter getRootPresenter();
        Picasso getPicasso();
    }
    //endregion
}
