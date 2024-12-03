package org.example.livcolis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationColis extends Application {
    private final GestionColis gestion = new GestionColis();
    private final ObservableList<Colis> colisObservableList = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Label statusLabel; // For success messages

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label title = new Label("Système de Gestion des Colis");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        // Input section
        TextField ownerNameInput = new TextField();
        ownerNameInput.setPromptText("Nom du propriétaire");
        Button addParcelButton = new Button("Ajouter le colis");

        // Status Label for messages
        statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;"); // Green text for success

        // Shared action for adding a parcel
        Runnable addParcelAction = () -> {
            String ownerName = ownerNameInput.getText().trim();
            if (!ownerName.isEmpty()) {
                Colis newParcel = new Colis(ownerName);
                gestion.enregistrerColis(newParcel);
                colisObservableList.add(newParcel);

                // Update status message
                updateStatusLabel("Colis #" + newParcel.getId() + " ajouté avec succès!");
                ownerNameInput.clear();
            } else {
                showAlert();
            }
        };

        // Trigger action when the button is clicked
        addParcelButton.setOnAction(event -> addParcelAction.run());

        // Trigger action when Enter is pressed in the TextField
        ownerNameInput.setOnAction(event -> addParcelAction.run());

        HBox inputSection = new HBox(10, ownerNameInput, addParcelButton);
        inputSection.setStyle("-fx-padding: 10px;");
        inputSection.setPrefHeight(50);

        // Parcel TableView
        TableView<Colis> parcelTable = new TableView<>(colisObservableList);
        TableColumn<Colis, String> idColumn = new TableColumn<>("ID Colis");
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Colis, String> nameColumn = new TableColumn<>("Propriétaire");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOwnerName()));

        TableColumn<Colis, String> statusColumn = new TableColumn<>("Statut");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatut()));

        parcelTable.getColumns().addAll(idColumn, nameColumn, statusColumn);
        parcelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox mainLayout = new VBox(10, title, inputSection, statusLabel, parcelTable);
        mainLayout.setStyle("-fx-padding: 10px;");

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Système de Gestion des Colis");
        primaryStage.show();

        // Start delivery simulation
        startDeliverySimulation(parcelTable);
    }

    private void startDeliverySimulation(TableView<Colis> tableView) {
        ServiceLivraison serviceLivraison = new ServiceLivraison(gestion, this::refreshTable);
        executorService.submit(serviceLivraison);
    }

    private void refreshTable() {
        // Run updates on the JavaFX Application Thread
        Platform.runLater(() -> {
            colisObservableList.clear();
            colisObservableList.addAll(gestion.getColis());
        });
    }

    private void updateStatusLabel(String message) {
        Platform.runLater(() -> {
            statusLabel.setText(message);

            // Clear message after 3 seconds
            scheduledExecutorService.schedule(() -> Platform.runLater(() -> statusLabel.setText("")),
                    3, TimeUnit.SECONDS);
        });
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Entrée invalide");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer un nom de propriétaire.");
        alert.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdownNow();
        scheduledExecutorService.shutdownNow();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
