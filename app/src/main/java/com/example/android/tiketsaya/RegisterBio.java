package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterBio extends AppCompatActivity {

    Button btn_continue, btn_upload;
    EditText name, bio;
    ImageView img_avatar;

    //Deklarasi variabel untuk lokasi foto
    Uri img_location;
    int img_limit = 1;

    DatabaseReference reference;
    StorageReference storage;

    final String USERNAME_KEY = "username";
    String username_default;
    String username_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bio);

        getUSernameLocal();

        btn_continue = findViewById(R.id.btn_continue);
        btn_upload = findViewById(R.id.btn_upload);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        img_avatar = findViewById(R.id.img_avatar);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findImage();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
            storage = FirebaseStorage.getInstance().getReference().child("Avatar").child(username_local);

            //Validasi foto
            if(img_location != null) {
                StorageReference temp = storage.child(System.currentTimeMillis() + "." + getFileExtension(img_location));
                temp.putFile(img_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String img_uri = taskSnapshot.getUploadSessionUri().toString();
                        reference.getRef().child("url_photo").setValue(img_uri);

                        reference.getRef().child("name").setValue(name.getText().toString());
                        reference.getRef().child("bio").setValue(bio.getText().toString());
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Intent intent = new Intent(RegisterBio.this, RegisterSuccess.class);
                        startActivity(intent);
                    }
                });
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
            Picasso.get().load(img_location).centerCrop().fit().into(img_avatar);
        }
    }

    //Mengambil data dari shared references
    private void getUSernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_local = sharedPreferences.getString(username_default, "");
    }
}
