package model;

import com.jhlabs.image.ChannelMixFilter;
import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.GrayFilter;
import view.MainInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ApplyFilters {
    /**
     * Reload tree after update actions are performed.
     *
     * @param filterType the filter type need to apply
     * @param input the image need to apply filter
     */
    public static void ApplyFilter(String filterType, File input) throws IOException {
        /*BufferedImage src = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        BufferedImage dst = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = src.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        channelMixFilter.filter(src, dst);*/

        BufferedImage src = ImageIO.read(new FileInputStream(input));
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(),BufferedImage.TYPE_INT_RGB);
        BufferedImage res;
        if(filterType.equals("Chrome Filter")){
            res = new ChromeFilter().filter(src,dst);
        }else if(filterType.equals("Channel Mix Filter")){
            res = new ChannelMixFilter().filter(src,dst);
        }else if(filterType.equals("Gray Filter")){
            res = new GrayFilter().filter(src,dst);
        }else{
            return;
        }

        String name = input.getName();
        String type = name.substring(name.lastIndexOf(".")+1);
        ImageIO.write(res, type, new File(MainInterface.filteredPath+ "/" + filterType + "_" + name));
    }
}
