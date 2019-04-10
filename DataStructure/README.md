# 数据结构思维

### 第一章 接口

###### 1.为什么要有两种List

> Java中List接口为ArrayList, LinkedList都继承于List.哪一个更适合于特定的应用程序，取决于它最常执行的操作.对于一些应用，`LinkedList`更快；对于其他应用，`ArrayList`更快.<br>



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

> 这种风格被称为基于接口的编程，或者更随意，“面向接口编程”。这里我们谈论接口的一般思想，而不是 Java 接口。<br>



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

selectionSort()，需要算n(n-1)/2,属于平方关系<br>





### 第三章  ArrayList

###### 1.Add方法

单参方法(分析它的时间复杂度)

```js
@Override
    public boolean add(T element) {
        //make a bigger array and copy over the elements
        if (size >= array.length) {
            T[] bigger = new Object[array.length * 2];
            System.arraycopy(array,0,bigger,0,array.length);
            array = bigger;
        }
        array[size] = element;
        size++;
        return true;
    }
```

> 单参数版本很难分析。如果数组中存在**未使用的空间，那么它是常数时间**，但如果我们必须**调整数组的大小，它是线性的**，因为`System.arraycopy`所需的时间与数组的大小成正比.（既存在常数时间，又是线性的）

> 那么`add`是常数还是线性时间的？（先给结论，可以看作是常数的）

- 我们第一次调用`add`时，它会在数组中找到未使用的空间，所以它存储`1`个元素。
- 第二次，它在数组中找到未使用的空间，所以它存储`1`个元素。
- 第三次，我们必须调整数组的大小，复制`2`个元素，并存储`1`个元素。现在数组的大小是`4`。
- 第四次存储`1`个元素。
- 第五次调整数组的大小，复制`4`个元素，并存储`1`个元素。现在数组的大小是`8`。
- 接下来的`3`个添加储存`3`个元素。
- 下一个添加复制`8`个并存储`1`个。现在的大小是`16`。
- 接下来的`7`个添加复制了`7`个元素。

> 整理一下规律

- `4`次添加之后，我们储存了`4`个元素，并复制了两个。
- `8`次添加之后，我们储存了`8`个元素，并复制了`6`个。
- `16`次添加之后，我们储存了`16`个元素，并复制了`14`个。

> 现在你应该看到了规律：要执行`n`次添加，我们必须存储`n`个元素并复制`n-2`个。所以操作总数为`n + n - 2`，为`2 * n - 2`
>
> 为了得到每个添加的平均操作次数，我们将总和除以`n`；结果是`2 - 2 / n`。随着`n`变大，第二项`2 / n`变小。参考我们只关心`n`的最大指数的原则，我们可以认为`add`是常数时间的

>有时线性的算法平均可能是常数时间，这似乎是奇怪的。关键是我们每次调整大小时都加倍了数组的长度。这限制了每个元素被复制的次数。否则 - 如果我们向数组的长度添加一个固定的数量，而不是乘以一个固定的数量 - 分析就不起作用。

>这种划分算法的方式，通过计算一系列调用中的平均时间，称为摊销分析。你可以在 <http://thinkdast.com/amort> 上阅读更多信息。重要的想法是，**复制数组的额外成本是通过一系列调用展开或“摊销”的。**<br>

<br>

双参方法

```js
   @Override
    public void add(int index, T element) {
        if (index<0 || index>size) {
            throw IndexOutOfBoundsException;
        }

        //new element（扩容之需）
        add(element);

        //shift the other element
        for (int i=size-1; i>index; i--) {
            array[i] = array[i-1];
        }

        //put the new one in the right place
        array[index] = element;
    }
```

> 这个双参数的版本，叫做`add(int, E)`，它使用了单参数的版本，称为`add(E)`，它将新的元素放在最后。然后它将其他元素向右移动，并将新元素放在正确的位置

> 现在，如果`add(E)`是常数时间，那么`add(int, E)`呢？调用`add(E)`后，它遍历数组的一部分并移动元素。这个循环是线性的，除了在列表末尾添加的特殊情况中。**因此， `add(int, E)`是线性的**。<br>



