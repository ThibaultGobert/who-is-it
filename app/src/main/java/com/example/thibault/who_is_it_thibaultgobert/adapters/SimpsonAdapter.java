package com.example.thibault.who_is_it_thibaultgobert.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.adapters.viewholders.SimpsonViewHolder;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thibault on 1/01/2018.
 */

public class SimpsonAdapter extends RecyclerView.Adapter<SimpsonViewHolder>{

    List<SimpsonCharacter> characters;
    private Context context;

    public SimpsonAdapter(List<SimpsonCharacter> characters, Context context){
        this.characters = characters;
        this.context = context;
    }

    public SimpsonAdapter(Context context, Cursor cursor){
        loadFromCursor(cursor);
        this.context = context;
    }

    public void loadFromCursor(Cursor cursor){
        characters = new ArrayList<>();
        if (cursor != null && !cursor.isClosed()) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                SimpsonCharacter character = new SimpsonCharacter(cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_DESCRIPTION)));
                character.setId(cursor.getInt(cursor.getColumnIndex(CharacterContract.CharacterEntry._ID)));
                characters.add(character);
            }
            cursor.close();
        }
        notifyDataSetChanged();
    }


    @Override
    public SimpsonViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent,false);
        SimpsonViewHolder holder = new SimpsonViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpsonViewHolder holder, int position) {
        holder.setData(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void insert(int position, SimpsonCharacter data){
        characters.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(int position){
        characters.remove(position);
        notifyItemRemoved(position);
    }

    public SimpsonCharacter getCharacter(int position){
        return characters.get(position);
    }



}
