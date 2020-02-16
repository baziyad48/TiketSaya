package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileEdit extends AppCompatActivity {

    ImageView edit_avatar;
    Button edit_upload, edit_save;
    TextView edit_name, edit_username, edit_password, edit_email, edit_bio;

    int img_limit = 1;
    Uri img_location;

    final String USERNAME_KEY = "username";
    String username_default;
    String username_local;

    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_local = sharedPreferences.getString(username_default, "");

        edit_avatar = findViewById(R.id.edit_avatar);
        edit_upload = findViewById(R.id.edit_upload);
        edit_save = findViewById(R.id.edit_save);
        edit_name = findViewById(R.id.edit_name);
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        edit_email = findViewById(R.id.edit_email);
        edit_bio = findViewById(R.id.edit_bio);

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.child("url_photo").getValue().toString()).centerCrop().fit().into(edit_avatar);

                edit_name.setText(dataSnapshot.child("name").getValue().toString());
                edit_username.setText(dataSnapshot.child("username").getValue().toString());
                edit_bio.setText(dataSnapshot.child("bio").getValue().toString());
                edit_email.setText(dataSnapshot.child("email").getValue().toString());
                edit_password.setText(dataSnapshot.child("password").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        edit_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findImage();
            }
        });

        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_save.setEnabled(false);
                edit_save.setText("Loading");

                if(edit_name.getText().toString().isEmpty() || edit_username.getText().toString().isEmpty() || edit_bio.getText().toString().isEmpty() ||
                    edit_password.getText().toString().isEmpty() || edit_email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Field cannot be blank!", Toast.LENGTH_SHORT).show();
                    edit_save.setEnabled(true);
                    edit_save.setText("Save");
                } else {
                    reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("name").setValue(edit_name.getText().toString());
                            dataSnapshot.getRef().child("bio").setValue(edit_bio.getText().toString());
                            dataSnapshot.getRef().child("username").setValue(edit_username.getText().toString());
                            dataSnapshot.getRef().child("password").setValue(edit_password.getText().toString());
                            dataSnapshot.getRef().child("email").setValue(edit_email.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    storage = FirebaseStorage.getInstance().getReference().child("Avatar").child(username_local);
                    //Validasi foto
                    if(img_location != null) {
                        final StorageReference temp = storage.child(System.currentTimeMillis() + "." + getFileExtension(img_location));
                        temp.putFile(img_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                temp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.v("url_photo", uri.toString());
                                        reference.getRef().child("url_photo").setValue(uri.toString());
                                    }
                                });
                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            }
                        });
                    }

                    Intent intent = new Intent(ProfileEdit.this, Profile.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //Method mengambil foto lewat intent
    private void findImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, img_limit);
    }

    //Menyimpan alamat foto
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Lanjutan method findImage()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Image caching
        if(requestCode == img_limit && resultCode == RESULT_OK && data != null && data.getData() != null) {
            img_location = data.getData();
            //Memasukkan URI ke ImageView
            Picasso.get().load(img_location).centerCrop().fit().into(edit_avatar);
        }
    }
}