###### 2.划分MyArrayList的方法

> 对于许多方法，我们不能通过测试代码来确定增长级别<br>



**常数级别**

> `get`中的每个东西都是常数时间的。所以`get`是常数时间

```java
public E get(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException();
    }
    return array[index];
}
```

> `set`中的一切，包括`get`的调用都是常数时间，所以`set`也是常数时间
>
> set它不会显式检查数组的边界；它利用`get`，如果索引无效则引发异常

```java
public E set(int index, E element) {
    E old = get(index);
    array[index] = element;
    return old;
}
```

<br>

**线性级别**

>每次在循环中，`indexOf`调用`equals`，所以我们首先要划分`equals`

```java
public int indexOf(Object target) {
    for (int i = 0; i<size; i++) {
        if (equals(target, array[i])) {
            return i;
        }
    }
    return -1;
}
```

> **equals方法**

```java
private boolean equals(Object target, Object element) {
    if (target == null) {
        return element == null;
    } else {
        return target.equals(element);
    }
}
```

> 如果我们幸运，我们可能会立即找到目标对象，并在测试一个元素后返回。如果我们不幸，我们可能需要测试所有的元素。平均来说，我们预计测试一半的元素，所以这种方法被认为是**线性**的（除了在不太可能的情况下，我们知道目标元素在数组的开头）<br>



###### 3.问题规模

**RemoveAll**

```java
public boolean removeAll(Collection<?> collection) {
    boolean flag = true;
    for (Object obj: collection) {
        flag &= remove(obj);
    }
    return flag;
}
```

> 每次循环中，`removeAll`都调用`remove`，这是线性的。所以认为`removeAll`是二次的很诱人。但事实并非如此。

>在这种方法中，循环对于每个`collection`中的元素运行一次。如果`collection`包含`m`个元素，并且我们从包含`n`个元素的列表中删除，则此方法是`O(nm)`的。如果`collection`的大小可以认为是常数，`removeAll`相对于`n`是线性的。但是，如果集合的大小与`n`成正比，`removeAll`则是平方的。例如，如果`collection`总是包含`100`个或更少的元素， `removeAll`则是线性的。但是，如果`collection`通常包含的列表中的 1% 元素，`removeAll`则是平方的。

> **当我们谈论问题规模时，我们必须小心我们正在讨论哪个大小**。这个例子演示了算法分析的陷阱：对循环计数的诱人捷径。如果有一个循环，算法往往是 线性的。如果有两个循环（一个嵌套在另一个内），则该算法通常是平方的。不过要小心！**你必须考虑每个循环运行多少次**。如果所有循环的迭代次数与`n`成正比，你可以仅仅对循环进行计数之后离开。但是，如在这个例子中，**迭代次数并不总是与`n`成正比**，所以你必须考虑更多。<br>





### 第四章 LinkedList

###### 1.链式数据结构

> 在链表中，每个节点包含列表中下一个节点的引用. 其它的链表结构包括树和图，其中节点可以包含多个其它节点的引用。

<br>

简单的节点Demo

```java
public class ListNode {

    public Object node;
    public ListNode next;

    //init Node
    public ListNode() {
        this.node = null;
        this.next = null;
    }

    //usually use as the head
    public ListNode(Object node) {
        this.node = node;
        this.next = null;
    }


    //normal node
    public ListNode(Object node, ListNode next) {
        this.node = node;
        this.next = next;
    }


    @Override
    public String toString() {
        return "ListNode{" + "node=" + node + ", next=" + next + '}';
    }
    
}
```

> `ListNode`提供了几个构造函数，可以让你为`data`和`next`提供值，或将它们初始化为默认值，`null`.

<br>

创造一个链表

```java
ListNode node1 = new ListNode(1);
ListNode node2 = new ListNode(2);
ListNode node3 = new ListNode(3);

node1.next = node2;
node2.next = node3;
node3.next = null;

ListNode node0 = new ListNode(0,node1);
```

