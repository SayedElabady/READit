package capstone.udacity.com.readit;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.udacity.com.readit.Fragments.BooksFragment;
import capstone.udacity.com.readit.Fragments.FavouriteFragment;
import capstone.udacity.com.readit.Helper.Tags;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    private final int FRAGMENT_CONTAINER = R.id.fragment_container;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        replaceWithBooksFragment();
        bottomNavigator.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_books:
                                replaceWithBooksFragment();
                                break;
                            case R.id.action_favourite:
                                replaceWithFavouritesFragment();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void replaceWithBooksFragment() {
        BooksFragment booksFragment = new BooksFragment();
        fragmentManager.beginTransaction()
                .replace(FRAGMENT_CONTAINER, booksFragment , Tags.VIEW_BOOKS_TAG)
                .commit();
    }

    public void replaceWithFavouritesFragment() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        fragmentManager.beginTransaction()
                .replace(FRAGMENT_CONTAINER, favouriteFragment , Tags.VIEW_FAVOURITE_TAG)
                .commit();
    }

    public void addFragment(Fragment toAdd, String tag) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(FRAGMENT_CONTAINER, toAdd, tag)
                    .commit();
        }
    }

    public void removeFragmentIfExists(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction mTransaction = fragmentManager.beginTransaction();
            mTransaction.remove(fragment);
            mTransaction.commit();
        }
    }
    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(FRAGMENT_CONTAINER);
        if (fragment != null)
            if (fragment.getTag().equals(Tags.VIEW_BOOKS_TAG) || fragment.getTag().equals(Tags.VIEW_FAVOURITE_TAG))
                super.onBackPressed();
            else
                removeFragmentIfExists(fragment);
    }

}
