package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

    /*Global variable*/
    SerialPort port = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxBaud.setItems(listBaud);
        detectPort();
        setDefaultTest();
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
    public void setDefaultTest(){/*Set default test for connections TCP*/
        textFieldIP.setText("mywebsite.com");
        textFieldPort.setText("3000");
    }
    public void serialConnect(){/*Call when press Connect*/
        SerialPort serialPort = new SerialPort(comboBoxCOM.getValue());
        try{
            serialPort.openPort();
            serialPort.setParams(
                    Integer.parseInt(comboBoxBaud.getValue()),
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);

            //Receive Event listener
            serialPort.addEventListener(serialPortEvent -> {
                if(serialPortEvent.isRXCHAR()){
                    try {
                        byte[] buffer = serialPort.readBytes();
                        String serialMsg = new String(buffer, StandardCharsets.UTF_8);
                        
                        msgSerial.setText(serialMsg);
                        //System.out.println(Arrays.toString(buffer));
                        System.out.println(serialMsg);
                        
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            });

            //cast port
            port = serialPort;
        }catch (Exception e){
            System.out.println("Error at serial Connect");
            e.printStackTrace();
        }
    }
    public void serialDisconnect(){
        System.out.println("Disconnect Serial port");
        if(port!=null){
            try {
                port.removeEventListener();

                if(port.isOpened())
                    port.closePort();
                
            } catch (SerialPortException e) {
                System.out.println("Error in disconnect");
                e.printStackTrace();
            }
        }
    }
}
