# 数据结构思维

### 第一章 接口

###### 1.为什么要有两种List

> Java中List接口为ArrayList, LinkedList都继承于List.哪一个更适合于特定的应用程序，取决于它最常执行的操作.对于一些应用，`LinkedList`更快；对于其他应用，`ArrayList`更快.

###### 2.List接口

```java
public class ListClientExample {
    private List list;

    public ListClientExample() {
        list = new LinkedList();
    }

    private List getList() {
        return list;        
    }

    public static void main(String[] args) {
        ListClientExample lce = new ListClientExample();
        List list = lce.getList();
        System.out.println(list);
    }
}
```

> `ListClientExample`没有任何有用的东西，但它封装了`List`，并具有一个类的基本要素。也就是说，它包含一个`List`实例变量。

> **通过实例化（也就是创建）新的`LinkedList`，这个`ListClientExample`构造函数初始化`list`；读取器方法叫做`getList`，返回内部`List`对象的引用；并且`main`包含几行代码来测试这些方法。**

> 这个例子的要点是，它尽可能地使用`List`，避免指定`LinkedList`，`ArrayList`，除非有必要。例如，实例变量被声明为`List`，并且`getList`返回`List`，但都不指定哪种类型的列表。

> 如果你改变主意并决定使用`ArrayList`，你只需要改变构造函数; 你不必进行任何其他更改。

> 这种风格被称为基于接口的编程，或者更随意，“面向接口编程”。这里我们谈论接口的一般思想，而不是 Java 接口。



### 第二章  算法分析

###### 1.简单算法分类

- 常数时间**O(1)**：不依赖于输入
- 线性**O(n)**:依赖于输入，并跟输入量大小成正比
- 平方**O(n^2)**:随着n的增长变为n^2

###### 2.简单排序

```javascript
public class SelectionSort {

    /**
     * Swaps the elements at indexes i and j.
     */
    public static void swapElements(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Finds the index of the lowest value
     * starting from the index at start (inclusive)
     * and going to the end of the array.
     */
    public static int indexLowest(int[] array, int start) {
        int lowIndex = start;
        for (int i = start; i < array.length; i++) {
            if (array[i] < array[lowIndex]) {
                lowIndex = i;
            }
        }
        return lowIndex;
    }

    /**
     * Sorts the elements (in place) using selection sort.
     */
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int j = indexLowest(array, i);
            swapElements(array, i, j);
        }
    }
}
```

swap()是常数级操作，时间恒定

indexLowest()复杂度，同n-start有关，属于线性关系

selectionSort()，需要算n(n-1)/2,属于平方关系

