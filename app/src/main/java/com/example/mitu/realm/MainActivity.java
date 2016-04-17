package com.example.mitu.realm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mitu.realm.Models.Country;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm myRealm = Realm.getInstance(getApplicationContext());

        Realm myOtherRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(getApplicationContext())
                                .name("myOtherRealm.realm")
                                .build()
                );


        myRealm.beginTransaction();

        // Create an object
        Country country1 = myRealm.createObject(Country.class);

        // Set its fields
        country1.setName("Norway");
        country1.setPopulation(5165800);
        country1.setCode("YES11");

        myRealm.commitTransaction();



        // Create the object
        Country country2 = new Country();
        country2.setName("Russia");
        country2.setPopulation(146430430);
        country2.setCode("EU11");

        myRealm.beginTransaction();
        Country copyOfCountry2 = myRealm.copyToRealm(country2);
        myRealm.commitTransaction();


        ///Query
        RealmResults<Country> results1 =
                myRealm.where(Country.class).findAll();

        for(Country c:results1) {
            Log.d("results1", c.getName());
        }


        //Condition Query
        RealmResults<Country> results2 =
                myRealm.where(Country.class)
                        .greaterThan("population", 100000000)
                        .findAll();


        for(Country c:results2) {
            Log.d("results2", c.getName());
        }

        // Sort by name, in descending order
        RealmResults<Country> results3 =
                myRealm.where(Country.class)
                        .findAllSorted("name", false);

        // Gets Russia, Norway
    }
}
