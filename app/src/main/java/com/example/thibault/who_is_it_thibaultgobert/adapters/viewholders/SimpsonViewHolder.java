package com.example.thibault.who_is_it_thibaultgobert.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 1/01/2018.
 */

public class SimpsonViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.cardView)
    CardView cv;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.characterImage)
    ImageView characterImage;

    public SimpsonViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(SimpsonCharacter character){
        name.setText(character.getName());
        description.setText(character.getDescription());
        characterImage.setImageResource(character.getCharacterImage());
        Context context = characterImage.getContext();

        loadImageFromStorage(character.getImagePath(),300);

        //  Picasso.with(context).load(character.getCharacterImage()).resize(400,400).centerInside().into(characterImage);
    }
    private void loadImageFromStorage(String path, int size)
    {
        try {
            Picasso.with(characterImage.getContext()).load("file://"+path).resize(size, size).centerInside().into(characterImage);
        }
        catch (Exception e)
        {
            Picasso.with(characterImage.getContext()).load(R.drawable.questionmark).resize(size, size).centerCrop().into(characterImage);
            e.printStackTrace();
        }

    }
}
