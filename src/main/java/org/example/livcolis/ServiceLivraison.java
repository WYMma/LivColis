package org.example.livcolis;

import java.util.concurrent.Semaphore;

public class ServiceLivraison implements Runnable {
    private final GestionColis gestion;
    private final Runnable onStatusChangeCallback;
    private final Semaphore semaphore; // Limit concurrent delivery threads

    public ServiceLivraison(GestionColis gestion, Runnable onStatusChangeCallback) {
        this.gestion = gestion;
        this.onStatusChangeCallback = onStatusChangeCallback;
        this.semaphore = new Semaphore(1); // Only one delivery at a time
    }

    @Override
    public void run() {
        while (true) {
            for (Colis parcel : gestion.getColis()) {
                if ("En attente".equals(parcel.getStatut())) {
                    try {
                        semaphore.acquire(); // Acquire a permit before proceeding
                        parcel.setStatut("En transit");
                        onStatusChangeCallback.run(); // Notify UI
                        sleep(); // Simulate delivery time

                        parcel.setStatut("Livré");
                        onStatusChangeCallback.run(); // Notify UI
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release(); // Release the permit
                    }
                }
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}




