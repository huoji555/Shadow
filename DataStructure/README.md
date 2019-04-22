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

- ###### `MyArrayList`的优势操作是，**插入末尾，移除末尾，获取和设置**。
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



### 第六章 双链表

###### 1.双链表数据结构

> `List`和`Deque`接口的双链表实现。[...] 所有的操作都能像双向列表那样执行。索引该列表中的操作将从头或者尾遍历列表，使用更接近指定索引的那个。

- 每个节点包含下一个节点的链接和上一个节点的链接。
- `LinkedList`对象包含指向列表的第一个和最后一个元素的链接。

所以我们**可以从列表的任意一端开始，并以任意方向遍历它**。因此，我们可以在常数时间内，在列表的头部和末尾添加和删除元素！<br>



###### 2.ArrayList,LinkedList和双链表的对比

|                           | **MyArrayList** | **MyLinkedList** | 双链表 |
| ------------------------- | :-------------: | :--------------: | :----: |
| `add`（末尾）             |        1        |        n         |   1    |
| `add`（开头）             | n(剩余的都往后) |        1         |   1    |
| `add`（一般）             |        n        |        n         |   n    |
| `get` / `set`             |        1        |        n         |   n    |
| `indexOf` / `lastIndexOf` |        n        |        n         |   n    |
| `isEmpty` / `size`        |        1        |        1         |   1    |
| `remove`（末尾）          |        1        |        n         |   1    |
| `remove`（开头）          |        n        |        1         |   1    |
| `remove`（一般）          |        n        |        n         |   n    |

<br>



###### 3.结构的选择

对于头部插入和删除，双链表的实现优于`ArrayList`。对于尾部插入和删除，都是一样好。所以，`ArrayList`唯一优势是`get`和`set`，链表中它需要线性时间，即使是双链表(**根据实际需要解决的问题来看**)。

如果你知道，你的应用程序的运行时间取决于`get`和`set`元素的所需时间，则`ArrayList`可能是更好的选择。如果运行时间取决于在开头或者末尾附加添加和删除元素，`LinkedList`可能会更好。

但请记住，这些建议是基于大型问题的增长级别。还有其他因素要考虑：

- 如果这些操作不占用你应用的大部分运行时间 - 也就是说，如果你的应用程序花费大部分时间来执行其他操作 - 那么你对`List`实现的选择并不重要。
- 如果你正在处理的列表不是很大，你可能无法获得期望的性能。对于小型问题，二次算法可能比线性算法更快，或者线性可能比常数时间更快。而对于小型问题，差异可能并不重要。
- 另外，别忘了空间。到目前为止，我们专注于运行时间，但不同的实现需要不同的空间。在`ArrayList`中，这些元素并排存储在单个内存块中，所以浪费的空间很少，并且计算机硬件通常在连续的块上更快。在链表中，每个元素需要一个节点，带有一个或两个链接。链接占用空间（有时甚至超过数据！），并且节点分散在内存中，硬件效率可能不高。

总而言之，算法分析为数据结构的选择提供了一些指南，但只有：

- 你的应用的运行时间很重要，
- 你的应用的运行时间取决于你选择的数据结构，以及，
- 问题的规模足够大，增长级别实际上预测了哪个数据结构更好。

作为一名软件工程师，在较长的职业生涯中，你几乎不必考虑这种情况。<br>





### 第七章 树的遍历

> 本章将介绍一个 Web 搜索引擎，我们将在本书其余部分开发它。我描述了搜索引擎的元素，并介绍了第一个应用程序，一个从**维基百科下载和解析页面的 Web 爬行器**。本章还介绍了**深度优先搜索的递归实现**，以及迭代实现，它使用 Java `Deque`实现“后入先出”的栈。

<br>



###### 1.搜索引擎

网络搜索引擎，像谷歌搜索或 Bing，接受一组“检索项”，并返回一个网页列表，它们和这些项相关。

