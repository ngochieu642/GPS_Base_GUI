package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jssc.SerialPortList;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    /*FXML components*/
    @FXML
    public TextField textFieldIP;
    @FXML
    public TextField textFieldPort;
    @FXML
    public ComboBox<String> comboBoxCOM;
    ObservableList<String> listPort;
    @FXML
    public ComboBox<String> comboBoxBaud;
    ObservableList<String> listBaud = FXCollections.observableArrayList("600","1200","2400","4800","9600","14400","19200",
            "38400","56000","57600","115200");
    @FXML
    public TextArea msgTCP;
    @FXML
    public TextArea msgSerial;
    

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxBaud.setItems(listBaud);
        detectPort();

    }

    public void detectPort(){/*Add port name to combo box*/
        listPort = FXCollections.observableArrayList();
        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name:serialPortNames){
            System.out.println(name);
            listPort.add(name);
        }
        comboBoxCOM.setItems(listPort);
    }
}
