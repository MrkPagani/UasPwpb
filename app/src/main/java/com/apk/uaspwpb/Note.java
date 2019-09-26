package com.apk.uaspwpb;

public class Note {

    private String noteId;
    private String noteJudul;
    private String noteDeskripsi;

    public Note(){

    }

    public Note(String noteId, String noteJudul, String noteDeskripsi) {
        this.noteId = noteId;
        this.noteJudul = noteJudul;
        this.noteDeskripsi = noteDeskripsi;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteJudul() {
        return noteJudul;
    }

    public String getNoteDeskripsi() {
        return noteDeskripsi;
    }
}