**搜索引擎的基本组成部分是**：

- **抓取**：我们需要一个程序，可以下载网页，解析它，并提取文本和任何其他页面的链接。 
- **索引**：我们需要一个数据结构，可以查找一个检索项，并找到包含它的页面。
- **检索：**我们需要一种方法，从索引中收集结果，并识别与检索项最相关的页面。

我们以爬虫开始。**爬虫的目标是查找和下载一组网页**。对于像 Google 和 Bing 这样的搜索引擎，目标是查找所有网页，但爬虫通常仅限于较小的域。在我们的例子中，我们只会读取维基百科的页面。

作为第一步，我们将构建一个读取维基百科页面的爬虫，找到第一个链接，并跟着链接来到另一个页面，然后重复。我们将使用这个爬虫来测试“到达哲学”的猜想，它是：

> 点击维基百科文章正文中的第一个小写的链接，然后对后续文章重复这个过程，通常最终会到达“哲学”的文章。

在几个章节之内，我们将处理索引器，然后我们将到达检索器.<br>



###### 2.解析HTML

下载网页时，内容使用HTML编写。

```html
<!DOCTYPE html>
<html>
  <head>
    <title>This is a title</title>
  </head>
  <body>
    <p>Hello world!</p>
  </body>
</html>
```

当我们的爬虫下载页面时，它需要解析 HTML，以便提取文本并找到链接。为此，我们将使用`jsoup`，**它是一个下载和解析 HTML 的开源 Java 库**。

**解析 HTML 的结果是文档对象模型（DOM）树**，其中包含文档的元素，包括文本和标签。树是由节点组成的链接数据结构；节点表示文本，标签和其他文档元素.

节点之间的关系由文档的结构决定。在上面的例子中，第一个节点称为根，是`<html>`标签，它包含指向所包含两个节点的链接， `<head>`和`<body>`；这些节点是根节点的子节点。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/6-1.jpg)



###### 3.使用jsoup

`jsoup`非常易于下载，和解析 Web 页面，以及访问 DOM 树。这里是一个例子：

```java
public static void main(String[] args) throws Exception{
    String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

    //下载并解析元素
    Connection connection = Jsoup.connect(url);
    Document doc = connection.get();

    //选择内容，并解析初其中所有的段落
    Element content = doc.getElementById("mw-content-text");
    Elements paragraphs = content.select("p");

    for (Element xx : paragraphs) {
        System.out.println(xx);
    }

}
```

`Jsoup.connect`接受`String`形式的`url`，并连接 Web 服务器。get`方法下载 HTML，解析，并返回`Document`对象，他表示 DOM。

`Document`提供了导航树和选择节点的方法,这里主要展示了两种：

- `getElementById`获取某个标签下的所有内容
- `select`可对获取的内容进行遍历，选择初想要的元素的集合(支持css选择器)

`Node`表示 DOM 树中的一个节点；有几个扩展`Node`的子类，其中包括 `Element`，`TextNode`，`DataNode`，和`Comment`。`Elements`是`Element`对象的`Collection`

![](https://wizardforcel.gitbooks.io/think-dast/content/img/6-3.jpg)

<br>



###### 4.遍历HTML

为了使你变得更轻松，这里提供了一个`WikiNodeIterable`类，可以让你遍历 DOM 树中的节点。

```java
Elements paragraphs = content.select("p");
Element firstPara = paragraphs.get(0);

