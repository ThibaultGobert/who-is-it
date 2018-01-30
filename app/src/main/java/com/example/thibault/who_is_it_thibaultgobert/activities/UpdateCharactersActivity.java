package com.example.thibault.who_is_it_thibaultgobert.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.adapters.SimpsonAdapter;
import com.example.thibault.who_is_it_thibaultgobert.interfaces.RecyclerViewItemClickListener;
import com.example.thibault.who_is_it_thibaultgobert.models.Game;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;
import com.example.thibault.who_is_it_thibaultgobert.util.CustomRVItemTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 29/01/2018.
 */

public class UpdateCharactersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    @BindView(R.id.recyclerviewUpdate)
    RecyclerView recyclerView;

    private SimpsonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_characters);
        ButterKnife.bind(this);

        initData();
        initializeClickListener();
        initializeSwipeListener();
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);
        getLoaderManager().restartLoader(0,null,this);
    }



    private void initData(){
        adapter = new SimpsonAdapter(new ArrayList<SimpsonCharacter>(), getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        RecyclerView.ItemAnimator itemAnimator =  new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void initializeClickListener(){
        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(getApplicationContext(), recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                SimpsonCharacter character = adapter.getCharacter(position);
                showUpdateCharacter(character, position);
            }
            @Override
            public void onLongClick(View view, int position) {
            }})
        );
    }

    private void showUpdateCharacter(SimpsonCharacter character, int position){
        Intent intent = new Intent(this, UpdateCharacterActivity.class);
        intent.putExtra("character", character);

        startActivityForResult(intent, position);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            //resultCode is position
            getLoaderManager().restartLoader(0,null,this);
        }

    }

    private void initializeSwipeListener(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public boolean isItemViewSwipeEnabled(){
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    deleteCharacter(adapter.getCharacter(position));
                    adapter.remove(position);
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void deleteCharacter(SimpsonCharacter character){
        String selection = CharacterContract.CharacterEntry.COLUMN_IMAGE + " = ?";
        String[] selectionArgs = {String.valueOf(character.getImagePath())};
        int rowsDeleted = getContentResolver().delete(CharacterContract.CharacterEntry.CONTENT_URI, selection, selectionArgs);
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.loadFromCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



}
