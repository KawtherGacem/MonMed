package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Question extends RecursiveTreeObject<Question> {
    int id;
    public String titre;
    public String specialite;
    public String text;
    int up;
    int down;
    int idMalade;

    public Question( String titre,String specialite, int up, int down) {
        this.titre = titre;
        this.specialite = specialite;
        this.up = up;
        this.down = down;
    }

    public Question(String titre, String specialite, String text, int up, int down, int idMalade) {
        this.titre = titre;
        this.specialite = specialite;
        this.text = text;
        this.up = up;
        this.down = down;
        this.idMalade = idMalade;
    }

    public Question(int id ,String titre, int up, int down) {
        this.id = id;
        this.titre = titre;
        this.up = up;
        this.down = down;
    }

    public Question(int id, String titre, String text, int up, int down) {
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.up = up;
        this.down = down;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdMalade() {
        return idMalade;
    }

    public void setIdMalade(int idMalade) {
        this.idMalade = idMalade;
    }

    public String getTitre() {
        return titre;
    }
}
