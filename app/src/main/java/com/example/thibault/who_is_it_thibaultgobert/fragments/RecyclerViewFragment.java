package com.example.thibault.who_is_it_thibaultgobert.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.thibault.who_is_it_thibaultgobert.MemoryLeakApplication;
import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.activities.MainActivity;
import com.example.thibault.who_is_it_thibaultgobert.adapters.SimpsonAdapter;
import com.example.thibault.who_is_it_thibaultgobert.interfaces.RecyclerViewItemClickListener;
import com.example.thibault.who_is_it_thibaultgobert.models.Game;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;
import com.example.thibault.who_is_it_thibaultgobert.util.CustomRVItemTouchListener;
import com.squareup.leakcanary.RefWatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault on 1/01/2018.
 */

public class RecyclerViewFragment extends Fragment
{
    private RecyclerView recyclerView;
    private Game game;
    private SimpsonAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState ==null) {
            game = (Game) getArguments().getSerializable("game");
        }else{
            game = (Game) savedInstanceState.getSerializable("game");
        }
        setGame();

    }


    public void setGame(){
        if(game.isPlayer1Move()){
            adapter = new SimpsonAdapter(game.getGameBoard1().getCharacters(), getActivity().getApplicationContext());
        }else{
            adapter = new SimpsonAdapter(game.getGameBoard2().getCharacters(), getActivity().getApplicationContext());
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        RecyclerView.ItemAnimator itemAnimator =  new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        initializeClickListener();
        initializeSwipeListener();
    }

    public void initializeClickListener(){
        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(getContext(), recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //set the new fragment with description
                SimpsonCharacter character = adapter.getCharacter(position);
                ((MainActivity) getActivity()).showDetailFragment(character);
            //    Toast.makeText(getActivity(), "klik", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public void initializeSwipeListener(){
         /*Listener for swipes*/
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public boolean isItemViewSwipeEnabled(){
                return !game.getHasGuessed();
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.LEFT){
                    if(game.isPlayer1Move()){
                        game.getGameBoard1().getCharacters().remove(viewHolder.getAdapterPosition());
                     //   deleteCharacter(game.getGameBoard1().getCharacters().get(viewHolder.getAdapterPosition()));
                    }else{
                        game.getGameBoard2().getCharacters().remove(viewHolder.getAdapterPosition());
                      //  deleteCharacter(game.getGameBoard2().getCharacters().get(viewHolder.getAdapterPosition()));

                    }
                   // recyclerView.removeViewAt(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    ((MainActivity) getActivity()).deleteDetail();
                    Toast.makeText(getActivity(), "Eliminated", Toast.LENGTH_SHORT).show();
                }
                if(direction == ItemTouchHelper.RIGHT){
                    String name =  adapter.getCharacter(viewHolder.getAdapterPosition()).getName();
                    if(game.guess(name)){
                        Toast.makeText(getActivity(), "Proficiat, je bent gewonnen!", Toast.LENGTH_LONG).show();
                        game.setGuessedCorrect(true);
                    }else{
                        Toast.makeText(getActivity(), "Jammer, je bent verloren. ", Toast.LENGTH_LONG).show();
                    }
                    ((MainActivity) getActivity()).showEnding();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        game.setOnGoing(true);
        outState.putSerializable("game",game);

    }



        private void deleteCharacter(SimpsonCharacter character){
        String selection = CharacterContract.CharacterEntry.COLUMN_IMAGE + " = ?";
        String[] selectionArgs = {String.valueOf(character.getImagePath())};
        int rowsDeleted = getActivity().getContentResolver().delete(CharacterContract.CharacterEntry.CONTENT_URI, selection, selectionArgs);
        //delete from internal storage
       // String[] split = character.getImagePath().split("/");//to avoid path separators
        //getActivity().getApplicationContext().deleteFile(split[split.length-1]);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        RefWatcher refWatcher = MemoryLeakApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }



}