Iterable<Node> iter = new WikiNodeIterable(firstPara);
for (Node node: iter) {
    if (node instanceof TextNode) {
        System.out.print(node);
    }
}
```

这个例子紧接着上一个例子。它选择`paragraphs`中的第一个段落，然后创建一个`WikiNodeIterable`，它实现`Iterable<Node>`。`WikiNodeIterable`执行“**深度优先搜索**”，**它按照它们将出现在页面上的顺序产生节点**(这个是创造它的目的)。

在这个例子中，仅当`Node`是`TextNode`时，我们打印它，并忽略其他类型的`Node`，特别是代表标签的`Element`对象。结果是没有任何标记的 HTML 段落的纯文本。<br>



###### 5.深度优先搜索

有几种方式可以合理地遍历一个树，每个都有不同的应用。我们从**深度优先搜索（DFS**）开始。DFS 从树的根节点开始，并选择第一个子节点。如果子节点有子节点，则再次选择第一个子节点。当它到达没有子节点的节点时，它回溯，沿树向上移动到父节点，在那里它选择下一个子节点，如果有的话；否则它会再次回溯。当它探索了根节点的最后一个子节点，就完成了(**跟之前思维导图遍历节点一样**)。

有两种常用的方式来实现 DFS，递归和迭代。

递归（代码简洁）：

```java
 public static void recursiveDFS (Node node) {
        if (node instanceof TextNode) {
            System.out.println(node);
        }
        for (Node child : node.childNodes()) {
            recursiveDFS(child);
        }
    }
