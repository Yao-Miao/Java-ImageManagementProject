package controller;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import view.MainInterface;

import java.io.File;
import java.util.Objects;

/** Control the file tree **/
public class TreeManager {
    private static TreeView<File> treeView = new TreeView<>();

    /**
     * Construction: Instantiates a new Tree manager.
     *
     * @param centerBox the VBox in the center of root
     */
    public TreeManager(VBox centerBox){
        /*change the displayed name of tree node: only display the filename */
        treeView.setCellFactory(
                new Callback<TreeView<File>, TreeCell<File>>() {

                    public TreeCell<File> call(TreeView<File> tv) {
                        return new TreeCell<File>() {

                            @Override
                            protected void updateItem(File item, boolean empty) {
                                super.updateItem(item, empty);

                                setText((empty || item == null) ? "" : item.getName());
                            }
                        };
                    }
                });
        /*display image in center when a tree node is double clicked*/
        treeView.setOnMouseClicked(
                mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) {
                        TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
                        if(item.getValue().isFile()){
                            File img = item.getValue();
                            String image_path = img.toString();

                            MainInterface.downloadFilePath = img;
                            centerBox.setSpacing(1);
                            centerBox.getChildren().clear();
                            centerBox.getChildren().addAll(MainInterface.imgView(image_path), MainInterface.imageInfo(img));
                        }

                    }
                });

    }
    /**
     * Gets tree.
     *
     * @return the tree
     */
    public static TreeView<File> getTree() {
        return treeView;
    }

    /**
     * Reload tree after update actions are performed.
     *
     * @param path the path
     */
    public static void reloadTree(File path) {
        treeView.setRoot(getNodesForDirectory(path));
        treeView.getRoot().setExpanded(true);
    }

    /**
     * Build a tree of images from selected folder
     * https://stackoverflow.com/questions/35070310/javafx-representing-directories
     *
     * @param directory generate file tree from this directory
     * @return root of said directory and its internal files/folders
     */
    private static TreeItem<File> getNodesForDirectory(File directory) {
        TreeItem<File> root = new TreeItem<>(directory);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length != 0) {
                    root.getChildren().add(getNodesForDirectory(file));
                } else {
                    if (name.endsWith("bmp")
                            || name.endsWith("jpg")
                            || name.endsWith("jpeg")
                            || name.endsWith("gif")
                            || name.endsWith("png")) {
                        root.getChildren().add(new TreeItem<>(file));
                    }
                }
            }
        } else {
            System.out.println("File error, please try again.");
        }

        return root;
    }


}
