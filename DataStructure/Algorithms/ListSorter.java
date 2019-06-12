package Algorithms;

import java.util.*;

public class ListSorter<T> {


    /**
     * @Author Ragty
     * @Description 插入排序
     * @Date 18:26 2019/6/7
     **/
    public void insertionSort(List<T> list, Comparator<T> comparator) {
        for (int i=1; i<list.size(); i++) {
            T elt_i = list.get(i);
            int j = i;

            while (j>0) {
                T elt_j = list.get(j-1);
                if (comparator.compare(elt_i,elt_j)>=0) {
                    break;
                }
                list.set(j,elt_j);
                j--;
            }
            list.set(j,elt_i);
        }
    }



    /**
     * @Author Ragty
     * @Description 归并排序
     * @Date 10:02 2019/6/12
     **/
    public void mergeSortInplace(List<T> list,Comparator<T> comparator) {
        List<T> sorted = mergeSort(list,comparator);
        list.clear();
        list.addAll(sorted);
    }



    /**
     * @Author Ragty
     * @Description 分割list并排序
     * @Date 10:10 2019/6/12
     **/
    public List<T> mergeSort(List<T> list,Comparator<T> comparator) {
        int size = list.size();
        if (size <= 1) {
            return  list;
        }
        //每次让list中的元素减半(递归)
        List<T> first = mergeSort(new LinkedList<T>(list.subList(0,size/2)),comparator);
        List<T> second = mergeSort(new LinkedList<T>(list.subList(size/2,size)),comparator);

        return merge(first,second,comparator);
    }


    /**
     * @Author Ragty
     * @Description 将两个排序好的list,合并为一个排序好的List(常数时间)
     * @Date 10:19 2019/6/12
     **/
    private List<T> merge(List<T> first,List<T> second, Comparator<T> comparator) {
        List<T> result = new LinkedList<T>();
        int total = first.size()+second.size();

        for(int i=0; i<total; i++) {
            List<T> winner = pickWinner(first,second,comparator);
            result.add(winner.remove(0));
        }
        return result;
    }



    /**
     * @Author Ragty
     * @Description 返回第一个元素较小的list,任意列表为空，返回另一个list
     * @Date 10:52 2019/6/12
     **/
    private List<T> pickWinner(List<T> first,List<T> second,Comparator<T> comparator) {
        if (first.size() == 0) {
            return second;
        }
        if (second.size() == 0) {
            return first;
        }
        int res = comparator.compare(first.get(0),second.get(0));
        if (res < 0) {
            return first;
        }
        if (res > 0) {
            return second;
        }
        return  first;
    }


    /**
     * @Author Ragty
     * @Description 堆排序
     * @Date 19:15 2019/6/12
     **/
    public void heapSort(List<T> list,Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(),comparator);
        heap.addAll(list);
        list.clear();
        while(!heap.isEmpty()) {
            list.add(heap.poll());
        }
    }



    /**
     * @Author Ragty
     * @Description 有界堆排序
     * @Date 19:49 2019/6/12
     **/
    public List<T> topK(int k,List<T> list,Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(),comparator);
        for (T element : list) {
            if (heap.size() < k) {
                heap.offer(element);
                continue;
            }
            int cmp = comparator.compare(element,heap.peek());
            if (cmp>0) {
                heap.poll();
                heap.offer(element);
            }
        }
        List<T> res = new LinkedList<T>();
        while (!heap.isEmpty()) {
            res.add(heap.poll());
        }
        return res;
    }





    public static void main(String[] args) {

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer elt1, Integer elt2) {
                return elt1.compareTo(elt2);
            }
        };

        ListSorter<Integer> sorter = new ListSorter<Integer>();
        sorter.insertionSort(list, comparator);
        System.out.println(list);

        //归并排序
        list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
        sorter.mergeSortInplace(list, comparator);
        System.out.println(list);

        //堆排序
        list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
        sorter.heapSort(list, comparator);
        System.out.println(list);

        // 有界堆排序
        list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
        List<Integer> queue = sorter.topK(3, list, comparator);
        System.out.println(queue);

    }






}