```

这个方法对树中的每一个`Node`调用，从根节点开始。如果`Node`是一个`TextNode`，它打印其内容。如果`Node`有任何子节点，它会按顺序在每一个子节点上调用`recursiveDFS`（**前序遍历**）。

通过进行递归调用，`recursiveDFS`使用**调用栈来跟踪子节点并以正确的顺序处理它们**。作为替代，我们可以使用栈数据结构自己跟踪节点；如果我们这样做，我们可以**避免递归并迭代遍历树**（非递归的好处）。<br>



###### 6.Java中的栈

在我解释 DFS 的迭代版本之前，我将解释栈数据结构。我们将从栈的一般概念开始，我将使用小写`s`指代“栈”。然后我们将讨论两个 Java`接口`，它们定义了栈的方法：`Stack`和`Deque`。

栈是与列表类似的数据结构：**它是维护元素顺序的集合**。栈和列表之间的主要区别是栈**提供的方法较少**。在通常的惯例中，它提供：

-  `push`：它将一个元素添加到栈顶。
-  `pop`：它从栈中删除并返回最顶部的元素。
-  `peek`：它返回最顶部的元素而不修改栈。 
- `isEmpty`：表示栈是否为空。

 因为`pop`总是返回最顶部的元素，栈也称为 LIFO，代表“后入先出”。栈的替代品是“队列”，它返回的元素顺序和添加顺序相同；即“先入先出（FIFO）。

**为什么栈和队列是有用的**，可能不是很明显：它们不提供任何列表没有的功能；实际上它们提供的功能更少。那么为什么不使用列表的一切？有两个原因：

- 如果你将自己限制于一小部分方法 - 也就是小型 API - **你的代码将更加易读，更不容易出错**。例如，如果使用列表来表示栈，则可能会以错误的顺序删除元素。使用栈 API，这种错误在字面上是不可能的。避免错误的最佳方法是使它们不可能(**小型API可以给到很多限制，大型的兼容性强**)。
- 如果一个数据结构提供了小型 API，那么它**更容易实现**。例如，实现栈的简单方法是单链表。当我们压入一个元素时，我们将它添加到列表的开头；当我们弹出一个元素时，我们在开头删除它。对于链表，在开头添加和删除是常数时间的操作，因此这个实现是高效的。相反，大型 API 更难实现高效。

为了**在 Java 中实现栈**，你有三个选项：

- 继续使用`ArrayList`或`LinkedList`。如果使用`ArrayList`，请务必从最后添加和删除，这是一个常数时间的操作。并且小心不要在错误的地方添加元素，或以错误的顺序删除它们。
- Java 提供了一个`Stack`类，它提供了一组标准的栈方法。但是这个类是 Java 的一个旧部分：它与 Java 集合框架不兼容，后者之后才出现。
- 最**好的选择可能是使用`Deque`接口的一个实现**，如`ArrayDeque`。

`Deque`代表“**双向队列**”；在 Java 中， `Deque`接口提供`push`，`pop`，`peek`和`isEmpty`，因此你可以将`Deque`用作栈。<br>



###### 7.迭代式DFS

这里是 DFS 的迭代版本，它使用`ArrayDeque`来表示`Node`对象的栈。

```java
public static void iteratorDFS(Node root) {
    Deque<Node> stack = new ArrayDeque<Node>();
    stack.push(root);

    while (!stack.isEmpty()) {
        Node node = stack.pop();
        if (node instanceof TextNode) {
            System.out.println(node);
        }

        // 这里必须反转，因为开始压栈时，顺序会反，所有需要反转，确保顺序正确
        List<Node> list = new ArrayList<Node>(node.childNodes());
        Collections.reverse(list);
        for (Node child : list) {
            stack.push(child);
        }

    }
}
```

参数`root`是我们想要遍历的树的根节点，所以我们首先创建栈并将根节点压入它。

循环持续到栈为空。每次迭代，它会从栈中弹出`Node`。如果它得到`TextNode`，它打印内容。然后它把子节点们压栈。**为了以正确的顺序处理子节点，我们必须以相反的顺序将它们压栈;** 我们通过将子节点复制成一个`ArrayList`，原地反转元素，然后遍历反转的`ArrayList`。

DFS 的迭代版本的一个优点是，**更容易实现为 Java `Iterator`**；你会在下一章看到如何实现。<br>



### 第八章 到达哲学

> 本章的目标是开发一个Web爬虫，同时验证之前提到的 `到达哲学` 



###### 1.起步

首先介绍本章中帮你起步的代码：

- `WikiNodeExample.java`包含前一章的代码，展示了 DOM 树中深度优先搜索（DFS）的递归和迭代实现。
- `WikiNodeIterable.java`包含`Iterable`类，用于遍历 DOM 树。我将在下一节中解释这段代码。
- `WikiFetcher.java`包含一个工具类，使用`jsoup`从维基百科下载页面。为了帮助你遵守维基百科的服务条款，此类限制了你下载页面的速度；如果你每秒请求许多页，在下载下一页之前会休眠一段时间。
- `WikiPhilosophy.java`包含你为此练习编写的代码的大纲。我们将在下面进行说明。

<br>



###### 2.可迭代对象和迭代器

在前一章中，我展示了迭代式深度优先搜索（DFS），并且认为与递归版本相比， **迭代版本的优点**在于， **它更容易包装在`Iterator`对象中（Iterable是一种数据结构，Iterator是用来迭代的迭代器）**。在本节中，我们将看到如何实现它。

**外层的类`WikiNodeIterable`实现`Iterable<Node>`接口，所以我们可以在一个`for`循环中使用它：**

```java
Node root = ...
Iterable<Node> iter = new WikiNodeIterable(root);
for (Node node: iter) {
    visit(node);
}
```

其中 root为树的根节点，visit() 是到Node节点时，你想做的任意的事.

`WikiNodeIterable`的实现遵循以下惯例：

- 构造函数接受并存储根`Node`的引用。
- `iterator`方法创建一个返回一个`Iterator`对象。

下边是它的样子

```java
public class WikiNodeIterable implements Iterable<Node> {
    private  Node root;
    
    // 从给定的节点开始创造一个迭代器
    public WikiNodeIterable(Node root) {
        this.root = root;
    }

    @Override
    public Iterator<Node> iterator() {
        return new WikiNodeIterator(root);
    }

}
```

内部类  `WikiNodeIterator`，**执行所有实际工作**

```java
//实现Iterator的内部类
private class WikiNodeIterator implements Iterator<Node> {

    // 这个堆栈跟踪等待访问的节点
    Deque<Node>  stack;

