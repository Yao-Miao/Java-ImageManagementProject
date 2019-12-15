package controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

import model.ApplyFilters;
import model.FileManager;
import org.im4java.core.IM4JavaException;
import view.MainInterface;

public class ButtonActions {
    private static Stage primaryStage;
    private static BorderPane root;
    /**
     * Initiate controller.ButtonActions and its static variables.
     *
     * @param primaryStage the primaryStage
     */
    public ButtonActions(Stage primaryStage,BorderPane root) {
        ButtonActions.primaryStage = primaryStage;
        ButtonActions.root = root;
    }

    /** Upload button action. */
    public static void uploadAction() {
        //Choose image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileManager fileManager = new FileManager();

        try {
            List<File> choice = fileChooser.showOpenMultipleDialog(primaryStage);
            if(choice.size() == 0){
                System.out.println("User cancelled select folder action.");
            }else{
                for(File file : choice){
                    String fileName = file.getName();
                    fileManager.fileCopy(file.toString(), MainInterface.storePath+"/"+fileName);
                }
            }
        } catch (NullPointerException | FileNotFoundException ex) {
            System.out.println("User cancelled select folder action.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeManager.reloadTree(MainInterface.storePath);
    }
    /** Download button action. */
    public static void downloadAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileManager fileManager = new FileManager();
        try {
            File choice = directoryChooser.showDialog(primaryStage);
            if (!choice.isDirectory()) {
                showAlert("error", "This is not a directory.", "");
            } else {
                String fileName = MainInterface.downloadFilePath.getName();
                fileManager.fileCopy(MainInterface.downloadFilePath.toString(), choice.toString() +"/"+fileName);
                showAlert("info", "Information","Download Successful.");
            }
        } catch (NullPointerException | IOException ex) {
            System.out.println("User cancelled select folder action.");
        }
    }

    /** View Image button action. */
    public static void viewUploadedImageAction() {
        TreeManager.reloadTree(MainInterface.storePath);
    }

    /** Convert Image button action. */
    public static void convertImageAction() {
        root.setRight(MainInterface.convertSection());
    }

    /** View converted Image button action. */
    public static void viewConvertedImageAction() {
        TreeManager.reloadTree(MainInterface.convertedPath);
    }

    /** Add Filter button action. */
    public static void AddFilterAction() {
        root.setRight(MainInterface.filterSection());
    }

    /** View Filtered Image button action. */
    public static void viewFilteredImageAction() {
        TreeManager.reloadTree(MainInterface.filteredPath);
    }

    /** Confirm Convert button action. */
    public static void confirmConvertAction(String outputFormat) throws InterruptedException, IOException, IM4JavaException {
        FileManager.transferFormat(outputFormat,MainInterface.downloadFilePath);
        TreeManager.reloadTree(MainInterface.convertedPath);
        showAlert("info", "Information","Convert Successful.");
    }


    /** Apply Filter button action. */
    public static void applyFilterAction(String filterType) throws InterruptedException, IOException, IM4JavaException {
        ApplyFilters.ApplyFilter(filterType, MainInterface.downloadFilePath);
        TreeManager.reloadTree(MainInterface.filteredPath);
        showAlert("info", "Information","Apply Filter Successful.");
    }


    /**
     * Show information or error alerts.
     *
     * @param type type of alert
     * @param title title of alert
     * @param content content of alert (not required in error)
     */
    private static void showAlert(String type, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        if (type.equals("info")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(content);
        }

        if (type.equals("error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText(title);
        }
        alert.showAndWait();
    }
}
