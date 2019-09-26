package com.apk.uaspwpb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String NOTE_ID = "noteid";
    public static final String NOTE_DESC = "notedesc";
    public static final String NOTE_JUDUL = "notejudul";

    FloatingActionButton btnTambah;
    ListView listViewNote;
    DatabaseReference db_note;
    List<Note> noteList;
    EditText inputJudul;
    EditText inputDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnTambah = findViewById(R.id.btnTambah);
        listViewNote = findViewById(R.id.listView);
        noteList = new ArrayList<>();
        inputJudul = findViewById(R.id.edtJudul);
        inputDesc = findViewById(R.id.edtDes);
        db_note = FirebaseDatabase.getInstance().getReference("note");

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNote();
            }
        });

        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = noteList.get(i);

                showDialog(note.getNoteId(),note.getNoteJudul(),note.getNoteDeskripsi());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db_note.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noteList.clear();

                for(DataSnapshot noteSnapshot : dataSnapshot.getChildren()){
                    Note note = noteSnapshot.getValue(Note.class);

                    noteList.add(note);
                }
                NoteList Adapter = new NoteList(HomeActivity.this,noteList);
                listViewNote.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void AddNote(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_manage,null);
        alert.setView(dialogView);

        final EditText inputJudul = dialogView.findViewById(R.id.edtJudul);
        final EditText inputDesc = dialogView.findViewById(R.id.edtDes);
        final Button buttonSubmit = dialogView.findViewById(R.id.btnSubmit);

        alert.setTitle("Tambah Note");
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = inputJudul.getText().toString().trim();
                String desc = inputDesc.getText().toString().trim();

                if (!TextUtils.isEmpty(judul)){
                    String id = db_note.push().getKey();
                    Note note = new Note(id,judul,desc);
                    db_note.child(id).setValue(note);

                    Toast.makeText(getApplicationContext(), "Note Ditambahkan", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "Silahkan Isi Kembali Note", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

    }

    private void showDialog(final String noteId, String noteJudul , String noteDeskripsi){

        AlertDialog.Builder dialogBuild = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_option,null);
        dialogBuild.setView(dialogView);

        final TextView buttonDelete = dialogView.findViewById(R.id.txtDelete);
        final TextView buttonEdit= dialogView.findViewById(R.id.txtEdit);

        dialogBuild.setTitle("Option");

        final AlertDialog alertDialog = dialogBuild.create();
        alertDialog.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(noteId);

                alertDialog.dismiss();
            }
        });

    }

    private void delete(String noteId){
        DatabaseReference db_note = FirebaseDatabase.getInstance().getReference("note").child(noteId);

        db_note.removeValue();

        Toast.makeText(this, "Note Dihapus", Toast.LENGTH_LONG).show();
    }


//    private boolean update(String noteId, String noteJudul, String noteDeskripsi){
//        DatabaseReference db_note = FirebaseDatabase.getInstance().getReference("note").child(noteId);
//        Note note = new Note(noteId,noteJudul,noteDeskripsi);
//        db_note.setValue(note);
//
//        Toast.makeText(this, "Note Telah Diupdate", Toast.LENGTH_LONG).show();
//        return true;
//    }

}

