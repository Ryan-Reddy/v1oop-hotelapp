package hotel.userinterface;


import hotel.model.Hotel;
import hotel.model.KamerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class BoekingenController {
    @FXML
    private Text infoLabel;
    @FXML
    private TextField adresTextField;
    @FXML
    private TextField naamTextField;
    @FXML
    private DatePicker aankomstDatePicker;
    @FXML
    private DatePicker vertrekDatePicker;
    @FXML
    private ComboBox kamertypeComboBox;
    private Hotel hotel;

    public void initialize() throws Exception {
        System.out.println("_Init_");
        naamTextField.setText("Please enter your name...");
        adresTextField.setText("Please enter your adress...");
        vertrekDatePicker.setValue(null);
        aankomstDatePicker.setValue(null);
        kamertypeComboBox.setValue(null);
        Hotel hotel = Hotel.getHotel();
        ObservableList<KamerType> list = FXCollections.observableArrayList(hotel.getKamerTypen());
        kamertypeComboBox.setItems(list);
    }

    public void naamTextBoxClick() {
        naamTextField.setText("");
    }

    public void adressTextBoxClick() {
        adresTextField.setText("");
    }


    public void boekButtonPressed() throws IOException {
        try {
            if (naamTextField.getText() == "" && adresTextField.getText() == "") {
                infoLabel.setText("Naam & Adres zijn vereist: ");
            } else if (naamTextField.getText() == "") {
                naamTextField.setText("Voer aub uw naam in!");
            } else if (adresTextField.getText() == "") {
                adresTextField.setText("Voer aub uw adres in!");
            }
            if (aankomstDatePicker.getValue().isBefore(LocalDate.now()) || vertrekDatePicker.getValue().isBefore(LocalDate.now()) || aankomstDatePicker.getValue() == null || vertrekDatePicker.getValue() == null) {
                infoLabel.setText("Kies een periode die in de toekomst ligt aub.");
                aankomstDatePicker.setValue(null);
                vertrekDatePicker.setValue(null);
            } else if (kamertypeComboBox.getValue() == null) {
                infoLabel.setText("Kies aub gewenst kamertype. ");
            } else if (aankomstDatePicker.getValue().isAfter(LocalDate.now()) && vertrekDatePicker.getValue().isAfter(LocalDate.now())) {
                try {
                    Hotel hotel = Hotel.getHotel();
                    hotel.voegBoekingToe(aankomstDatePicker.getValue(), vertrekDatePicker.getValue(), naamTextField.getText(), adresTextField.getText(), (KamerType) kamertypeComboBox.getValue());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        public void resetFields() throws Exception {
            initialize();
        }
    }






