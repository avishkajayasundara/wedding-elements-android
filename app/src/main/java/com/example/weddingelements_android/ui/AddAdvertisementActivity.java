package com.example.weddingelements_android.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.model.BusinessOwner;
import com.example.weddingelements_android.model.Cache;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAdvertisementActivity extends AppCompatActivity {

    EditText title,description,price;
    private Uri imageUri;
    ImageView imageView;
    Retrofit retrofit;
    Spinner category;
    private StorageReference imageReference;
    FirebaseStorage storage;
    private static final int galleryPick=1;
    AppCompatButton button;
    String url;
    String categoryString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisement);
        FirebaseApp.initializeApp(getApplicationContext());
        storage = FirebaseStorage.getInstance();
        category = (Spinner) findViewById(R.id.advcategory_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        title = findViewById(R.id.newadv_title);
        description = findViewById(R.id.newadv_description);
        price = findViewById(R.id.newadv_price);
        imageView = findViewById(R.id.newadv_image);
        button = findViewById(R.id.adv_add);
        imageReference=storage.getReferenceFromUrl("gs://wedding-elements.appspot.com");
        imageReference= FirebaseStorage.getInstance().getReference().child("Advertisement Images");
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = category.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });

    }

    private void uploadImage() {
        if(imageUri==null){
            Toast.makeText(getApplicationContext(),"Advertisement Image is Required",Toast.LENGTH_SHORT).show();
        }
        else{
            final StorageReference filePath=imageReference.child(imageUri.getLastPathSegment()+".jpg");
            final UploadTask uploadTask=filePath.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Image uploaded Failed",Toast.LENGTH_SHORT).show();

                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Image uploaded successfully",Toast.LENGTH_SHORT).show();
                    Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(task.isSuccessful()){
                                url=filePath.getDownloadUrl().toString();
                            }
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                url=task.getResult().toString();
                                Toast.makeText(getApplicationContext(),"Image URL was retrieved",Toast.LENGTH_SHORT).show();
                                postAdvertisement();
                            }
                        }
                    });

                }
            });

        }
    }

    private void postAdvertisement() {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(title.getText().toString());
        advertisement.setDescription(description.getText().toString());
        advertisement.setStartingPrice(Double.parseDouble(price.getText().toString()));
        advertisement.setBusinessOwner(Cache.user.getUsername());
        advertisement.setCategory(categoryString);
        advertisement.setImage(url);

        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi api = retrofit.create(RestApi.class);
        Call<Advertisement> call = api.addAdvertisement(advertisement);
        call.enqueue(new Callback<Advertisement>() {
            @Override
            public void onResponse(Call<Advertisement> call, Response<Advertisement> response) {
                System.out.println("Succeess");
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<Advertisement> call, Throwable t) {
                System.out.println("Failed");
                System.out.println(t);
            }
        });
    }

    private void openGalery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPick&&resultCode==RESULT_OK&&data!=null){
            imageView.setImageURI(data.getData());
            imageUri=data.getData();
        }
    }
}