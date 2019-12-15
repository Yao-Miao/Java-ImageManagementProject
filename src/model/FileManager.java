package model;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import view.MainInterface;


import java.io.*;



import javax.imageio.ImageIO;

/**
 * Be used for file management include copy, delete, transfer format etc.
 *
 */
public class FileManager {
    /**
     * copy a file from input path to output path
     *
     * @param input original file path
     * @param output target file path
     */
    public void fileCopy(String input, String output) throws IOException {
        FileInputStream inputStream = new FileInputStream(input);
        FileOutputStream outputStream = new FileOutputStream(output);
        int len = 0;
        while ((len = inputStream.read()) != -1) {
            outputStream.write(len);
        }
        outputStream.close();
        outputStream.close();
    }
    /**
     * transfer Format
     *
     * @param outputFormat target file format
     * @param input original file path
     */
    public static void transferFormat(String outputFormat, File input) throws InterruptedException, IOException, IM4JavaException {
        String output = MainInterface.convertedPath.toString();
        String str = input.getName();
        str = str.substring(0,str.lastIndexOf("."));
        output = output + "/" + str + "." + outputFormat;
        IMOperation op = new IMOperation();
        op.addImage(input.toString());
        op.addImage(output);
        System.out.println(op);
        ConvertCmd cmd = new ConvertCmd();
        cmd.run(op);
    }
}
