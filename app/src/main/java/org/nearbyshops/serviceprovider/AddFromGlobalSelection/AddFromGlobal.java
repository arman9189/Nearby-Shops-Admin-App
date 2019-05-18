package org.nearbyshops.serviceprovider.AddFromGlobalSelection;

import android.os.Bundle;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifySort;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.ToggleFab;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.Interfaces.NotifyBackPressed;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.Interfaces.NotifyHeaderChanged;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.SlidingLayerSortItems;
import org.nearbyshops.serviceprovider.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddFromGlobal extends AppCompatActivity implements NotifyHeaderChanged,NotifySort,ToggleFab{

    public static final String TAG_FRAGMENT = "item_categories_simple";
    public static final String TAG_SLIDING = "sort_items_sliding";
    public static final String INTENT_KEY_ITEM_CAT_PARENT = "parent_cat";


    // Fab Variables
    @BindView(R.id.fab_menu) FloatingActionMenu fab_menu;
//    @BindView(R.id.fab_detach) FloatingActionButton fab_detach;
//    @BindView(R.id.fab_change_parent) FloatingActionButton fab_change_parent;
//    @BindView(R.id.fab_add_item) FloatingActionButton fab_add_item;
//    @BindView(R.id.fab_add) FloatingActionButton fab_add;
//    @BindView(R.id.fab_add_from_global_item) FloatingActionButton fab_add_from_global_item;
    @BindView(R.id.fab_add_from_global) FloatingActionButton fab_add_from_global;


    @BindView(R.id.text_sub) TextView itemHeader;
    @BindView(R.id.slidingLayer) SlidingLayer slidingLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_global_two);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
     */   getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT)==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,new FragmentAddFromGlobal(),TAG_FRAGMENT)
                    .commit();
        }


        setFabBackground();
        setupSlidingLayer();
    }



    private void setFabBackground() {
        // assign background to the FAB's
//        fab_add.setImageResource(R.drawable.fab_add);
//        Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_low_priority_black_24px, getTheme());
//        fab_change_parent.setImageDrawable(drawable);

//        Drawable drawable_add = VectorDrawableCompat.create(getResources(), R.drawable.ic_playlist_add_from_global, getTheme());
//        fab_add_from_global.setImageDrawable(drawable_add);

//        Drawable drawable_detach = VectorDrawableCompat.create(getResources(), R.drawable.ic_detach, getTheme());
//        fab_detach.setImageDrawable(drawable_detach);

//        fab_add_item.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add_black_24dp));
        fab_add_from_global.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_playlist_add_from_global));

    }



    void setupSlidingLayer()
    {

        ////slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        //slidingLayer.setShadowSizeRes(R.dimen.shadow_size);

        if(slidingLayer!=null)
        {
            slidingLayer.setChangeStateOnTap(true);
            slidingLayer.setSlidingEnabled(true);
//            slidingLayer.setPreviewOffsetDistance(15);
            slidingLayer.setOffsetDistance(30);
            slidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);

            //slidingContents.setLayoutParams(layoutParams);

            //slidingContents.setMinimumWidth(metrics.widthPixels-50);


            if(getSupportFragmentManager().findFragmentByTag(TAG_SLIDING)==null)
            {
                System.out.println("Item Cat Simple : New Sliding Layer Loaded !");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.slidinglayerfragment,new SlidingLayerSortItems(),TAG_SLIDING)
                        .commit();
            }
        }

    }





    @Override
    public void showFab() {
        fab_menu.animate().translationY(0);
    }

    @Override
    public void hideFab() {
        fab_menu.animate().translationY(fab_menu.getHeight());
    }





//    Fragment fragment = null;


    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT);

        //notifyBackPressed !=null

        if(fragment instanceof NotifyBackPressed)
        {
            if(((NotifyBackPressed) fragment).backPressed())
            {
                super.onBackPressed();
            }
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public void notifyItemHeaderChanged(String header) {
        itemHeader.setText(header);
    }



    @OnClick({R.id.icon_sort,R.id.text_sort})
    void sortClick()
    {
        slidingLayer.openLayer(true);
    }


    @Override
    public void notifySortChanged() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

        if(fragment instanceof NotifySort)
        {
            ((NotifySort)fragment).notifySortChanged();
        }
    }




//    @OnClick(R.id.fab_detach)
//    void fabDetachClick()
//    {
//        Fragment fragment = getSupportFragmentManager()
//                .findFragmentByTag(TAG_FRAGMENT);
//
//        if(fragment instanceof NotifyFABClick)
//        {
//            ((NotifyFABClick) fragment).detachSelectedClick();
//        }
//    }
//
//
//    @OnClick(R.id.fab_change_parent)
//    void changeParentClick()
//    {
//        Fragment fragment = getSupportFragmentManager()
//                .findFragmentByTag(TAG_FRAGMENT);
//
//        if(fragment instanceof NotifyFABClick)
//        {
//            ((NotifyFABClick) fragment).changeParentForSelected();
//        }
//    }
//
//
//    @OnClick(R.id.fab_add_item)
//    void fabAddItemClick()
//    {
//        Fragment fragment = getSupportFragmentManager()
//                .findFragmentByTag(TAG_FRAGMENT);
//
//        if(fragment instanceof NotifyFABClick)
//        {
//            ((NotifyFABClick) fragment).addItem();
//        }
//    }
//
//

    @OnClick(R.id.fab_add_from_global)
    void fabAddItemCatClick()
    {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT);

        if(fragment instanceof NotifyFABClickAddFromGlobal)
        {
            ((NotifyFABClickAddFromGlobal) fragment).copySelected();
        }
    }


}
