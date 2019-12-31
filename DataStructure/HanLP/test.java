package HanLP;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {

    /**
     * 去除停用词
     * @param oldString：原中文文本
     * @return 去除停用词之后的中文文本
     * @throws IOException
     */
    public static String RemovalOfStopWords(String oldString) throws IOException {
        String newString = oldString;

        // 分词
        List<Term> termList = HanLP.segment(newString);
        System.out.println(termList);


        // 中文 停用词 .txt 文件路径
        String filePath = "D://stopwords.txt";
        File file = new File(filePath);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> stopWords = new ArrayList<>();
        String temp = null;
        while ((temp = bufferedReader.readLine()) != null) {
            stopWords.add(temp.trim());
        }

        List<String> termStringList = new ArrayList<>();
        for(Term term:termList) {
            termStringList.add(term.word);
        }

        termStringList.removeAll(stopWords);

        newString = "";
        for (String string:termStringList) {
            newString += string;
        }

        return newString;
    }

    public static ReadStopWords readStopWords;


    public static void main(String[] args) throws IOException{

        try {
            System.out.println(RemovalOfStopWords("床前明月光，疑是地上霜。举头望明月，低头思故乡。"));
        }   catch (IOException e) {
            e.printStackTrace();
        }


    }



}
