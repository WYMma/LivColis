package org.example.livcolis;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GestionColis {
    private final ConcurrentLinkedQueue<Colis> colis = new ConcurrentLinkedQueue<>();

    public void enregistrerColis(Colis nouveauColis) {
        colis.add(nouveauColis);
    }

    public ConcurrentLinkedQueue<Colis> getColis() {
        return colis;
    }
}