    // 使用堆栈上的根节点初始化Iterator。
    public WikiNodeIterator(Node node) {
        stack = new ArrayDeque<Node>();
        stack.push(node);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Node next() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException();
        }
        // 出栈
        Node node = stack.pop();
        // 反转后将child节点放入堆栈中
        List<Node> nodes = new ArrayList<Node>(node.childNodes());
        Collections.reverse(nodes);
        for (Node child : nodes) {
            stack.push(child);
        }
        return node;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
```

该代码与 DFS 的迭代版本几乎相同(**DFS是写在一块的**)，但现在分为三个方法：

- 构造函数初始化栈（使用一个`ArrayDeque`实现）并将根节点压入这个栈。
- `isEmpty`检查栈是否为空。
- `next`从`Node`栈中弹出下一个节点，按相反的顺序压入子节点，并返回弹出的`Node`。如果有人在空`Iterator`上调用`next`，则会抛出异常。

可能不明显的是，值得使用两个类和五个方法，来重写一个完美的方法。但是现在我们已经完成了，在需要`Iterable`的任何地方，我们可以使用`WikiNodeIterable`，这使得它的语法整洁，易于将迭代逻辑（DFS）与我们对节点的处理分开。<br>



###### 3.`WikiFetcher`

编写 Web 爬虫时，很容易下载太多页面，这**可能会违反你要下载的服务器的服务条款**。为了帮助你避免这种情况，我提供了一个`WikiFetcher`类，它可以做两件事情:

- 它封装了我们在上一章中介绍的代码，用于从维基百科下载页面，**解析 HTML 以及选择内容文本**。
- 它测量请求之间的时间，如果我们在请求之间没有足够的时间，它将休眠直到经过了合理的间隔。默认情况下，间隔为`1`秒（**防止过度请求**）。

这里是`WikiFetcher`的定义：

```java
public class WikiFetcher {

    private long lastRequestTime = -1;
    private long minIterval = 1000;


    /**
     * @Author Ragty
     * @Description  找到并解析数据
     * @Date 10:15 2019/4/16
     **/
    public Elements fetchWikiPedia(String url) throws IOException {
        sleepIfNeed();
        Connection connection = Jsoup.connect(url);
        Document doc = connection.get();
        Element content = doc.getElementById("mw-content-text");
        Elements para = content.select("p");
        return para;
    }


