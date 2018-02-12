package capstone.udacity.com.readit.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import capstone.udacity.com.readit.BusinessLayer.Firebase;
import capstone.udacity.com.readit.Listeners.OnFinishListener;
import capstone.udacity.com.readit.Listeners.OnUploadComplete;
import capstone.udacity.com.readit.MainActivity;
import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class AddBookFragment extends Fragment {

    @BindView(R.id.book_name)
    EditText bookName;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.category)
    EditText category;
    Unbinder unbinder;
    Firebase firebase;
    Uri imageUri;
    public final static int SELECT_PICTURE = 21;
    @BindView(R.id.book_image)
    ImageView bookImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View addBookView = inflater.inflate(R.layout.add_book_fragment, container, false);
        firebase = new Firebase();
        unbinder = ButterKnife.bind(this, addBookView);
        return addBookView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_book)
    public void addBook() {
        if (isDataFilled()) {
            final Book bookToAdd = new Book();
            bookToAdd.setBookName(bookName.getText().toString());
            bookToAdd.setDescription(description.getText().toString());
            bookToAdd.setCategory(category.getText().toString());

            if (imageUri != null) {
                firebase.uploadImage(imageUri, bookToAdd.getBookName(), new OnUploadComplete() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        bookToAdd.setImageUri(downloadUrl.toString());
                        uploadBook(bookToAdd);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(getContext(), "Error on Uploading the image," + errorMessage, Toast.LENGTH_SHORT).show();
                        //       uploadBook(bookToAdd);
                    }
                });
            }
        } else
            Toast.makeText(getContext(), "Please fill the missing data", Toast.LENGTH_SHORT).show();
    }

    private boolean isDataFilled() {
        boolean filled = true;
        if (bookName.getText().toString().equals("")) {
            filled = false;
            bookName.setError("It's Mandatory field");
        }
        if (description.getText().toString().equals("")) {
            filled = false;
            description.setError("It's Mandatory field");
        }
        if (category.getText().toString().equals("")) {
            filled = false;
            category.setError("It's Mandatory field");
        }
        return filled;
    }

    private void uploadBook(Book bookToAdd) {
        firebase.addBook(bookToAdd, new OnFinishListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "Your Book has been added sucessfully!", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity())
                        .removeFragmentIfExists(AddBookFragment.this);
                ((MainActivity) getActivity())
                        .replaceWithBooksFragment();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getActivity(), "There was an error, " + errorMessage + " Try again later!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @OnClick(R.id.book_image)
    void selectPhoto() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imageUri = selectedImageUri;
                bookImage.setImageURI(selectedImageUri);
            }
        }
    }
}
