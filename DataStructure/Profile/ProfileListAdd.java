package Profile;

import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Ragty
 * @Description  主要用用来分析List的性能分析（性能分析器的使用）
 * @Date 11:41 2019/4/10
 **/
public class ProfileListAdd {

    public static void main(String[] args) {
        //profileArrayListAddEnd();
        //profileArrayListAddBeginning();
       // profileLinkedListAddBegining();
        profileLinkedListAddEnd();
    }


    /**
     * @Author Ragty
     * @Description 测量在ArrayList上运行add所需的时间
     * @Date 11:54 2019/4/10
     **/
    public static void profileArrayListAddEnd() {

            Profiler.Timeable timeable = new Profiler.Timeable() {
                List<String> list;

                @Override
                public void setup(int n) { //执行在启动计时之前所需的任何工作
                    list = new ArrayList<String>();
                }

                @Override
                public void timeMe(int n) { //执行我们试图测量的任何操作
                    for (int i=0; i<n; i++) {
                        list.add("anything");
                    }
                }
            };

            Profiler profiler = new Profiler("ArrayListAddEnd",timeable);
            int startN = 4000;
            int endMils = 2000;
            //绘制图像(好像MATLAB)
            XYSeries series = profiler.timingLoop(startN,endMils);
            profiler.plotResults(series);
    }



    /**
     * @Author Ragty
     * @Description 测量在ArrayList前段上运行add所需的时间
     * @Date 15:39 2019/4/10
     **/
    public static void profileArrayListAddBeginning() {

        Profiler.Timeable timeable = new Profiler.Timeable() {
            List<String> list;
            @Override
            public void setup(int n) {
                list = new ArrayList<String>();
            }

            @Override
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0,"anything");  //使用双参方法，不断把最新的元素放到ArrayList首位
                }
            }
        };
        int startN = 4000;
        int endMillis = 10000;
        runProfiler("ArrayList add beginning", timeable, startN, endMillis);
    }



    /**
     * @Author Ragty
     * @Description 测试在LinkedList首部添加元素的时间复杂度
     * @Date 18:00 2019/4/10
     **/
    public static void profileLinkedListAddBegining() {

        Profiler.Timeable timeable = new Profiler.Timeable() {
            List<String> list;
            @Override
            public void setup(int n) {
                list = new LinkedList<String>(); //这边初始化为LinkedList
            }

            @Override
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0,"lalalalala");
                }
            }
        };
        int startN = 128000;  //这些数值是慢慢测试出来的
        int endMils = 2000;
        runProfiler("LinkedList add beginning",timeable,startN,endMils);
    }


    /**
     * @Author Ragty
     * @Description 在LinkedList尾端添加元素测试时间复杂度
     * @Date 18:08 2019/4/10
     **/
    public static void profileLinkedListAddEnd() {

        Profiler.Timeable timeable = new Profiler.Timeable() {
            List<String> list;
            @Override
            public void setup(int n) {
                list = new LinkedList<String>();
            }

            @Override
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add("CN DOTA BEST DOTA");
                }
            }
        };
        int startN = 128000;
        int endMils = 2000;
        runProfiler("LinkedListAddEnd",timeable,startN,endMils);
    }




    /**
     * @Author Ragty
     * @Description  绘图检测方法
     * @Date 15:39 2019/4/10
     **/
    private static void runProfiler(String title, Profiler.Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }


}