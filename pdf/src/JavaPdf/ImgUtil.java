package JavaPdf;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

                    System.out.println(width+"&&&&&&"+height);
                    System.out.println(bd);

                    BigDecimal standaraHeight = BigDecimal.valueOf(842);
                    BigDecimal standaraWeight = BigDecimal.valueOf(595);
                    BigDecimal scaleHeight = standaraHeight.divide(height,4,BigDecimal.ROUND_CEILING);
                    BigDecimal scaleWidth = standaraWeight.divide(width,4,BigDecimal.ROUND_CEILING);
                    //补偿
                    BigDecimal realScale = scaleHeight.add(scaleWidth).divide(BigDecimal.valueOf(2),2,BigDecimal.ROUND_CEILING).add(BigDecimal.valueOf(0.27));


                    if (height.compareTo(standaraHeight) == 1) { //大于标准高度，缩放

                    }


                    System.out.println("调整图片大小 ..." +realScale);
                    bufferedImage = Thumbnails.of(_img_file_).scale(realScale.floatValue()).asBufferedImage();

                    if (bd.compareTo(theValue) == -1 ) {
                        System.out.println("转换中 ...");
                        bufferedImage = Thumbnails.of(src).rotate(angel).scale(realScale.floatValue()).asBufferedImage();
                    }else {
                        System.out.println("大小合适,跳过 ...");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }



    }