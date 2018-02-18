package capstone.udacity.com.readit.BusinessLayer;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import capstone.udacity.com.readit.Listeners.OnAccountFetched;
import capstone.udacity.com.readit.Listeners.OnFetchingCompleted;
import capstone.udacity.com.readit.Listeners.OnFinishListener;
import capstone.udacity.com.readit.Listeners.OnUploadComplete;
import capstone.udacity.com.readit.Models.Account;
import capstone.udacity.com.readit.Models.Book;

/**
 * Created by Sayed El-Abady on 2/2/2018.
 */

public class Firebase {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private StorageReference storageReference;
    String uID;

    public Firebase() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void login(String email, String password, final OnFinishListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                            uID = auth.getCurrentUser().getUid();

                        } else
                            listener.onFailed(task.getException().getMessage());
                    }
                });
    }

    public void createUser(final Account account, final OnFinishListener listener) {
        auth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uID = auth.getCurrentUser().getUid();
                            addAccount(account);
                            listener.onSuccess();
                        } else
                            listener.onFailed(task.getException().getMessage());
                    }
                });
    }

    public void addAccount(Account account) {
        uID = auth.getCurrentUser().getUid();

        account.setuID(uID);
        database.getReference("users").child(uID).setValue(account);
    }

    public void addBook(Book book, final OnFinishListener listener) {
        uID = auth.getCurrentUser().getUid();

        book.setOwnerID(uID);
        book.setId(book.getOwnerID() + book.getBookName());
        database.getReference("books").child(uID).push().setValue(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else
                            listener.onFailed(task.getException().getMessage());
                    }
                });
    }

    public void getBooks(final OnFetchingCompleted listener) {
        database.getReference("books")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Book> books = new ArrayList<>();
                        for (DataSnapshot ymdSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot repSnapshot : ymdSnapshot.getChildren()) {
                                Book book = repSnapshot.getValue(Book.class);
                                books.add(book);
                            }
                        }
                        listener.onDataFetched(books);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onError(databaseError.getMessage());
                    }
                });
    }

    public void uploadImage(Uri imageUri, String bookName, final OnUploadComplete listener) {
        StorageReference riversRef = storageReference.child("images");
        uID = auth.getCurrentUser().getUid();

        riversRef.child(uID).child(bookName).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        listener.onSuccess(downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        listener.onFailed(exception.getMessage());
                    }
                });
    }

    public void getAccount(String uID, final OnAccountFetched listener) {
        database.getReference("users").child(uID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot.getValue(Account.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onFailed(databaseError.getMessage());
                    }
                });

    }
}
