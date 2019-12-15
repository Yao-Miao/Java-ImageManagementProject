package view;

import controller.ButtonActions;
import controller.TreeManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ImageInformation;
import org.im4java.core.IM4JavaException;

import java.io.File;
import java.io.IOException;

/**
 * @author Yao Miao and Xin Wen
 * crete data: Oct 01 2019
 * version 3.0
 * main function:
 * 1.Allow user to upload image file(s) from desktop/laptop
 * 2.When uploaded, show image
 * 3.Show image properties
 * 4.Convert image to various formats
 * 5.Download converted images
 * 6.Apply various filters
 **/

public class MainInterface extends Application {
    /* the main pane */
    private static BorderPane root = new BorderPane();
    private static VBox centerBox = new VBox();
    private static VBox rightBox = new VBox();
    public static File storePath = new File("out/Image Storage");
    public static File convertedPath = new File("out/Converted Image");
    public static File filteredPath = new File("out/Filtered Image");
    public static File downloadFilePath;//present download path
    /** The entrance of application. **/
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Return a HBox for the top section of the app.
     *
     * @return the HBox
     *
     */
    private static HBox topBar() {

        Button upLoadImg = new Button("Upload");
        upLoadImg.setOnAction(event -> ButtonActions.uploadAction());

        Button downloadImg = new Button("DownLoad");
        downloadImg.setOnAction(event -> ButtonActions.downloadAction());

        Button viewImage = new Button("View Uploaded Image");
        viewImage.setOnAction(event -> ButtonActions.viewUploadedImageAction());


        Button convertImage = new Button("Convert Image");
        convertImage.setOnAction(event -> ButtonActions.convertImageAction());

        Button convertedImage = new Button("View Converted Image");
        convertedImage.setOnAction(event -> ButtonActions.viewConvertedImageAction());

        Button addFilter = new Button("Add Filter");
        addFilter.setOnMouseClicked(event -> ButtonActions.AddFilterAction());

        Button filteredImage = new Button("View Filtered Image");
        filteredImage.setOnMouseClicked(event -> ButtonActions.viewFilteredImageAction());

        HBox top = new HBox();
        top.setSpacing(10);
        top.getChildren().addAll(upLoadImg, downloadImg, viewImage, convertImage,convertedImage, addFilter,filteredImage);
        return top;
    }


    /**
     * Return a ImageView when a image in the file tree is selected.
     *
     * @param path Path of image.
     * @return the image view
     */
    public static ImageView imgView(String path) {
        String img_path = "file:" + path;
        Image image = new Image(img_path);
        ImageView img = new ImageView();
        img.setImage(image);
        img.setFitWidth(600);
        img.setFitHeight(350);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);

        return img;
    }
    /**
     * Return a VBox which contains image information when a image in the file tree is selected.
     * @param image the image file
     * @return the GridPane
     */
    public static GridPane imageInfo(File image){

        GridPane infoGridPane = new GridPane();
        infoGridPane.setVgap(10);
        infoGridPane.setHgap(60);
        infoGridPane.setPrefHeight(170);
        infoGridPane.setPadding(new Insets(10, 10, 0, 10));
        ImageInformation.getInformation(infoGridPane,image);

        return infoGridPane;
    }
    /**
     * Return a GridPane which be used for convert operation
     *
     * @return the GridPane
     */
    public static GridPane convertSection(){

        //Label for format
        Text formatLabel = new Text("Format");

        //Choice box for format
        ChoiceBox formatBox = new ChoiceBox();
        formatBox.getItems().addAll("jpg", "png", "gif","bmp");
        formatBox.setPrefWidth(100);
        formatBox.getSelectionModel().getSelectedItem();
        Button confirmConvert = new Button("Confirm");
        confirmConvert.setOnAction(event -> {
            try {
                ButtonActions.confirmConvertAction((String)formatBox.getSelectionModel().getSelectedItem());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IM4JavaException e) {
                e.printStackTrace();
            }
        });

        GridPane gridPane = new GridPane();
        //Setting the padding
        gridPane.setPrefWidth(200);
        gridPane.setPadding(new Insets(0, 10, 0, 10));
        //Setting the vertical and horizontal gaps between the columns
        gridPane.setHgap(10);
        gridPane.setVgap(20);

        gridPane.setStyle("-fx-background-color:#ffffff");

        //Arranging all the nodes in the grid
        gridPane.add(formatLabel, 0, 0);
        gridPane.add(formatBox, 1, 0);

        gridPane.add(confirmConvert, 1, 1);
        return gridPane;
    }

    /**
     * Return a GridPane which be used for filter operation
     *
     * @return the GridPane
     */
    public static GridPane filterSection(){

        //Label for filter
        Text filterLabel = new Text("Filter");

        //Choice box for format
        ChoiceBox filterBox = new ChoiceBox();
        filterBox.getItems().addAll("Chrome Filter","Channel Mix Filter","Gray Filter");
        filterBox.setPrefWidth(100);
        filterBox.getSelectionModel().getSelectedItem();
        Button applyFilter = new Button("Apply");
        applyFilter.setOnAction(event -> {
            try {
                ButtonActions.applyFilterAction((String)filterBox.getSelectionModel().getSelectedItem());
            } catch (InterruptedException | IOException | IM4JavaException e) {
                e.printStackTrace();
            }
        });

        GridPane gridPane = new GridPane();
        //Setting the padding
        gridPane.setPrefWidth(200);
        gridPane.setPadding(new Insets(0, 10, 0, 10));
        //Setting the vertical and horizontal gaps between the columns
        gridPane.setHgap(10);
        gridPane.setVgap(20);

        gridPane.setStyle("-fx-background-color:#ffffff");

        //Arranging all the nodes in the grid
        gridPane.add(filterLabel, 0, 0);
        gridPane.add(filterBox, 1, 0);

        gridPane.add(applyFilter, 1, 1);
        return gridPane;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Manage tree functions*/
        TreeManager treeManager = new TreeManager(centerBox);
        TreeView<File> tree = TreeManager.getTree();
        TreeManager.reloadTree(storePath);
        centerBox.setPadding(new Insets(20,10,0,10));
        centerBox.setPrefWidth(600);
        centerBox.setAlignment(Pos.CENTER);

        ButtonActions buttonActions = new ButtonActions(primaryStage,root);

        /* set pane*/
        root.setTop(topBar());
        root.setLeft(tree);
        root.setCenter(centerBox);
        root.setRight(rightBox);
        /* set stage */
        primaryStage.setTitle("Image Management Tool");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();
    }
}