![](https://wizardforcel.gitbooks.io/think-dast/content/img/3-1.jpg)

<br>

###### 2.LinkedList初始化结构

构造内嵌函数Node

```java
//内嵌函数（Nested Function set the Node）
    private class Node {
        public E data;
        public Node next;

        public Node() {
            this.data = null;
            this.next = null;
        }

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

    }
```

<br>

初始化结构

```java
public class MyLinkedList<E> implements List<E> {

    private int size;            // keeps track of the number of elements
    private Node head;           // reference to the first node

    public MyLinkedList() {
        head = null;
        size = 0;
    }
}
```

>存储元素数量不是必需的，并且一般来说，保留冗余信息是有风险的，因为如果没有正确更新，就有机会产生错误。它还需要一点点额外的空间。

> 但是如果我们显式存储`size`，我们可以实现常数时间的`size`方法；否则，我们必须遍历列表并对元素进行计数，这需要线性时间。

> 因为我们显式存储`size`明确地存储，每次添加或删除一个元素时，我们都要更新它，这样一来，这些方法就会减慢，但是它不会改变它们的增长级别，所以很值得。

<br>

###### 3.LinkedList方法划分

Add方法

```java
    @Override
    public boolean add( E element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node node = head;
            for (; node.next != null; node = node.next) {}      //loop until the next one
            node.next = new Node(element);
        }
        size++;
        return true;
    }
```

<br>

indexOf方法

```java
	@Override
    public int indexOf(Object target) {
        Node node = head;
        for (int i=0; i<size; i++) {
            if ( equals(target,node.data) ) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }
```

>那么这种方法的增长级别是什么？
>
>- 每次在循环中，我们调用了`equals`，这是一个常数时间（它可能取决于`target`或`data`大小，但不取决于列表的大小）。循环中的其他操作也是常数时间。
>- 循环可能运行`n`次，因为在更糟的情况下，我们可能必须遍历整个列表。
>
>所以这个方法的运行时间与列表的长度成正比(换言之，是线性的)。

<br>

Add双参方法

```java
	@Override
    public void add(int index, E element) {
        if (head == null) {
            Node node = new Node(element);
        } else {
            Node node = getNode(index-1);
            node.next = new Node(element,node.next);
        }
        size++;
    }
```

辅助方法

```java
	private Node getNode(int index){
        if (index<0 || index>=size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }
```

> - `getNode`类似`indexOf`，出于同样的原因也是线性的。
> - 在`add`中，`getNode`前后的一切都是常数时间。
>
> 所以放在一起，`add`是线性的。

<br>

Remove方法

```java
 @Override
    public E remove(int index) {
        E element = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index-1);
            node.next = node.next.next;
        }
        size--;
        return element;
    }
```

> `remove`使用了`get`查找和存储`index`处的元素。然后它删除包含它的`Node`。
>
> 如果`index==0`，我们再次处理这个特殊情况。否则我们找到节点`index-1`(这个真的是心机，得防止它漏过)并进行修改，来跳过`node.next`并直接链接到`node.next.next`。这有效地从列表中删除`node.next`，它可以被垃圾回收。
>
> 当人们看到两个线性操作时，他们有时会认为结果是平方的，但是只有一个操作嵌套在另一个操作中才适用。如果你在一个操作之后调用另一个，运行时间会相加。如果它们都是`O(n)`的，则总和也是`O(n)`的。
>
> 所以Remove()是线性的

<br>



###### 4.ArrayList和LinkedList的对比

|                           | **MyArrayList** | **MyLinkedList** |
| ------------------------- | :-------------: | :--------------: |
| `add`（末尾）             |        1        |        n         |
| `add`（开头）             | n(剩余的都往后) |        1         |
| `add`（一般）             |        n        |        n         |
| `get` / `set`             |        1        |        n         |
| `indexOf` / `lastIndexOf` |        n        |        n         |
| `isEmpty` / `size`        |        1        |        1         |
| `remove`（末尾）          |        1        |        n         |
| `remove`（开头）          |        n        |        1         |
| `remove`（一般）          |        n        |        n         |

- `MyArrayList`的优势操作是，**插入末尾，移除末尾，获取和设置**。
- `MyLinkedList`的优势操作是，插入开头，以及移动开头。（**链式结构头容易获取**）

要用哪种看具体的需求<br>





### 第五章 性能分析

###### 1.性能分析

对于下一个练习，我提供了一个`Profiler`类，它包含代码，使用一系列**问题规模运行方法**，**测量运行时间**和**绘制结果**。

> 你将使用`Profiler`，为 Java 的实现`ArrayList`和`LinkedList`，划分`add`方法的性能。

- `Profiler.java`包含上述`Profiler`类的实现。你会使用这个类，但你不必知道它如何工作。但可以随时阅读源码。
- `ProfileListAdd.java`包含此练习的起始代码，包括上面的示例，它测量了`ArrayList.add`。你将修改此文件来测量其他一些方法。

<br>



###### 2.ArrayList的尾部添加

示例，展示如何使用分析器

```java
public static void profileArrayListAddEnd() {

        Profiler.Timeable timeable = new Profiler.Timeable() {
            List<String> list;

            @Override
            public void setup(int n) {
                list = new ArrayList<String>();//执行在启动计时之前所需的任何工作
            }

            @Override
            public void timeMe(int n) {  //执行我们试图测量的任何操作
                for (int i=0; i<n; i++) {
                    list.add("anything");
                }
            }
        };

        Profiler profiler = new Profiler("ArrayListAddEnd",timeable);
        int startN = 4000;
        int endMils = 1000;
        //绘制图像(好像MATLAB)
        XYSeries series = profiler.timingLoop(startN,endMils);
        profiler.plotResults(series);
}
```

**此方法测量在`ArrayList`上运行`add`所需的时间，它向末尾添加新元素**。

> 为了使用`Profiler`，我们需要创建一个`Timeable`，它提供两个方法：`setup`和`timeMe`。

- `setup`方法执行在**启动计时之前所需的任何工作**；这里它会创建一个空列表。
- 然后`timeMe`**执行我们试图测量的任何操作**；这里它将`n`个元素添加到列表中。

> `Profiler`提供了`timingLoop`，它使用存储为实例变量的`Timeable`。它多次调用`Timeable`对象上的`timeMe`方法，使用一系列的`n`值。`timingLoop`接受两个参数：

- `startN`是`n`的值，计时循环应该从它开始。
- `endMillis`是以毫秒为单位的阈值。随着 `timingLoop`增加问题规模，运行时间增加；当运行时间超过此阈值时，`timingLoop`停止。

**当你运行实验时，你可能需要调整这些参数。如果`startN`太低，运行时间可能太短，无法准确测量。如果`endMillis`太低，你可能无法获得足够的数据，来查看问题规模和运行时间之间的明确关系。**



```java
4000, 3
8000, 0
16000, 1
32000, 2
64000, 3
128000, 6
256000, 18
512000, 30
1024000, 88
2048000, 185
4096000, 242
8192000, 544
16384000, 1325 （超过预定时间）
```

**第一列是问题规模，`n`；第二列是以毫秒为单位的运行时间**。前几个测量非常嘈杂；最好将`startN`设置在`64000`左右。（每次得出的结果不同，可具体分析，最后得出一张图，如图所示）

![](https://wizardforcel.gitbooks.io/think-dast/content/img/4-1.jpg)

**结果分析：**

> 基于我们对`ArrayList`工作方式的理解，我们期望，在添加元素到最后时，`add`方法需要常数时间。所以添加`n`个元素的**总时间应该是线性**的。
>
> 为了测试这个理论，我们可以绘制总运行时间和问题规模，我们应该看到一条直线，至少对于大到足以准确测量的问题规模。在数学上，我们可以为这条直线编写一个函数：

```java
runtime = a + b * n
```

其中`a`是线的截距，`b`是斜率。

另一方面，如果`add`是线性的，则`n`次添加的总时间将是平方。如果我们绘制运行时间与问题规模，我们预计会看到抛物线。或者在数学上，像：

```java
runtime = a + b * n + c * n^2
```

对于`n`的较大值，**最大指数项是最重要的**，因此：

```
runtime ≈ c * n^k
```

其中`≈`意思是“大致相等”。现在，如果我们对这个方程的两边取对数：

```
log(runtime) ≈ log(c) + k * log(n)
```

这个方程式意味着，如果我们在重对数合度上绘制运行时间与`n`，我们预计看到一条直线，截距为`log(c)`，斜率为`k`。我们**不太在意截距，但斜率表示增长级别**：如果`k = 1`，算法是线性的；如果`k = 2`，则为平方的。

看上一节中的数字，你可以**通过眼睛来估计斜率**。但是当你调用`plotResults`它时，会计算数据的最小二乘拟合并打印估计的斜率。在这个例子中：

```
Estimated slope = 1.06194352346708(这个值是不一定的)
```

它接近`1`；并且这表明`n`次添加的总时间是线性的，所以每个添加是常数时间，像预期的那样。

其中重要的一点：如果你在图形看到这样的直线，这并不意味着该算法是线性的。如果对于任何指数`k`，运行时间与`n ** k`成正比，我们预计看到斜率为`k`的直线。**如果斜率接近`1`，则表明算法是线性的。如果接近`2`，它可能是平方的。**<br>



###### 3.ArrayList的首部添加

**预估：**

我们每次在ArrayList的首部进行添加，需要移动n个元素，执行n次，所以我们预估时间复杂度为O(n^2).斜率接近于2.

```java
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
```

这个方法几乎和`profileArrayListAddEnd`相同。**唯一的区别在于`timeMe`，它使用`add`的双参数版本，将新元素置于下标`0`处**。同样，我们增加了`endMillis`，来获取一个额外的数据点。

以下是时间结果（左侧是问题规模，右侧是运行时间，单位为毫秒）：

```java
4000, 14
8000, 35
16000, 150
32000, 604
64000, 2518
128000, 11555
```

**以下是运行时间和问题规模曲线:**

![](https://wizardforcel.gitbooks.io/think-dast/content/img/5-1.jpg)

**分析结果：**

请记住，**该图上的直线并不意味着该算法是线性的**。相反，如果对于任何指数`k`，运行时间与`n ^ k`成正比，我们预计会看到斜率为`k`的直线。在这种情况下，我们预计，`n`次添加的总时间与`n ^ 2`成正比，所以我们预计会有一条斜率为`2`的直线。实际上，估计的斜率是`1.992`，非常接近。恐怕假数据才能做得这么好。(**还是看斜率，与预期相符合**)<br>



###### 4.LInkedList的首部添加

**预估：**

LinkedList首部添加不需要移动元素，操作时间复杂度为O(1),操作n次，预估时间复杂度为O(n^2)，预估他是线性的.

```java
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
```

**测试结果（有一点嘈杂这个结果）：**

```java
128000, 16
256000, 19
512000, 28
1024000, 77
2048000, 330
4096000, 892
8192000, 1047
16384000, 4755
```

**以下是运行时间和问题规模曲线:**

![](<https://wizardforcel.gitbooks.io/think-dast/content/img/5-2.jpg>)

**结果分析：**

并不是一条很直的线，斜率也不是正好是`1`，最小二乘拟合的斜率是`1.23`。但是结果表示，`n`次添加的总时间至少近似于`O(n)`，所以**每次添加都是常数时间**。<br>



###### 5.LinkedList的尾部添加

**预估：**

LinkedList的尾部添加，需要逐个便利，操作是线性的，为O(n)，操作n次,时间复杂度为O(n^2).

```java
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
    int startN = 64000;
    int endMils = 1000;
    runProfiler("LinkedListAddEnd",timeable,startN,endMils);
}
```

**测试结果：**

```java
64000, 9
128000, 9
256000, 21
512000, 24
1024000, 78
2048000, 235
4096000, 851
8192000, 950
16384000, 6160
```

**以下是运行时间和问题规模曲线：**

![](<https://wizardforcel.gitbooks.io/think-dast/content/img/5-3.jpg>)

**结果分析：**

同样，测量值很嘈杂，线不完全是直的，但估计的斜率为`1.19`，接近于在头部添加元素，而并不非常接近`2`，这是我们根据分析的预期。事实上，它接近`1`，这表明在尾部添加元素是常数元素。(思考一个问题，它的问题规模为什么这么大，大概有0.2的偏差，经验证，问题规模在400w的时候会有一个明显的下降)<br>