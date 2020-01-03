package HanLP;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.recognition.ns.PlaceRecognition;
import com.hankcs.hanlp.recognition.nt.OrganizationRecognition;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.util.List;

public class HanLPTest {

    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 17:41
     *  @Description: 标准分词
     */
    public static List standardSegment(String sentences) {
        List list = HanLP.segment(sentences);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 17:56
     *  @Description: NLP分词
     */
    public static List NLPSegment(String sentences) {
        List list = NLPTokenizer.segment(sentences);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 18:13
     *  @Description: 索引分词(适用于搜索引擎索引)
     */
    public static List<Term> indexSegment(String sentences) {
        List<Term> termList = IndexTokenizer.segment(sentences);
        for (Term term : termList)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
        return termList;
    }



    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 18:23
     *  @Description: N最短路径分词(更加精确，耗时较长)
     */
    public static List<Term> nShortSegment(String sentence) {
        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
        return nShortSegment.seg(sentence);
    }



    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 19:06
     *  @Description: CRF分词，能够很好的识别新词
     */
    public static Sentence CRFSegment(String sentence) throws IOException{
        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
        Sentence sentence1 = analyzer.analyze(sentence);
        System.out.println(sentence1);
        return sentence1;

    }




    /**
     *  @author: Ragty
     *  @Date: 2019/12/31 19:08
     *  @Description: 急速分词(适用于 “高吞吐量” “精度一般” 的场合)
     */
    public static List<Term> speedSegment(String sentence) {
        List<Term> list = SpeedTokenizer.segment(sentence);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 0:34
     *  @Description: 中国人名识别(默认开启，这里这是为了强调，音译人名识别为enableTranslatedNameRecognize)
     */
    public static List<Term> NameRecognize(String sentence) {
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> list = segment.seg(sentence);
        System.out.println(list);
        return list;
    }


    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 0:44
     *  @Description: 日本人名识别(因出现频率较低，需手动开启)
     */
    public static List<Term> JapaneseNameRecognize(String sentence) {
        Segment segment = HanLP.newSegment().enableJapaneseNameRecognize(true);
        List<Term> list = segment.seg(sentence);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 0:48
     *  @Description: 地名识别(大部分在核心字典里有，默认不开启)
     */
    public static List<Term> placeRecognize(String sentence) {
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        List<Term> list = segment.seg(sentence);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 0:54
     *  @Description: 机构名识别(默认不开启)
     */
    public static List<Term> organizationRecognition(String sentence) {
        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
        List<Term> list = segment.seg(sentence);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 1:01
     *  @Description: 关键词提取
     */
    public static List<String> keyWordExtract(String content, Integer size) {
        List<String> list = HanLP.extractKeyword(content,size);
        System.out.println(list);
        return list;
    }



    /**
     *  @author: Ragty
     *  @Date: 2020/1/4 1:08
     *  @Description: 总结提取(内部用TextRangeSummary实现)
     */
    public static  List<String> summaryExtract(String content, Integer size) {
        List<String> list = HanLP.extractSummary(content,size);
        System.out.println(list);
        return list;
    }








    public static void main(String[] args) throws IOException {

        //standardSegment("商品和服务");

        //NLPSegment("我新造一个词叫幻想乡你能识别并标注正确词性吗？");
        //System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        //System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));

        /*List<Term> termList = IndexTokenizer.segment("主副食品");
        for (Term term : termList)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }*/


       //nShortSegment("今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。");

       //CRFSegment("上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观");

       //speedSegment("江西鄱阳湖干枯，中国最大淡水湖变成大草原");

        /*String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        System.out.println(SpeedTokenizer.segment(text));
        long start = System.currentTimeMillis();
        int pressure = 1000000;
        for (int i = 0; i < pressure; ++i)
        {
            SpeedTokenizer.segment(text);
        }
        double costTime = (System.currentTimeMillis() - start) / (double)1000;
        System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);*/

       //NameRecognize("签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。");

       //JapaneseNameRecognize("新垣结衣是我的老婆.");

       //placeRecognize("原平市是一个好地方");

       //organizationRecognition("电影院是不错的哦");

       //keyWordExtract("程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。",5);

       //String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。 算法可以宽泛的分为三类，一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。 二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。 三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
       //summaryExtract(document,3);


    }


}
