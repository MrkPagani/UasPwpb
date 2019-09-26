package com.apk.uaspwpb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteList extends ArrayAdapter<Note> {

    public Activity context;
    public List<Note> noteList;

    public NoteList(Activity context,List<Note> noteList){
        super(context,R.layout.item_list,noteList);
        this.context = context;
        this.noteList = noteList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.item_list,null,true);
        TextView textJudul = listView.findViewById(R.id.txtJudul);
        TextView textDesc = listView.findViewById(R.id.txtDesc);

        Note note =  noteList.get(position);

        textJudul.setText(note.getNoteJudul());
        textDesc.setText(note.getNoteDeskripsi());

        return listView;
    }
}
