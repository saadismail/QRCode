/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author saad
 */
public class QRCode extends Application {
    
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    ImageView qrView = new ImageView();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("QR Code Generator");
        
        // Main Windows shetz
        GridPane mainPane = new GridPane();
        mainPane.setVgap(30);
        mainPane.setHgap(30);
        
        TextField textfield = new TextField("Enter content here");
        textfield.setMaxSize(300, 30);
        textfield.setOnAction(e -> updateQR(textfield.getText()));
        mainPane.add(textfield, 1, 1);
        
        Button generateQRbtn = new Button("Generate");
        generateQRbtn.setMaxSize(300, 30);
        generateQRbtn.setOnAction(e -> updateQR(textfield.getText()));
        mainPane.add(generateQRbtn, 1, 2);
        
        Scene mainScene = new Scene(mainPane, 360, 480);
        
        updateQR("Enter content here");
        
        mainPane.add(qrView, 1, 3);
        
        window.setScene(mainScene);
        window.show();
        
        
    }
    
    public void updateQR(String text) {
        int width = 300;
        int height = 300;
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
        } catch (WriterException ex) {
            Logger.getLogger(QRCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }
}