    /**
     * @Author Ragty
     * @Description 通过最小访问时间来限制范围访问频率
     * @Date 10:43 2019/4/16
     **/
    private void sleepIfNeed() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = lastRequestTime + minIterval;
            if (currentTime < lastRequestTime) {
                try {
                    Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException e) {
                    System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }

}
```

新的代码是`sleepIfNeeded`，它检查自上次请求以来的时间，如果经过的时间小于`minInterval`（毫秒），则休眠。<br>



###### 4.练习

在`WikiPhilosophy.java`中，你会发现一个简单的`main`方法，展示了如何使用这些部分。从这个代码开始，你的工作是写一个爬虫：

1. 获取维基百科页面的 URL，下载并分析。
2. 它应该**遍历所得到的 DOM 树来找到第一个 有效的链接**。我会在下面解释“有效”的含义。
3. 如果页面没有链接，或者如果第一个链接是我们已经看到的页面，程序应该指示失败并退出。
4. 如果链接**匹配维基百科页面上的哲学网址**，程序应该提示成功并退出。
5. 否则应该回到步骤`1`。

该程序应该为它访问的 URL 构建`List`，**并在结束时显示结果（无论成功还是失败）**。

`那么我们应该认为什么是“有效的”链接？`

- 这个链接**应该在页面的内容文本中**，而不是侧栏或弹出框。
- 它**不应该是斜体或括号**。
- 你应该**跳过外部链接**，当前页面的链接和红色链接。
- 在某些版本中，如果文本以大写字母开头，则应跳过链接。

如果你有足够的信息来起步，请继续。或者你可能想要阅读这些提示：

- 当你遍历树的时候，你将需要处理的两种`Node`是`TextNode`和`Element`。如果你找到一个`Element`，你可能需要转换它的类型，来访问标签和其他信息。
- 当你**找到包含链接的`Element`时**，**通过向上跟踪父节点链，可以检查是否是斜体**。如果父节点链中有一个`<i>`或`<em>`标签，链接为斜体。
- 为了检查链接是否在括号中，你必须在遍历树时扫描文本，并跟踪开启和闭合括号（理想情况下，你的解决方案应该能够处理嵌套括号（像这样））。<br>





### 第九章 索引器

> 目前，我们构建了一个基本的 Web 爬虫；我们下一步将是索引。在网页搜索的上下文中，**索引是一种数据结构，可以查找检索词并找到该词出现的页面**。此外，我们想知道每个页面上显示检索词的次数，这将有助于确定与该词最相关的页面。
>
> 例如，如果用户提交检索词“Java”和“编程”，我们将查找两个检索词并获得两组页面。带有“Java”的页面将包括 Java 岛屿，咖啡昵称以及编程语言的网页。具有“编程”一词的页面将包括不同编程语言的页面，以及该单词的其他用途。通过选择具有两个检索词的页面，我们希望消除不相关的页面，并找到 Java 编程的页面。现在我们了解索引是什么，它执行什么操作，我们可以设计一个数据结构来表示它。



###### 1.数据结构选取

索引的基本操作是查找；具体来说，我们需要能够查找检索词并找到包含它的所有页面。最简单的实现将是页面的集合。给定一个检索词，我们可以遍历页面的内容，并选择包含检索词的内容。但运行时间与所有页面上的总字数成正比，这太慢了(最简单的思路就这样)。

一个更好的选择是一个映射（`字典`），它是一个数据结构，表示键值对的集合，并提供了一种方法，快速查找键以及相应值。例如，我们将要构建的第一个映射是`TermCounter`，**它将每个检索词映射为页面中出现的次数。键是检索词，值是计数（也称为“频率”）**。

Java 提供了`Map`的调用接口，它指定映射应该提供的方法；最重要的是：

- `get(key)`：此方法查找一个键并返回相应的值。
- `put(key, value)`：该方法向`Map`添加一个新的键值对，或者如果该键已经在映射中，它将替换与`key`关联的值。

除了检索词到计数的映射`TermCounter`之外，我们将定义一个被称为`Index`的类，**它将检索词映射为出现的页面的集合**。而这又引发了下一个问题，即如何表示页面集合。同样，如果我们考虑我们想要执行的操作，它们就指导了我们的决定。

**在这种情况下，我们需要组合两个或多个集合，并找到所有这些集合中显示的页面**（敲黑板，也就是需要找到包含并集的页面）。你可以将此操作看做集合的交集：两个集合的交集是出现在两者中的一组元素。

你可能猜到了，Java 提供了一个`Set`接口，来定义集合应该执行的操作。它实际上并不提供设置交集，但它提供了方法，使我们能够有效地实现交集和其他结合操作。核心的`Set`方法是：

- `add(element)`：该方法将一个元素添加到集合中；如果元素已经在集合中，则它不起作用。
- `contains(element)`：该方法检查给定元素是否在集合中。

现在我们自顶向下设计了我们的数据结构，我们将从内到外实现它们，从`TermCounter`开始。<br><br>



###### 2.`TermCounter`

`TermCounter`是一个类，表示检索词到页面中出现次数的映射。这是类定义的第一部分：

```java
public class TermCounter {
    private Map<String, Integer> map;
    private String label;

    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap<String, Integer>();
    }
}
```

实例变量`map`包含检索词到计数的映射（这里使用HashMap），并且`label`标识检索词的来源文档；

```java
 public void put(String term, int count) {
     map.put(term, count);
 }

 public Integer get(String term) {
     Integer count = map.get(term);
     return count == null ? 0 : count;   //没有就返回0
 }
 
