package com.example.langtranslation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentificationOptions;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView outputText;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.editTextTextInput);
        outputText = findViewById(R.id.outputTextView);
        goButton = findViewById(R.id.goButton);
    }
    public void identifyLanguage(View v) {
        String text = inputText.getText().toString();
        identifyLanguageUsingFirebase(text);
    }

    public void identifyLanguageUsingFirebase(String text){
        FirebaseLanguageIdentification languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode != "und") {
                                    //create Locale object to convert
                                    //BCP language code to language name
                                    Locale loc = new Locale(languageCode);
                                    outputText.setText("The text is in " + loc.getDisplayLanguage());
                                } else {
                                    Toast.makeText(MainActivity.this,"Language could not be identified",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Language could not be identified",Toast.LENGTH_LONG).show();
                            }
                        });
    }
}