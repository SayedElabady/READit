package capstone.udacity.com.readit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import capstone.udacity.com.readit.BusinessLayer.Firebase;
import capstone.udacity.com.readit.Database.BooksDBHelper;
import capstone.udacity.com.readit.Listeners.OnAccountFetched;
import capstone.udacity.com.readit.MainActivity;
import capstone.udacity.com.readit.Models.Account;
import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */

public class ViewBookFragment extends Fragment {
    Book viewedBook;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.book_name_view)
    TextView bookName;
    @BindView(R.id.description_view)
    TextView description;
    @BindView(R.id.category_view)
    TextView category;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.address)
    TextView address;
    Unbinder unbinder;
    Firebase firebase;
    @BindView(R.id.phone)
    TextView phone;
    BooksDBHelper dbHelper;
    boolean isFavourited = false;
    @BindView(R.id.add_to_favourite)
    Button addToFavourite;
    @BindView(R.id.owner_layout)
    LinearLayout ownerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewBooks = inflater.inflate(R.layout.view_book_fragment, container, false);
        unbinder = ButterKnife.bind(this, viewBooks);
        dbHelper = new BooksDBHelper(getContext());
        firebase = new Firebase();
        Bundle arguments = getArguments();
        if (arguments != null) {
            viewedBook = arguments.getParcelable("book");
            isFavourited = arguments.getBoolean("isFavourited");
            setViewsData();
        }


        return viewBooks;
    }

    private void setViewsData() {
        bookName.setText(viewedBook.getBookName());
        description.setText(viewedBook.getDescription());
        category.setText(viewedBook.getCategory());
        Picasso.with(getContext()).load(viewedBook.getImageUri()).placeholder(R.drawable.ic_photo_size_select_actual_black_24dp).into(imageView);
        if (isFavourited) {
            ownerLayout.setVisibility(View.GONE);
            addToFavourite.setVisibility(View.GONE);
        } else
            setOwnerData();
    }

    private void setOwnerData() {
        firebase.getAccount(viewedBook.getOwnerID(), new OnAccountFetched() {
            @Override
            public void onSuccess(Account account) {
                email.setText(account.getEmail());
                address.setText(account.getAddress());
                phone.setText(account.getPhone());
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), R.string.error_loading, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.confirm_button)
    public void onOkClicked() {
        if (!isFavourited)
            ((MainActivity) getActivity()).replaceWithBooksFragment();
        else
            ((MainActivity) getActivity()).replaceWithFavouritesFragment();

    }

    @OnClick(R.id.add_to_favourite)
    public void onFavouriteClicked() {
        if (dbHelper.isExistInDB(viewedBook.getOwnerID() + viewedBook.getBookName())) {
            Toast.makeText(getContext(), R.string.already_in_fav, Toast.LENGTH_LONG).show();
        } else {
            dbHelper.addToFavourite(viewedBook);
            Toast.makeText(getContext(), R.string.book_added, Toast.LENGTH_LONG).show();

        }
    }
}
