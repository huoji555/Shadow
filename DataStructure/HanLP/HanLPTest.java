package HanLP;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
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

    }


}
