package dev.suncha.mannersphoto;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    private myAdapter adapter;

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;
    private View containerView;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstance = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        recyclerView = (RecyclerView)layout.findViewById(R.id.drawerList);
        adapter =new myAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Information> getData(){
        List<Information> data= new ArrayList<>();
        int[] icons = {R.drawable.recent_post,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
        String[] titles = {"Recent Posts","General Photography","Landscape Photography","Portrait Photography","Candid Photography","Mobile Photography"};
        for(int i=0;i<titles.length && i<icons.length;i++){
            Information current = new Information();
            current.iconId=icons[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolBar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (slideOffset < 0.6) {
                    toolBar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstance) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public static void saveToPreference(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);

    }
}
