package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HotelOverzichtController {
    private final Hotel hotel = Hotel.getHotel();
    @FXML
    private Label hotelnaamLabel;
    @FXML
    private ListView boekingenListView;
    @FXML
    private DatePicker overzichtDatePicker;

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
    }

    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
    }

    public void nieuweBoeking(ActionEvent actionEvent) throws Exception {
        String fxmlPagina = "Boekingen.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPagina));
        Parent root = loader.load();
        Stage boekingStage = new Stage();
        boekingStage.setTitle("Nieuwe Boeking Aanmaken");
        boekingStage.setScene(new Scene(root));
        boekingStage.initModality(Modality.APPLICATION_MODAL);
        boekingStage.showAndWait();
    }

    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();
        for (Boeking i : hotel.getBoekingen()) {
            if ((i.getAankomstDatum().isBefore(overzichtDatePicker.getValue()) || i.getAankomstDatum().isEqual(overzichtDatePicker.getValue()))
                    && (i.getVertrekDatum().isAfter(overzichtDatePicker.getValue()) || i.getVertrekDatum().isEqual(overzichtDatePicker.getValue()))){
                String s = "De kamer: " + i.getKamer() + ", gehuurd door: " + i.getBoeker().getNaam() + ", voor de periode: " + i.getAankomstDatum() + " t/m " + i.getVertrekDatum();
                boekingen.add(s);
            }
        }
        boekingenListView.setItems(boekingen);
    }
}
