package JavaPdf;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class ImgUtil {


   /**
    * @Author Ragty
    * @Description 调整图片角度并生成合适大小的图片
    * @Date 16:37 2019/3/7
    * @return
    **/
    public BufferedImage rotateImage(String imgFile) throws Exception{
        BufferedImage bufferedImage = null;
        try {
            if(imgFile != "" || imgFile != null){
                File _img_file_ = new File(imgFile);
                if(_img_file_.exists()){
                    Integer angel = 270;
                    BufferedImage src = ImageIO.read(_img_file_);

                    BigDecimal height = BigDecimal.valueOf(src.getHeight());
                    BigDecimal width = BigDecimal.valueOf(src.getWidth());
                    BigDecimal bd = height.divide(width,4,BigDecimal.ROUND_CEILING);
                    BigDecimal theValue = BigDecimal.valueOf(1.1700);

                    if (bd.compareTo(theValue) == -1 ) {
                        System.out.println("转换中 ...");
                        bufferedImage = Thumbnails.of(src).rotate(angel).scale(1).asBufferedImage();
                        bufferedImage = calcScale(bufferedImage);
                    }else {
                        bufferedImage = calcScale(src);
                        System.out.println("宽高比合适,跳过 ...");
                    }

                    bufferedImage = getSharperPicture(bufferedImage);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }


    /**
     * @Author Ragty
     * @Description  计算最佳放缩比
     * @Date 9:19 2019/3/11
     * @return
     **/
    public BufferedImage calcScale(BufferedImage bufferedImage) throws IOException{

        BigDecimal standaraHeight = BigDecimal.valueOf(842);
        BigDecimal standaraWeight = BigDecimal.valueOf(595);
        BigDecimal height = BigDecimal.valueOf(bufferedImage.getHeight());
        BigDecimal width = BigDecimal.valueOf(bufferedImage.getWidth());

        if (height.compareTo(standaraHeight) == 1 || width.compareTo(standaraWeight) == 1) { //不符合标准，缩放
            BigDecimal scaleHeight = standaraHeight.divide(height,4,BigDecimal.ROUND_CEILING);
            BigDecimal scaleWidth = standaraWeight.divide(width,4,BigDecimal.ROUND_CEILING);

            if (scaleHeight.compareTo(BigDecimal.valueOf(1)) == -1) {
                bufferedImage = Thumbnails.of(bufferedImage).scale(scaleHeight.floatValue()).outputQuality(1.0f).asBufferedImage();
                calcScale(bufferedImage);
            }else if (scaleWidth.compareTo(BigDecimal.valueOf(1)) == -1) {
                bufferedImage = Thumbnails.of(bufferedImage).scale(scaleWidth.floatValue()).outputQuality(1.0f).asBufferedImage();
                calcScale(bufferedImage);
            }
        }

        return  bufferedImage;
    }


}