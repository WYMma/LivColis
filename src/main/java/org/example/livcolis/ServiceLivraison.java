package org.example.livcolis;

public class ServiceLivraison implements Runnable {
    private final GestionColis gestion;
    private final Runnable onStatusChangeCallback; // Callback to refresh the UI

    public ServiceLivraison(GestionColis gestion, Runnable onStatusChangeCallback) {
        this.gestion = gestion;
        this.onStatusChangeCallback = onStatusChangeCallback;
    }

    @Override
    public void run() {
        while (true) {
            for (Colis parcel : gestion.getColis()) {
                if ("En attente".equals(parcel.getStatut())) {
                    parcel.setStatut("En transit");
                    onStatusChangeCallback.run(); // Notify UI
                    sleep(); // Simulate delivery time

                    parcel.setStatut("Livré");
                    onStatusChangeCallback.run(); // Notify UI
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



