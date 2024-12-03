package org.example.livcolis;

public class Colis {
    private static int idCounter = 1; // Auto-incremented ID
    private final int id;
    private String statut; // "En attente", "En transit", "Livré"
    private final String ownerName;

    public Colis(String ownerName) {
        this.id = idCounter++;
        this.ownerName = ownerName;
        this.statut = "En attente";
    }

    public int getId() {
        return id;
    }

    public synchronized String getStatut() {
        return statut;
    }

    public synchronized void setStatut(String statut) {
        this.statut = statut;
    }

    public String getOwnerName() {
        return ownerName;
    }
}



