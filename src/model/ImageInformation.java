package model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

public class ImageInformation {
    /**
     * Use metadata-extractor to Get the ex Information of image.
     * Need to import  metadata-extractor.jar and xmpcore.jar.
     *
     * @param img
     * @throws JpegProcessingException
     * @throws IOException
     */
    public static void getInformation(GridPane infoGridPane, File img){
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(img);
            int r = -1;
            int c = 0;
            for(Directory dir : metadata.getDirectories()){
                for(Tag tag : dir.getTags()){
                    String tagName = tag.getTagName();
                    String describe = tag.getDescription();
                    //System.out.println(tagName+":   " + describe);
                    if(tagName.equals("Image Height")  || tagName.equals("Image Width")
                            || tagName.equals("File Name") || tagName.equals("File Size")
                            || tagName.equals("Color space") || tagName.equals("Technology")
                            || tagName.equals("Device manufacturer") || tagName.equals("Device model")
                            || tagName.equals("File Modified Date") || tagName.equals("Signature")){
                        if(c % 2 == 0){
                            r++;
                        }
                        Text text = new Text(tagName + ":    " + describe);
                        infoGridPane.add(text,c % 2,r);
                        c++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
