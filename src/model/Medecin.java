package model;

public class Medecin {
    int id;
    public String nom;
    public String username;
    String password;
    String email;
    int age;
    String specialite;
    String Experience;
    int numero;
    String adresseTravail;
    int up;
    int down;
    int nbr_questions;
    byte[] image;

    public Medecin(String nom, String username, String password, int age, String specialite, String adresseTravail) {
        this.nom = nom;
        this.username = username;
        this.password = password;
        this.age = age;
        this.specialite = specialite;
        this.adresseTravail = adresseTravail;
    }

    public Medecin(String username, String specialite) {
        this.username = username;
        this.specialite = specialite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAdresseTravail() {
        return adresseTravail;
    }

    public void setAdresseTravail(String adresseTravail) {
        this.adresseTravail = adresseTravail;
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

    public int getNbr_questions() {
        return nbr_questions;
    }

    public void setNbr_questions(int nbr_questions) {
        this.nbr_questions = nbr_questions;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
