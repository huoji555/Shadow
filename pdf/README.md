### 1.需求
将同一文件夹下的图片和PDF,转换合并为同一个PDF，并要求转换中的图片按格式排版好 <br><br>

### 2.思路
1. 调整图片，检测图片大小及方向，调整至A4大小(842×595)， 寻找最佳缩放比，等比例缩放图片，为避免多次IO操作，返回BufferedImage
2. 将图片转换为PDF
3. 将文件夹下的PDF按顺序合并，得到最终的final.pdf<br><br>


### 3.所需工具类
前两个为处理PDF的工具类，最后一个为图片处理类

```js
<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13</version>
</dependency>
```

```js
<!-- https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox -->
<dependency>
    <groupId>org.apache.pdfbox</groupId>
    <artifactId>pdfbox</artifactId>
    <version>1.7.1</version>
</dependency>
```

```js
<!-- https://mvnrepository.com/artifact/net.coobird/thumbnailator -->
<dependency>
    <groupId>net.coobird</groupId>
    <artifactId>thumbnailator</artifactId>
    <version>0.4.8</version>
</dependency>
```
<br><br>


### 4.图片处理类

```java
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
```
<br><br>


### 5.PDF处理类

```js
package JavaPdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.util.PDFMergerUtility;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class PdfUtil {


    /**
     * @Author Ragty
     * @Description 将图片转换为PDF
     * @Date 15:27 2019/3/4
     * @return
     **/
    public static String Img2PDF(String imagePath,BufferedImage img,String descfolder) throws Exception{
        String pdfPath = "";
        try {
            //图片操作
            Image image = null;
            File file = new File(descfolder);

            if (!file.exists()){
                file.mkdirs();
            }

            pdfPath = descfolder +"/"+System.currentTimeMillis()+".pdf";
            String type = imagePath.substring(imagePath.lastIndexOf(".")+1);
            Document doc = new Document(null, 0, 0, 0, 0);

            //更换图片图层
            BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
            bufferedImage.getGraphics().drawImage(img, 0,0, img.getWidth(), img.getHeight(), null);
            bufferedImage=new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null).filter (bufferedImage,null);

            //图片流处理
            doc.setPageSize(new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight()));
            System.out.println(bufferedImage.getWidth()+"()()()()()"+bufferedImage.getHeight());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(bufferedImage, type, out);
            byte[] b = out.toByteArray();
            image = Image.getInstance(b);

            //写入PDF
            System.out.println("写入PDf:" + pdfPath);
            FileOutputStream fos = new FileOutputStream(pdfPath);
            PdfWriter.getInstance(doc, fos);
            doc.open();
            doc.add(image);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return pdfPath;
    }




    /**
     * @Author Ragty
     * @Description 获取文件夹下的PDF
     * @Date 17:33 2019/3/7
     * @return
     **/
    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;

        if (_folder.isDirectory()) {
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("Path is not a directory");
        }
    }




    /**
     * @Author Ragty
     * @Description 合成PDF
     * @Date 17:25 2019/3/7
     * @return
     **/
    public static void mergePDF(String[] files,String desfolder,String mergeFileName ) throws Exception{

        PDFMergerUtility mergePdf = new PDFMergerUtility();

        for (String file :files) {
            if (file.toLowerCase().endsWith("pdf"))
                mergePdf.addSource(file);
        }

        mergePdf.setDestinationFileName(desfolder+"/"+mergeFileName);
        mergePdf.mergeDocuments();
        System.out.println("merge over");

    }




    public static void main(String[] args) throws Exception{

        ImgUtil imageUtil = new ImgUtil();
        String[] files = new String[11];
        files[0] = "D:/test2/2019021910311346.jpg";
        files[1] = "D:/test2/2019021816284226.jpg";
        files[2] = "D:/test2/2019021816284244.png";
        files[3] = "D:/test2/2019021816284299.jpg";
        files[4] = "D:/test2/2019021816284346.png";
        files[5] = "D:/test2/2019021816285533.png";
        files[6] = "D:/test2/2019021816285546.jpg";
        files[7] = "D:/test2/2019021816285553.jpg";
        files[8] = "D:/test2/2019021816285640.png";
        files[9] = "D:/test2/2019021910305527.pdf";
        files[10] = "D:/test2/201902191030445.pdf";

        String folder = "D:/test2";
        String mergeFileName = "final.pdf";

        for (int i=0;i<files.length;i++) {
            String file = files[i];

            if (file.toLowerCase().endsWith(".png")
                    || file.toLowerCase().endsWith(".jpg")
                    || file.toLowerCase().endsWith(".gif")
                    || file.toLowerCase().endsWith(".jpeg")
                    || file.toLowerCase().endsWith(".gif")) {

                BufferedImage bi = imageUtil.rotateImage(file);
                if (bi==null)
                    continue;

                String pdffile = Img2PDF(file,bi,folder);
                files[i] = pdffile;
            }
        }
        mergePDF(files,folder,mergeFileName);



    }



}
```
<br><br>
### 7.测试
处理前![在这里插入图片描述](https://img-blog.csdnimg.cn/20190308094925582.png)
处理后的图片
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190308100117975.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2h1b2ppNTU1,size_16,color_FFFFFF,t_70)
合并后的PDF
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190308100206758.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2h1b2ppNTU1,size_16,color_FFFFFF,t_70)