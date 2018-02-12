package capstone.udacity.com.readit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            setViewsData();
        }


        return viewBooks;
    }

    private void setViewsData() {
        bookName.setText(viewedBook.getBookName());
        description.setText(viewedBook.getDescription());
        category.setText(viewedBook.getCategory());
        Picasso.with(getContext()).load(viewedBook.getImageUri()).placeholder(R.drawable.ic_photo_size_select_actual_black_24dp).into(imageView);
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
                Toast.makeText(getContext(), "Error loading owner data, Try again Later", Toast.LENGTH_SHORT).show();
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
        ((MainActivity) getActivity()).replaceWithBooksFragment();
    }

    @OnClick(R.id.add_to_favourite)
    public void onFavouriteClicked() {
        if (dbHelper.isExistInDB(viewedBook.getOwnerID() + viewedBook.getBookName())){
            Toast.makeText(getContext() , "It's Already in favourites" , Toast.LENGTH_LONG).show();
        }else {
            dbHelper.addToFavourite(viewedBook);
            Toast.makeText(getContext() , "Book is added to favourite" , Toast.LENGTH_LONG).show();

        }
    }
}