 public void incrementTermCount(String term) {
     put(term, get(term) + 1);
 }
    
```

帮助索引网页的方法：

```java
 public void processElements(Elements paragraphs) {
        for (Node node: paragraphs) {
            processTree(node);
        }
    }

    public void processTree(Node root) {
        for (Node node: new WikiNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    public void processText(String text) {
        // 用空格替换标点符号，转换为小写，并以空格为分隔符
        String[] array = text.replaceAll("\\pP|\\pS"," ")
                            .toLowerCase()
                            .split("\\s+");
        for (String term : array) {
            if (!term.equals("")) {
                incrementTermCount(term);
            }
        }
    }
```

**特别强调，这里还要去除"",否则结果会显示计数**

`测试用例`

```java
String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
WikiFetcher wf = new WikiFetcher();
Elements para = wf.fetchWikiPedia(url);

TermCounter tc = new TermCounter(url);
tc.processElements(para);
tc.printCounts();
```

`部分测试结果(截取底部数据)`

```java
command,2
performance,4
boolean,1
currently,1
compliance,1
response,1
variable,2
arguments,3
Total of counts:4020
```

<br>



###### 3.Index类的实现

这个类的主要目的是实现：**实例变量`index`是每个检索词到一组`TermCounter`对象的映射。每个`TermCounter`表示检索词出现的页面。**

这是它的基础结构：

```java
public class Index {

    private Map<String, Set<TermCounter>> index = 
        new HashMap<String, Set<TermCounter>>();

    public void add(String term, TermCounter tc) {
        Set<TermCounter> set = get(term);

        // if we're seeing a term for the first time, make a new Set
        if (set == null) {
            set = new HashSet<TermCounter>();
            index.put(term, set);
        }
        // otherwise we can modify an existing Set
        set.add(tc);
    }

    public Set<TermCounter> get(String term) {
        return index.get(term);
    }
 }   
```

`add`方法向集合添加新的`TermCounter`，它与检索词关联。当我们索引一个尚未出现的检索词时，我们必须创建一个新的集合。否则我们可以添加一个新的元素到一个现有的集合。在这种情况下，`set.add`修改位于`index`里面的集合，但不会修改`index`本身。  **我们唯一修改`index`的时候是添加一个新的检索词**

这种数据结构比较复杂。回顾一下，`Index`包含`Map`，将每个检索词映射到`TermCounter`对象的`Set`，每个`TermCounter`包含一个`Map`，**将检索词映射到计数**（这是值得一提的部分）。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/8-1.jpg)

`printIndex`方法展示了如何解压缩此数据结构：

```java
public void printIndex() {
        for (String term: index.keySet()) {
            System.out.println(term);

            Set<TermCounter> tcs = get(term);
            for (TermCounter tc: tcs) {
                Integer count = tc.get(term);
                System.out.println(tc.getLabel()+ "   "+count);
            }
        }
    }
```

**外层循环遍历检索词。内层循环迭代`TermCounter`对象。**

`indexPage`统计页面中的检索词:

```java
 public void indexPage(String url, Elements paragraphs) {

        //  生成一个 TermCounter 并统计段落中的检索词
        TermCounter tc = new TermCounter(url);
        tc.processElements(paragraphs);

        // 对于 TermCounter 中的每个检索词，将 TermCounter 添加到索引
        for (String term: tc.keySet()) {
            add(term,tc);
        }

    }
```

`测试`

```java
WikiFetcher wikiFetcher = new WikiFetcher();
Index indexer = new Index();

String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
Elements paragraphs = wikiFetcher.fetchWikiPedia(url);
indexer.indexPage(url,paragraphs);

String url1 = "https://en.wikipedia.org/wiki/Programming_language";
Elements paragraphs1 = wikiFetcher.fetchWikiPedia(url);
indexer.indexPage(url1,paragraphs1);

indexer.printIndex();
```

`部分测试结果`

```java
response
https://en.wikipedia.org/wiki/Java_(programming_language)   1
https://en.wikipedia.org/wiki/Programming_language   1
variable
https://en.wikipedia.org/wiki/Java_(programming_language)   2
https://en.wikipedia.org/wiki/Programming_language   2
arguments
https://en.wikipedia.org/wiki/Java_(programming_language)   3
https://en.wikipedia.org/wiki/Programming_language   3
```





### 第十章 Map接口

> 在接下来的几个练习中，我介绍了`Map`接口的几个实现。其中一个基于哈希表，这可以说是所发明的最神奇的数据结构。另一个是类似的`TreeMap`，不是很神奇，但它有附加功能，它可以按顺序迭代元素。
>
> 我们从一个`Map`开始，它使用键值对的`List`实现。



###### 1.实现MyLinearMap

简单来说，就是继承Map,放入List中,来看下初始结构

```java
public class MyLinearMap<K, V> implements Map<K, V> {

    private List<Entry> entries = new ArrayList<Entry>();
```

`MyLinearMap`对象具有单个实例变量，`entries`，这是一个`Entry`的`ArrayList`对象。每个`Entry`都包含一个键值对。这里是定义：

```java
public class Entry implements Map.Entry<K,V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            this.value = newValue;
            return value;
        }
    }
```

<br>



###### 2.分析MyLinearMap

两个私有方法`FindEntry`用于根据key值，找到对应的实体，`equal`用于比较，下边是实现：

```java
private Entry findEntry(Object target) {
    for (Entry entry: entries) {
        if (equals(target, entry.getKey())) {
            return entry;
        }
    }
    return null;
}

private boolean equals(Object target, Object obj) {
    if (target == null) {
        return obj == null;
    }
    return target.equals(obj);
}
```

`equals`运行时间取决于值的大小，跟数目无关，**是常数时间O(1)**.

`findEntry`运行时间取决于数目，最好的情况下，可以一开始就找到，但是平均下来的话，仍要遍历整个map,是线性时间，**时间复杂度为O(n)**.

大部分的`MyLinearMap`核心方法使用`findEntry`，包括`put`，`get`，和`remove`。这就是他们的样子：

```java
public V put(K key, V value) {
    Entry entry = findEntry(key);
    if (entry == null) {
        entries.add(new Entry(key, value));
        return null;
    } else {
        V oldValue = entry.getValue();
        entry.setValue(value);
        return oldValue;
    }
}

public V get(Object key) {
    Entry entry = findEntry(key);
    if (entry == null) {
        return null;
    }
    return entry.getValue();
}

public V remove(Object key) {
    Entry entry = findEntry(key);
    if (entry == null) {
        return null;
    } else {
        V value = entry.getValue();
        entries.remove(entry);
        return value;
    }
}
```

这三个方法中，调用`findEntry`是线性时间，其余操作都为常数时间，**所以总的时间复杂度为线性时间**。

总而言之，核心方法都是线性的，这就是为什么我们将这个实现称为`MyLinearMap`

**如果我们知道输入的数量很少，这个实现可能会很好（很好的原因就是，数目少了有更大机率查找为常数时间）**，但是我们可以做得更好。**实际上，`Map`所有的核心方法都是常数时间的实现**。当你第一次听到这个消息时，可能似乎觉得不可能。实际上我们所说的是，你可以在常数时间内大海捞针，不管海有多大。这是魔法。

我们不是将条目存储在一个大的`List`中，**而是把它们分解成许多短的列表（这样查找时就为常数时间）**。对于每个键，我们将使用哈希码（在下一节中进行说明）来确定要使用的列表。 使用大量的简短列表比仅仅使用一个更快，但正如我将解释的，它不会改变增长级别；核心功能仍然是线性的。但还有一个技巧：**如果我们增加列表的数量来限制每个列表的条目数，就会得到一个恒定时间的映射**。你会在下一个练习中看到细节，但是首先要了解哈希！