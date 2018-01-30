package com.example.thibault.who_is_it_thibaultgobert.activities;

import android.content.Intent;
import android.database.Cursor;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.adapters.SimpsonAdapter;
import com.example.thibault.who_is_it_thibaultgobert.fragments.DetailFragment;
import com.example.thibault.who_is_it_thibaultgobert.fragments.EndingFragment;
import com.example.thibault.who_is_it_thibaultgobert.fragments.RecyclerViewFragment;
import com.example.thibault.who_is_it_thibaultgobert.fragments.ToolbarFragment;
import com.example.thibault.who_is_it_thibaultgobert.interfaces.RecyclerViewItemClickListener;
import com.example.thibault.who_is_it_thibaultgobert.models.Game;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentManager manager;
    private RecyclerViewFragment recyclerViewFragment1;
    private ToolbarFragment toolbarFragment1;
    private DetailFragment detailFragment;
    private Bundle bundle;
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bundle = new Bundle();
        if(savedInstanceState!=null){
            game = (Game) savedInstanceState.getSerializable("game");
        }
        if( game == null){
            game = new Game();
        }
        bundle.putSerializable("game", game);
        //check if portrait mode
        if (findViewById(R.id.fragment_container) !=null) {


            manager = getSupportFragmentManager();
            if (manager.findFragmentById(R.id.fragment_container) == null) {

                // Add the fragment to the 'fragment_container' FrameLayout
                recyclerViewFragment1 = new RecyclerViewFragment();
                toolbarFragment1 = new ToolbarFragment();
                toolbarFragment1.setArguments(bundle);
                recyclerViewFragment1.setArguments(bundle);

                manager.beginTransaction().replace(R.id.fragment_container, recyclerViewFragment1).commit();
                manager.beginTransaction().add(R.id.fragment_container, toolbarFragment1).commit();
            }

        }else {
            manager = getSupportFragmentManager();
            if (manager.findFragmentById(R.id.fragment_container_land) == null) {

                // Add the fragment to the 'fragment_container' FrameLayout
                recyclerViewFragment1 = new RecyclerViewFragment();
                toolbarFragment1 = new ToolbarFragment();
                toolbarFragment1.setArguments(bundle);
                recyclerViewFragment1.setArguments(bundle);

                manager.beginTransaction().replace(R.id.fragment_container_land, recyclerViewFragment1).commit();
                manager.beginTransaction().add(R.id.fragment_container_land, toolbarFragment1).commit();
            }
        }
    }





    public void showDetailFragment(SimpsonCharacter character){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("character", character);
        detailFragment.setArguments(bundle);
        if (findViewById(R.id.fragment_container) !=null) {
            manager.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
        }else{
            manager.beginTransaction().replace(R.id.fragment_container_land, detailFragment).addToBackStack(null).commit();
            manager.beginTransaction().add(R.id.fragment_container_land, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container_land, toolbarFragment1).commit();
        }
    }
    public void deleteDetail(){
        if(findViewById(R.id.fragment_container_land) !=null){
            toolbarFragment1 = new ToolbarFragment();
            toolbarFragment1.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragment_container_land, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container_land, toolbarFragment1).commit();
        }
    }

    public void showRecyclerView(){
        if (findViewById(R.id.fragment_container) !=null) {
            manager.beginTransaction().replace(R.id.fragment_container, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container, toolbarFragment1).commit();
        }else{
            manager.beginTransaction().replace(R.id.fragment_container_land, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container_land, toolbarFragment1).commit();
        }
    }

    public void showPlayerToolbar(){
        recyclerViewFragment1 = new RecyclerViewFragment();
        toolbarFragment1 = new ToolbarFragment();

        recyclerViewFragment1.setArguments(bundle);
        toolbarFragment1.setArguments(bundle);
        if (findViewById(R.id.fragment_container) !=null) {
            manager.beginTransaction().replace(R.id.fragment_container, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container, toolbarFragment1).commit();
        }else{
            manager.beginTransaction().replace(R.id.fragment_container_land, recyclerViewFragment1).commit();
            manager.beginTransaction().add(R.id.fragment_container_land, toolbarFragment1).commit();
        }
    }

    public void showEnding(){
        EndingFragment endingFragment = new EndingFragment();
        endingFragment.setArguments(bundle);
        if (findViewById(R.id.fragment_container) !=null) {
            manager.beginTransaction().replace(R.id.fragment_container, endingFragment).commit();
        }else{
            manager.beginTransaction().replace(R.id.fragment_container_land, endingFragment).commit();

        }
    }

    public void showMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);
        game = (Game) savedState.getSerializable("game");

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                CharacterContract.CharacterEntry._ID,
                CharacterContract.CharacterEntry.COLUMN_NAME,
                CharacterContract.CharacterEntry.COLUMN_DESCRIPTION,
                CharacterContract.CharacterEntry.COLUMN_IMAGE,
        };
        String sortOrder = CharacterContract.CharacterEntry.COLUMN_NAME + " ASC";
        return new CursorLoader(getApplicationContext(), CharacterContract.CharacterEntry.CONTENT_URI, projection, null, null, sortOrder);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(!game.isOnGoing()){
            getLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<SimpsonCharacter> characters = new ArrayList<>();
        if (cursor != null && !cursor.isClosed()) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                SimpsonCharacter character = new SimpsonCharacter(cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_DESCRIPTION)));
                character.setId(cursor.getInt(cursor.getColumnIndex(CharacterContract.CharacterEntry._ID)));
                characters.add(character);
            }
        }
        game.setCharacters(characters);
        bundle.putSerializable("game", game);
        //check landscape
        if(recyclerViewFragment1 == null){
            if (findViewById(R.id.fragment_container) !=null) {
                recyclerViewFragment1 = (RecyclerViewFragment) getSupportFragmentManager().getFragments().get(0);
                toolbarFragment1 = (ToolbarFragment) getSupportFragmentManager().getFragments().get(1);
            }else{
                recyclerViewFragment1 = (RecyclerViewFragment) getSupportFragmentManager().getFragments().get(2);
                toolbarFragment1 = (ToolbarFragment) getSupportFragmentManager().getFragments().get(3);
            }
        }
        recyclerViewFragment1.setArguments(bundle);
        toolbarFragment1.setArguments(bundle);
        recyclerViewFragment1.setGame();
        toolbarFragment1.setGame();
        /*
        if (findViewById(R.id.fragment_container) !=null) {
            recyclerViewFragment1.setArguments(bundle);
            toolbarFragment1.setArguments(bundle);
            recyclerViewFragment1.setGame();
            toolbarFragment1.setGame();
        }else{
            recyclerViewFragment1 = (RecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.recyclerview_fragment);
            detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
            detailFragment.setArguments(bundle);
            recyclerViewFragment1.setArguments(bundle);
        }
        */
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
