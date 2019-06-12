数据结构思维

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

<br><br>



### 第十一章 哈希

> 在本章中，我定义了一个比`MyLinearMap`更好的`Map`接口实现，`MyBetterMap`，并引入哈希，这使得`MyBetterMap`效率更高。



###### 1.哈希

为了提高`MyLinearMap`的性能，我们将编写一个新的类，它被称为`MyBetterMap`，它包含`MyLinearMap`对象的集合。**它在内嵌的映射之间划分键，因此每个映射中的条目数量更小**，这加快了`findEntry`，以及依赖于它的方法的速度。

类定义的开始：

```java
public class MyBetterMap<K, V> implements Map<K, V> {

    protected List<MyLinearMap<K, V>> maps;

    public MyBetterMap(int k) {
        makeMaps(k);
    }

    // k值关系到hashCode的分配
    protected void makeMaps(int k) {
        maps = new ArrayList<MyLinearMap<K, V>>(k);
        for (int i=0; i<k; i++) {
            maps.add(new MyLinearMap<K, V>());
        }
    }
}
```

实例变量`maps`是一组`MyLinearMap`对象。构造函数接受一个参数`k`，**决定至少最开始，要使用多少个映射**。然后`makeMaps`创建内嵌的映射并将其存储在一个`ArrayList`中。

现在，完成这项工作的关键是，**我们需要一些方法来查看一个键，并决定应该进入哪个映射(首先要放进去，放到哪里用HashCode算法决定)**。当我们`put`一个新的键时，我们选择一个映射；当我们`get`同样的键时，我们必须记住我们把它放在哪里(**重点是要确定如何跟踪**)。

一种可能性是**随机选择一个子映射**，并跟踪我们把每个键放在哪里(**无法跟踪**)。

一个更好的方法是**使用一个哈希函数**，它接受一个`Object`，一个任意的`Object`，并返回一个称为哈希码的整数。重要的是，**如果它不止一次看到相同的`Object`，它总是返回相同的哈希码**。这样，如果我们使用哈希码来存储键，当我们查找时，我们将得到相同的哈希码。

在Java中，每个`Object`都提供了`hashCode`，一种计算哈希函数的方法。**这种方法的实现对于不同的对象是不同的**；我们会很快看到一个例子。

选择到正确的子映射：

```java
/**
* @Author Ragty
* @Description 为一个给定的键选择正确的子映射（同makeMaps的初始化数量有关）
* @Date 10:53 2019/4/23
**/
protected MyLinearMap<K, V> chooseMap(Object key) {
    int index = 0;
    if (key != null) { 
        index = Math.abs(key.hashCode()) % maps.size();
    }
    return maps.get(index);
}
```

如果`key`是`null`，我们选择索引为`0`的子映射。否则，我们使用`hashCode`获取一个整数，调用`Math.abs`来确保它是非负数，然后使用余数运算符`%`，**这保证结果在`0`和`maps.size()-1`之间**。**所以`index`总是一个有效的`maps`索引**。然后`chooseMap`返回为其所选的映射的引用。

我们使用`chooseMap`的`put`和`get`，所以**当我们查询键的时候，我们得到添加时所选的相同映射，我们选择了相同的映射**。至少应该是 - 稍后我会解释为什么这**可能不起作用**。

`put`和`get`实现：

```java
public V put(K key, V value) {
  MyLinearMap<K, V> map = chooseMap(key);
    return map.put(key, value);
}

public V get(Object key) {
    MyLinearMap<K, V> map = chooseMap(key);
    return map.get(key);
}
```

我们使用`chooseMap`来找到正确的子映射，然后在子映射上调用一个方法，我们考虑一下性能.

**如果在`k`个子映射中分配了`n`个条目，则平均每个映射将有`n/k`个条目。当我们查找一个键时，我们必须计算其哈希码，这需要一些时间，然后我们搜索相应的子映射。**

因为`MyBetterMap`中的条目列表，比`MyLinearMap`中的短`k`倍，我们的预期是`ķ`倍的搜索速度。**但运行时间仍然与`n`成正比(n/k)，所以`MyBetterMap`仍然是线性的**。在下一个练习中，你将看到如何解决这个问题。

关于此问题的思考，现在考虑一种比较极端的情况，如果恰好所有数据都集中在一个子映射中，那么效率将跟线性方法一样.<br>



###### 2.哈希如何工作

哈希函数的基本要求是，**每次相同的对象应该产生相同的哈希码**。对于不变的对象，这是比较容易的。对于具有可变状态的对象，我们必须花费更多精力。

作为一个不可变对象的例子，我将定义一个`SillyString`类，它包含一个`String`：

```java
public class SillyString {
    private final String innerString;

    public SillyString(String innerString) {
        this.innerString = innerString;
    }

    public String toString() {
        return innerString;
    }
```

使用它来展示，一个类如何定义它自己的哈希函数：

```java
    @Override
    public boolean equals(Object other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for (int i=0; i<innerString.length(); i++) {
            total += innerString.charAt(i);
        }
        return total;
    }
```

注意`SillyString`重写了`equals`和`hashCode`。这个很重要。为了正常工作，`equals`必须和`hashCode`一致，这意味着如果两个对象被认为是相等的 - 也就是说，`equals`返回`true` - 它们应该有相同的哈希码。**但这个要求只是单向的；如果两个对象具有相同的哈希码，则它们不一定必须相等。**（不可逆）

`hashCode`的原理是，**迭代`String`中的字符并将它们相加**。当你向`int`添加一个字符时，Java 将使用其 Unicode 代码点，将字符转换为整数。

该哈希函数满足要求：**如果两个`SillyString`对象包含相等的内嵌字符串，则它们将获得相同的哈希码。**

这可以正常工作，**但它可能不会产生良好的性能，因为它为许多不同的字符串返回相同的哈希码**。如果两个字符串以任何顺序包含相同的字母，它们将具有相同的哈希码。即使它们不包含相同的字母，它们可能会产生相同的总量，例如`"ac"`和`"bb"`。或者`abc`同`cab`的哈希码也是一致的

**如果许多对象具有相同的哈希码，它们将在同一个子映射中。如果一些子映射比其他映射有更多的条目，那么当我们有`k`个映射时，加速比可能远远小于`k`。**所以哈希函数的**目的之一是统一**；也就是说，以相等的可能性，在这个范围内产生任何值。(**等概率的在子映射中分配条目，这正是之前我们所担心的**)

<br>



###### 3.哈希和可变性

`String`是不可变的，`SillyString`也是不可变的，因为`innerString`定义为`final`。一旦你创建了一个`SillyString`，你不能使`innerString`引用不同的`String`，你不能修改所指向的`String`。因此，它将始终具有相同的哈希码。

我们现在用一个Array，和以上的一样，改变的是之前是String,现在是数组(**可变**)

```java
public class SillyArray {
    private final char[] array;

    public SillyArray(char[] array) {
        this.array = array;
    }

    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public boolean equals(Object other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for (int i=0; i<array.length; i++) {
            total += array[i];
        }
        System.out.println(total);
        return total;
    }
```

`SillyArray`也提供`setChar`，它能够修改修改数组内的字符。

```java
public void setChar(int i, char c) {
    this.array[i] = c;
}
```

现在假设我们创建了一个`SillyArray`，并将其添加到`map`。

```java
SillyArray array1 = new SillyArray("Word1".toCharArray());
map.put(array1, 1);
```

这个数组的哈希码是`461`。现在如果我们修改了数组内容，之后尝试查询它，像这样：

```java
array1.setChar(0, 'C');
Integer value = map.get(array1);
```

修改之后的哈希码是`441`。**使用不同的哈希码，我们就很可能进入了错误的子映射**。这就很糟糕了。

**一般来说，使用可变对象作为散列数据结构中的键是很危险的**，这包括`MyBetterMap`和`HashMap`。如果你可以**保证映射中的键不被修改，或者任何更改都不会影响哈希码**，那么这可能是正确的。但是避免这样做可能是一个好主意。（个人认为还是直接避免这种不安全的操作比较好）

<br>

<br>



### 第十二章 HashMap

上一章中，我们写了一个使用哈希的`Map`接口的实现。我们期望这个版本更快，**因为它搜索的列表较短，但增长顺序仍然是线性的（k太小，而且是固定的）**。

如果存在`n`个条目和`k`个子映射，则子映射的大小平均为`n/k`，这仍然与`n`成正比。但是，如果我们**与`n`一起增加`k`，我们可以限制`n/k`的大小**。

例如，**假设每次`n`超过`k`的时候，我们都使`k`加倍；在这种情况下，每个映射的条目的平均数量将小于`1`，**并且几乎总是小于`10`，只要**散列函数能够很好地展开键**。

如果**每个子映射的条目数是不变的，我们可以在常数时间内搜索一个子映射**。并且计算散列函数通常是常数时间（它可能取决于键的大小，但不取决于键的数量）。这使得`Map`的核心方法， `put`和`get`时间不变。<br>



###### 1.MyHashMap

在`MyHashMap.java`中，我提供了哈希表的大纲，它会按需增长。这里是定义的起始：

```java
public class MyHashMap<K,V> extends MyBetterMap<K,V> implements Map<K,V> {

    // 常数FACTOR（称为负载因子）确定每个子映射的平均最大条目数
    protected static final double FACTOR = 1.0;
    
    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key,value);

        if (size()> maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }
```

`MyHashMap`扩展了`MyBetterMap`，所以它继承了那里定义的方法。**它覆盖的唯一方法是`put`，**它调用了超类中的`put` -- 也就是说，它调用了`MyBetterMap`中的`put`版本 -- **然后它检查它是否必须`rehash`。**调用`size`返回总数量`n`。调用`maps.size`返回内嵌映射的数量`k`。

**常数`FACTOR`（称为负载因子）确定每个子映射的平均最大条目数。如果`n > k * FACTOR`，这意味着`n/k > FACTOR`，意味着每个子映射的条目数超过阈值，所以我们调用`rehash`。**

下面是给出的`rehash`方法（**收集表中的条目，调整表的大小，然后重新放入条目**）：

```java
    protected void rehash() {
        // 扩容
        List<MyLinearMap<K,V>> oldMaps = maps;
        makeMaps(maps.size()*2);

        // 将旧的map放进去
        for (MyLinearMap<K,V> map: oldMaps) {
            for (Map.Entry<K, V> entry : map.getEntries()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
```

<br>



###### 2.分析MyHashMap

如果**最大子映射中的条目数**(FACTOR)**与`n/k`成正比**，并且`k`与`n`成正比，那么多个核心方法就是常数时间的：

```java
    public boolean containsKey（Object target）{ 
        MyLinearMap <K，V> map = chooseMap（target）; 
        return map.containsKey（target）; 
    } 

    public V get（Object key）{ 
        MyLinearMap <K，V> map = chooseMap（key）; return map.get（key）; 
    } 
    public V remove（Object key）{ 
        MyLinearMap <K，V> map = chooseMap（key）; 
        return map.remove（key）; 
    }
```

每个方法都计算键的哈希，这是常数时间，然后在一个子映射上调用一个方法，这个方法是常数时间的。**（这些发方法总体上都是常数的）**

但另一个核心方法，`put`有点难分析。当我们**不需要`rehash`时，它是常数时间**，反之，它是线性的。这样同之前的**摊销分析**类似。

假设子映射的初始数量`k`为`2`，负载因子为`1`。**现在我们来看看`put`一系列的键需要多少工作量**。作为基本的“工作单位”，我们**将计算对密钥哈希，并将其添加到子映射中的次数**。

我们第一次调用`put`时，它需要`1`个工作单位。第二次也需要`1`个单位。第三次我们需要`rehash`，所以需要`2`个单位重新填充现有的键，和`1`个单位来对新键哈希。

现在哈希表的大小是`4`，所以下次调用`put`时 ，需要`1`个工作单位。但是下一次我们必须`rehash`，需要`4`个单位来`rehash`现有的键，和`1`个单位来对新键哈希。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/11-1.jpg)

如图所示，**对新键哈希的正常工作量在底部展示（计算哈希），额外工作量展示为塔楼**(扩容部分)。

如箭头所示，如果我们把塔楼推倒，每个积木都会在下一个塔楼之前填满空间。结果似乎`2`个单位的均匀高度，这表明`put`的平均工作量约为`2`个单位。**这意味着`put`平均是常数时间**。

这个图还显示了，当我们`rehash`的时候，为什么加倍子映射数量`k`很重要。**如果我们只是加上`k`而不是加倍，那么这些塔楼会靠的太近，他们会开始堆积。这样就不会是常数时间了**。

<br>



###### 3.权衡

我们已经表明，`containsKey`，`get`和`remove`是常数时间，`put`平均为常数时间。我们应该花一点时间来欣赏它有多么出色。**无论哈希表有多大，这些操作的性能几乎相同**。

记住，我们的分析基于一个简单的计算模型，其中每个“工作单位”花费相同的时间量。真正的电脑比这更复杂。特别是，当处理足够小，适应高速缓存的数据结构时，它们通常最快；如果结构不适合高速缓存但仍适合内存，则稍慢一点；如果结构不适合在内存中，则非常慢。

这个实现的**另一个限制是**，**如果我们得到了一个值而不是一个键时，那么散列是不会有帮助的**：`containsValue`是线性的，因为它必须搜索所有的子映射。查找一个值并找到相应的键（或可能的键），没有特别有效的方式。

还有一个限制：`MyLinearMap`的**一些常数时间的方法变成了线性的**。例如：

```java
    public void clear() {
        for (int i=0; i<maps.size(); i++) {
            maps.get(i).clear();
        }
    }
```

`clear`必须清除所有的子映射，子映射的数量与`n`成正比，所以它是线性的。幸运的是，这个操作并不常用，所以在大多数应用中，这种权衡是可以接受的。

<br>



###### 4.检查MyHashMap

在我们继续之前，我们应该检查一下，`MyHashMap.put`是否真的是常数时间。

下面是测试用例：

```java
    public static void profileMyHashMap() {
        Profiler.Timeable timeable = new Profiler.Timeable() {
            Map<String,Integer> map;

            @Override
            public void setup(int n) {
                map = new MyHashMap<String, Integer>();
            }

            @Override
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    map.put(String.format("%10d",i),i);
                }
            }
        };
        int startN = 1000;
        int endMillis = 5000;
        runProfiler("MyHashMap put", timeable, startN, endMillis);
    }
```

测试结果：

```java
1000, 131
2000, 366
4000, 974
8000, 3088
16000, 11760
Estimated slope= 1.6053112075356588
```

可以发现，**它的斜率为1.6左右，这表明这个实现不是一直都是常数的**，检查一下问题出在哪里.

<br>



###### 5.修复MyHashMap

`MyHashMap`的问题是`size`，它继承自`MyBetterMap`：

```java
    public int size() {
        int total = 0;
        for (MyLinearMap<K, V> map: maps) {
            total += map.size();
        }
        return total;
    }
```

为了累计整个大小，它必须迭代子映射。**由于我们增加了子映射的数量`k`，随着条目数`n`增加，所以`k`与`n`成正比，所以`size`是线性的。**

**`put`也是线性的**，因为它使用`size`：

```java
    public V put(K key, V value) {
        V oldValue = super.put(key, value);

        if (size() > maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }
```

如果`size`是线性的，我们做的一切都浪费了。幸运的是，有一个简单的解决方案，我们以前看过：我们必须维护实例变量中的条目数，并且每当我们调用一个改变它的方法时更新它。（**实时更新size**）

下面是它的初始结构：

```java
public class MyFixedHashMap<K, V> extends MyHashMap<K, V> implements Map<K, V> {

    private int size = 0;

    public void clear() {
        super.clear();
        size = 0;
    }
```

我们不修改`MyHashMap`，我定义一个扩展它的新类。它添加一个新的实例变量`size`，它被初始化为零。

更新`remove`和`put`有点困难，因为当我们调用超类的该方法，我们不能得知子映射的大小是否改变。这是我的解决方式：

```java
    public V remove(Object key) {
        MyLinearMap<K, V> map = chooseMap(key);
        size -= map.size();
        V oldValue = map.remove(key);
        size += map.size();
        return oldValue;
    }
```

`remove`使用`chooseMap`找到正确的子映射，然后减去子映射的大小。它会在子映射上调用`remove`，根据是否找到了键，它可以改变子映射的大小，也可能不会改变它的大小。但是无论哪种方式，**我们将子映射的新大小加到`size`，所以最终的`size`值是正确的。**

新版的`put`方法：

```java
    public V put(K key, V value) {
        MyLinearMap<K, V> map = chooseMap(key);
        size -= map.size();
        V oldValue = map.put(key, value);
        size += map.size();

        if (size() > maps.size() * FACTOR) {
            size = 0;
            rehash();
        }
        return oldValue;
    }
    
        public int size() {
        return size;
    }
```

当我测量这个解决方案时，我发现放入`n`个键的总时间正比于`n`，也就是说，**每个`put`是常数时间的**，符合预期。

我们正常的去走下流程，`chooseMap`为常数时间，子映射的`push`为常数时间，`rehash`为常数时间，**现在的`put`方法是常数时间，**同之前对比，我们所作的改变主要体现在两个方面：

- **摊销分析**，通过摊销分析，随着map变大，扩充哈希表，添加子映射摊销时间复杂度，通过加载因子控制每个子映射的数量
- **实时更新Size**，由于之前的摊销分析，增加了多个子映射，size()由原来的常数级变为线性级方法，我们实时维护更新它，使得它也变为常数级方法

下面给出性能测试：

```java
public static void profileMyFixedHashMap() {
    Profiler.Timeable timeable = new Profiler.Timeable() {
        Map<String,Integer> map;

        @Override
        public void setup(int n) {
            map = new MyFixedHashMap<String, Integer>();
        }

        @Override
        public void timeMe(int n) {
            for (int i=0; i<n; i++) {
                map.put(String.format("%10d",i),i);
            }
        }
    };
    int startN = 8000;
    int endMillis = 1000;
    runProfiler("MyHashMap put", timeable, startN, endMillis);
}

```

测试结果：

```java
8000, 243
16000, 288
32000, 428
64000, 615
128000, 1281
Estimated slope= 0.5891002112256021
```

<br>



###### 6.UML类图

这里用 [yUML](http://yuml.me/) 生成**UML类图**，帮助我们梳理本章中的类

![](https://wizardforcel.gitbooks.io/think-dast/content/img/11-2.jpg)

不同的关系由不同的箭头表示：

- 实心箭头表示 HAS-A 关系。例如，每个`MyBetterMap`实例包含多个`MyLinearMap`实例，因此它们通过实线箭头连接。
- 空心和实线箭头表示 IS-A 关系。例如，`MyHashMap`扩展 了`MyBetterMap`，因此它们通过 IS-A 箭头连接。
- 空心和虚线箭头表示一个类实现了一个接口;在这个图中，每个类都实现 `Map`。

**UML 类图提供了一种简洁的方式，来表示大量类集合的信息。**在设计阶段中，它们用于交流备选设计，在实施阶段中，用于维护项目的共享思维导图，并在部署过程中记录设计。

<br><br>



### 第十三章 TreeMap

> 这一章展示了**二叉搜索树**，它是个`Map`接口的高效实现。如果我们想让元素**有序**，它非常实用。



###### 1.哈希的不足

`HashMap`被广泛使用，但并不是唯一的`Map`实现。有几个原因可能需要另一个实现：

- **哈希可能很慢**，所以即使`HashMap`操作是常数时间，“常数”可能很大。 如果哈希函数将键均匀分配给子映射，效果很好。但设计良好的散列函数并不容易，如果太多的键在相同的子映射上，那么`HashMap`的性能可能会很差。
-  **哈希表中的键不以任何特定顺序存储**；实际上，当表增长并且键被重新排列时，顺序可能会改变。对于某些应用程序，必须或至少保持键的顺序，这很有用。

很难同时解决所有这些问题，但是 Java 提供了一个称为`TreeMap`的实现：

- **它不使用哈希函数**，所以它避免了哈希的开销和选择哈希函数的困难。
- 在`TreeMap`之中，**键被存储在二叉搜索树中**，这使我们可以以线性时间顺序遍历键。
- 核心方法的**运行时间与`log(n)`成正比**，并不像常数时间那样好，但仍然非常好。

下一节中，我将解释二进制搜索树如何工作，然后你将使用它来实现`Map`。

<br>



###### 2.二叉搜索树

二叉搜索树（BST）是一个树，其中每个`node`（节点）包含一个键，并且每个都具有“BST 属性”：

- 如果`node`有一个左子树，左子树中的所有键都必须小于`node`的键。
- 如果`node`有一个右子树，右子树中的所有键都必须大于`node`的键。

二叉搜索树示例：

![](https://wizardforcel.gitbooks.io/think-dast/content/img/12-1.jpg)

在二叉搜索树中查找一个键是很快的，因为我们不必搜索整个树。从根节点开始，我们可以使用以下算法：

- 将你要查找的键`target`，与当前节点的键进行比较。如果他们相等，你就完成了。
- 如果`target`小于当前键，搜索左子树。如果没有，`target`不在树上。
- 如果`target`大于当前键，搜索右子树。如果没有，`target`不在树上。

在树的每一层，你只需要搜索一个子树。例如，如果你在上图中查找`target = 4`，则从根节点开始，它包含键`8`。因为`target`小于`8`，你走了左边。因为`target`大于`3`，你走了右边。因为`target`小于`6`，你走了左边。然后你找到你要找的键

现在你可能会看到这个规律。如果我们将树的层数从`1`数到`n`，第`i`层可以拥有多达`2^(n-1)`个节点。`h`层的树共有`2^h-1`个节点。如果我们有：

```java
n = 2^h - 1
```

我们可以对两边取以`2`为底的对数：

```java
log2(n) ≈ h
```

意思是树的高度正比于`logn`，**如果它是满的(每一层包含最大数量的节点)**，满二叉树。

<br>



###### 3.TreeMap结构

这里将要使用**二叉搜索树**编写`Map`接口的一个实现，这是开头结构：

```java
public class MyTreeMap<K, V> implements Map<K, V> {

    private int size = 0;
    private Node root = null;
```

`size`追踪键的数量，`root`为根节点，下面是`Node`的定义：

```java
    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
```

这时，我们可以实现一些相对简单的方法：

```java
 public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        root = null;
    }
```

<br>





### 第十四章 二叉搜索树

本章继续承接上章的内容，具体实现`TreeMap`中的方<br>



###### 1.简单的`TreeMap`

这里比较核心的一个方法是`findNode`，用来寻找与键值相当的节点，下面是它的实现：

```java
  private Node findNode(Object target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        Comparable<?super K> k = (Comparable<?super K>)target;

        Node node = root;
        while (node != null) {
            int cmp = k.compareTo(node.key);
            if (cmp <0)
                node = node.left;
            else if (cmp>0)
                node = node.right;
            else 
            return node;
        }
        return null;
    }
```

- 在这个实现中，`null`不是键的合法值。
- 在我们可以在`target`上调用`compareTo`之前，我们必须把它强制转换为某种形式的`Comparable`。这里使用的“**类型通配符**”会尽可能允许；也就是说，它适用于任何实现`Comparable`类型，并且它的`compareTo`接受`K`或者任和`K`的超类（**可以同任何类型做比较**）。

之后，实际搜索比较简单。我们初始化一个循环变量`node`来引用根节点。每次循环中，我们将目标与`node.key`比较。**如果目标小于当前键，我们移动到左子树。如果它更大，我们移动到右子树。如果相等，我们返回当前节点(这里用的是迭代，不断赋值)。**

<br>



###### 2.搜索值

**`findNode`运行时间与树的高度成正比**，而不是节点的数量，因为我们不必搜索整个树。**但是对于`containsValue`，我们必须搜索值，而不是键**；BST 的特性不适用于值，因此我们必须搜索整个树。

下面是`containsValue`方法，这里用**递归**实现：

```java
public boolean containsValue(Object target) {
    return containsValueHelper(root, target);
}

private boolean containsValueHelper(Node node, Object target) {
    if (node == null) {
        return false;
    }
    if (equals(target, node.value)) {
        return true;
    }
    if (containsValueHelper(node.left, target)) {
        return true;
    }
    if (containsValueHelper(node.right, target)) {
        return true;
    }
    return false;
}
```

这是`containsValueHelper`的工作原理：

- 第一个`if`语句检查递归的边界情况。如果`node`是`null`，那意味着我们已经递归到树的底部，没有找到`target`，所以我们应该返回`false`。请注意，**这只意味着目标没有出现在树的一条路径上；它仍然可能会在另一条路径上被发现。**
- 第二种情况检查我们是否找到了我们正在寻找的东西。如果是这样，我们返回`true`。否则，我们必须继续。
- 第三种情况是执行递归调用，在左子树中搜索`target`。如果我们找到它，我们可以立即返回`true`，而不搜索右子树。否则我们继续。
- 第四种情况是搜索右子树。同样，如果我们找到我们正在寻找的东西，我们返回`true`。否则，我们搜索完了整棵树，返回`false`。

该方法“访问”了树中的每个节点，**所以它的所需时间与节点数成正比**。

<br>



###### 3.实现`put`

`put`方法比起`get`要复杂一些，因为要处理两种情况：

1. **如果给定的键已经在树中，则替换并返回旧值**；
2. **否则必须在树中添加一个新的节点，在正确的地方**。

```java
public V put(K key, V value) {
    if (key == null) {
        throw new IllegalArgumentException();
    }
    if (root == null) {
        root = new Node(key, value);
        size++;
        return null;
    }
    return putHelper(root, key, value);
}

private V putHelper(Node node, K key, V value) {
    Comparable<? super K> k = (Comparable<? super K>) key;
    int cmp = k.compareTo(node.key);

    if (cmp < 0) {
        if (node.left == null) {
            node.left = new Node(key, value);
            size++;
            return null;
        } else {
            return putHelper(node.left, key, value);
        }
    }
    if (cmp > 0) {
        if (node.right == null) {
            node.right = new Node(key, value);
            size++;
            return null;
        } else {
            return putHelper(node.right, key, value);
        }
    }
    V oldValue = node.value;
    node.value = value;
    return oldValue;
}
```

第一个参数`node`最初是树的根，但是每次我们执行递归调用，它指向了不同的子树。就像`get`一样，我们用`compareTo`方法来弄清楚，跟随哪一条树的路径。如果`cmp < 0`，我们添加的键小于`node.key`，那么我们要走左子树。有两种情况：

- 如果左子树为空，那就是，如果`node.left`是`null`，我们已经到达树的底部而没有找到`key`。这个时候，我们知道`key`不在树上，我们知道它应该放在哪里。所以我们创建一个新节点，并将它添加为`node`的左子树。
- 否则我们进行递归调用来搜索左子树。

如果`cmp > 0`，我们添加的键大于`node.key`，那么我们要走右子树。我们处理的两个案例与上一个分支相同。最后，如果`cmp == 0`，我们在树中找到了键，那么我们更改它并返回旧的值。

<br>



###### 4.中序遍历

这里我们还剩最后一个方法`KeySet`，它返回一个`Set`，按升序包含树中的键。在其他`Map`实现中，`keySet`返回的键没有特定的顺序，但是树形实现的一个功能是，**对键进行简单而有效的排序**。下面是如何实现它的：

```java
public Set<K> keySet() {
    Set<K> set = new LinkedHashSet<K>();
    addInOrder(root, set);
    return set;
}

private void addInOrder(Node node, Set<K> set) {
    if (node == null) return;
    addInOrder(node.left, set);
    set.add(node.key);
    addInOrder(node.right, set);        
}
```

在`keySet`中，我们创建一个`LinkedHashSet`，这是一个`Set`实现，使元素保持有序,第一个参数`node`最初是树的根，但正如你的期望，我们用它来递归地遍历树。`addInOrder`对树执行经典的“中序遍历”。

1. 按顺序遍历左子树。
2. 添加`node.key`。
3. 按顺序遍历右子树。

<br>



###### 5.二叉搜索树的问题

我们获取最有查询效率时，**一般是O(log(n))**，这种情况会在所搜索的树为平衡二叉树时出现，若不是平衡二叉树，搜索效率则会很低。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/13-1.jpg)

如果你思考`put`如何工作，你可以弄清楚发生了什么。每次添加一个新的键时，它都大于树中的所有键，所以我们总是选择右子树，并且总是将新节点添加为，最右边的节点的右子节点。结果是一个“不平衡”的树，只包含右子节点。

**这种树的高度正比于`n`，不是`logn`，所以`get`和`put`的性能是线性的，不是对数的**

<br>



###### 6.自平衡树

这个问题有两种可能的解决方案：

- **你可以避免向`Map`按顺序添加键**。但这并不总是可能的。 你可以制作一棵树，如果碰巧按顺序处理键，那么它会更好地处理键。(**按顺序添加会导致这是一个极不平衡的树**)
- 第二个解决方案是更好的，有几种方法可以做到。最常见的是修改`put`，以便它检测树何时开始变得不平衡，如果是，则重新排列节点。具有这种能力的树被称为“自平衡树”。普通的自平衡树包括 AVL 树（“AVL”是发明者的缩写），以及红黑树，这是 Java`TreeMap`所使用的。

总而言之，二叉搜索树可以以对数时间实现`get`和`put`，但是只能按照使得树足够平衡的顺序添加键。**自平衡树通过每次添加新键时，进行一些额外的工作来避免这个问题**。

<br>



###### 7.二叉搜索树的**删除**

**实现思路：**

1. 删除叶子节点
2. 删除只有左孩子的节点
3. 删除只有右孩子的节点
4. 删除左右孩子都有的节点，这里我们需要找出待删除节点的右子树中的最小节点，并放到待删除位置上

下面是实现的具体方案:

```java
public V remove(Object k) {
        // 一会儿我来填这个坑
        Comparable<?super K> key = (Comparable<?super K>)k;
        Node currentNode = this.root; //用来保存待删除节点
        Node parentNode = this.root;  //保存待删除节点的父节点
        boolean isLeftNode = true;   //左右节点判断
        V oldValue = null;

        // 寻找需要的节点(同时记录它的位置)
        while ( (currentNode != null) && (currentNode.key != key) ) {
            parentNode = currentNode;
            int cmp = key.compareTo(currentNode.key);
            if (cmp < 0) {
                currentNode = currentNode.left;
                isLeftNode = true;
            } else {
                currentNode = currentNode.right;
                isLeftNode = false;
            }
        }

        // 空树
        if (currentNode == null) {
            return null;
        }

        // 删除的节点是叶子节点
        if ( (currentNode.left == null) && (currentNode.right == null) ) {
            if (currentNode == this.root) {
                oldValue = root.value;
                root = null;
            } else if (isLeftNode) {
                oldValue = parentNode.left.value;
                parentNode.left = null;
            } else {
                oldValue = parentNode.right.value;
                parentNode.right = null;
            }
        } else if ( (currentNode.right == null) && (currentNode.left != null) ) {   //删除节点只有左孩子(分情况看它挂到哪)
            if (currentNode == this.root) {
                oldValue = root.value;
                root = currentNode.left;
            } else if (isLeftNode) {
                oldValue = currentNode.left.value;
                parentNode.left = currentNode.left;
            } else {
                oldValue = currentNode.right.value;
                parentNode.right = currentNode.left;
            }
        } else if ( (currentNode.right != null) && (currentNode.left == null) ) {   //删除节点只有右孩子
            if (currentNode == root) {
                oldValue = root.value;
                root = currentNode.right;
            } else if (isLeftNode) {
                oldValue = parentNode.left.value;
                parentNode.left = currentNode.right;
            } else {
                oldValue = parentNode.right.value;
                parentNode.right = currentNode.right;
            }
        } else {    //待删除节点既有左子树，又有右子树(思路:将待删除节点右子树的最小节点赋值给待删除节点)
            Node directPostNode = getDirectPostNode(currentNode);
            oldValue = directPostNode.value;
            currentNode.key = directPostNode.key;
            currentNode.value = directPostNode.value;
        }
        size--;
        return oldValue;
    }
```

**得到待删除节点的直接后继节点：**

```java
   private Node getDirectPostNode(Node delNode) {

        Node parentNode = delNode;  //用来保存待删除节点的（直接后继节点的父亲节点）
        Node directNode = delNode;  //用来保存待删除节点的（直接后继节点）
        Node currentNode = delNode.right;   // 待删除节点右子树

        while (currentNode != null) {
            parentNode = directNode;
            directNode = currentNode;
            currentNode = currentNode.left;
        }

        // 直接删除此后继节点(因为不是直接相连的,最小的肯定是叶子节点或者没有左子树)
        if (directNode != delNode.right) {
            parentNode.left = directNode.right;
            directNode.right = null;
        }

        return directNode;
    }
```

**具体的可以看图：**

![](http://images2015.cnblogs.com/blog/938494/201702/938494-20170209210350729-446211360.png)







### 第十五章 持久化

现在我们回顾一下搜索引擎的构建:

- 抓取：我们需要一个程序，可以下载一个网页，解析它，并提取文本和任何其他页面的链接。
- 索引：我们需要一个索引，可以查找检索项并找到包含它的页面。
- 检索：我们需要一种方法，从索引中收集结果，并识别与检索项最相关的页面。

抓取和索引我们之前都处理过，但是还不够好，我们现在先处理索引，存在Redis里持久化并排序，最后再处理检索功能。



###### 1.Redis

索引器的之前版本，将索引存储在两个数据结构中：`TermCounter`将检索词映射为网页上显示的次数，以及`Index`将检索词映射为出现的页面集合。

这些数据结构存储在正在运行的 Java 程序的内存中，这意味着当程序停止运行时，索引会丢失。仅在运行程序的内存中存储的数据称为“易失的”，因为程序结束时会消失。

但这个解决方案有几个问题：

- 读取和写入大型数据结构（如 Web 索引）会很慢。
- 整个数据结构可能不适合单个运行程序的内存。
- 如果程序意外结束（例如，由于断电），则自程序上次启动以来所做的任何更改都将丢失。

现在我们用Redis解决这个问题。



###### 2.制作基于Redis的索引

此时，梳理一下思路：

- `JedisMaker.java`包含连接到 Redis 服务器并运行几个 Jedis 方法的示例代码。
- `JedisIndex.java`包含此练习的起始代码。
- `JedisIndexTest.java`包含`JedisIndex`的测试代码。
- `WikiFetcher.java`包含我们在以前的练习中看到的代码，用于阅读网页并使用`jsoup`进行解析。

你还将需要这些文件，你在以前的练习中碰到过：

`Index.java`使用 Java 数据结构实现索引。 `TermCounter.java`表示从检索项到其频率的映射。`WikiNodeIterable.java`迭代`jsoup`生成的 DOM 树中的节点。



现在，我们需要做的事是连接Redis服务器:

这里用的方法是，本地安装redis,java客户端推荐用Jedis

下面是具体创建连接及测试的方法：

```java
public class JedisMaker {

    /**
     * @Author Ragty
     * @Description 创建Redis连接
     * @Date 11:52 2019/5/10
     **/
    public static Jedis make() {
        Jedis jedis = new Jedis("localhost",6379);
        return jedis;
    }


    public static void main(String[] args) {

        Jedis jedis = make();

        // String
        jedis.set("mykey", "myvalue");
        String value = jedis.get("mykey");
        System.out.println("Got value: " + value);

        // Set
        jedis.sadd("myset", "element1", "element2", "element3");
        System.out.println("element2 is member: " + jedis.sismember("myset", "element2"));

        // List
        jedis.rpush("mylist", "element1", "element2", "element3");
        System.out.println("element at index 1: " + jedis.lindex("mylist", 1));

        // Hash
        jedis.hset("myhash", "word1", Integer.toString(2));
        jedis.hincrBy("myhash", "word2", 1);
        System.out.println("frequency of word1: " + jedis.hget("myhash", "word1"));
        System.out.println("frequency of word2: " + jedis.hget("myhash", "word2"));

        jedis.close();
    }


}
```







### 第十六章 爬取百度百科



###### 1.基于Redis的索引器

在我的解决方案中，我们在 Redis 中存储两种结构：

- 对于每个检索词，我们有一个`URLSet`，它是一个 Redis 集合，包含检索词的 URL。
- 对于每个网址，我们有一个`TermCounter`，这是一个 Redis 哈希表，将每个检索词映射到它出现的次数。

在`JedisIndex`中，我提供了一个方法，它可以接受一个检索词并返回 Redis 中它的`URLSet`的键：

```java
 private String urlSetKey(String url) {
        return "URLSet:"+url;
 }
```

以及一个方法，接受 URL 并返回 Redis 中它的`TermCounter`的键：

```java
private String termCounterKey(String term) {
        return "TermCounter:"+term;
}
```

这里是`indexPage`的实现（索引化一个界面）:

```java
	public void indexPage(String url, Elements elements) {
        System.out.println("Indexing..." + url);

        TermCounter tc = new TermCounter(url);
        tc.processElements(elements);

        pushTermCounterToRedis(tc);
    }
```

为了索引页面，我们：

- 为页面内容创建一个 Java 的`TermCounter`，使用上一个练习中的代码。
- 将`TermCounter`的内容推送到 Redis。

以下是将`TermCounter`的内容推送到 Redis 的新代码：

```java
    public List<Object> pushTermCounterToRedis(TermCounter tc) {
        Transaction t = jedis.multi();

        String url = tc.getLabel();
        String redisKey = urlSetKey(url);
        t.del(redisKey);

        for (String term: tc.keySet()) {
            Integer count = tc.get(term);
            t.hset(redisKey, term, count.toString());
            t.sadd(termCounterKey(term),url);
        }
        List<Object> res = t.exec();
        return res;
    }
```

该方法使用`Transaction`来收集操作，**并将它们一次性发送到服务器，这比发送一系列较小操作要快得多**。

它遍历`TermCounter`中的检索词。对于每一个，它：

- 在 Redis 上寻找或者创建`TermCounter`，然后为新的检索词添加字段。
- 在 Redis 上寻找或创建`URLSet`，然后添加当前的 URL。

如果页面已被索引，则`TermCounter`在推送新内容之前删除旧页面 。

这里还需要`getCounts`，它需要一个检索词，并从该词出现的每个网址返回一个映射。这是我的解决方案：

```java
    public Map<String, Integer> getCounts(String term) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Set<String> urls = getURLs(term);
        for (String url: urls) {
            Integer count = getCount(url, term);
            map.put(url, count);
        }
        return map;
    }
```

此方法使用两种辅助方法：

- `getURLs`接受检索词并返回该字词出现的网址集合。
- `getCount`接受 URL 和检索词，并返回该检索词在给定 URL 处显示的次数。

以下是实现：

```java
    public Set<String> getURLs(String term) {
        Set<String> set = jedis.smembers(termCounterKey(term));
        return set;
    }

    public Integer getCount(String url, String term) {
        String redisKey = urlSetKey(url);
        String count = jedis.hget(redisKey, term);
        return new Integer(count);
    }
```



###### 2.查找的分析

假设我们索引了`N`个页面，并发现了`M`个唯一的检索词。检索词的查询需要多长时间？在继续之前，先考虑一下你的答案。

要查找一个检索词，我们调用`getCounts`，其中：

- 创建映射。
- 调用`getURLs`来获取 URL 的集合。
- 对于集合中的每个 URL，调用`getCount`并将条目添加到`HashMap`。

`getURLs`**所需时间与包含检索词的网址数成正比**。对于罕见的检索词，这可能是一个很小的数字，但是对于常见检索词，它可能和`N`一样大。

在循环中，我们调用了`getCount`，它在 Redis 上寻找`TermCounter`，查找一个检索词，并向`HashMap`添加一个条目。那些都是常数时间的操作，所以在最坏的情况下，`getCounts`的整体复杂度是`O(N)`。然而实际上，运行时间正比于包含检索词的页面数量，通常比`N`小得多。

这个算法根据复杂性是有效的，但是它非常慢，因为它向 **Redis 发送了许多较小的操作**。

解决办法：利用事务，解决这个问题：

```java
    public Map<String,Integer> getCountsFaster(String term) {
        //将set集合转换为list,以便每次都有相同的遍历顺序
        List<String> urls = new ArrayList<String>();
        urls.addAll(getUrls(term));

        // 创建一个事务执行所有查询
        Transaction t = jedis.multi();
        for (String url: urls) {
            String redisKey = urlSetKey(url);
            t.hget(redisKey,term);
        }
        List<Object> res = t.exec();

        //遍历结果，制造出map
        Map<String,Integer> map = new HashMap<String, Integer>();
        int i=0;
        for (String url: urls) {
            System.out.println(url);
            Integer count = new Integer((String)res.get(i++));
            map.put(url,count);
        }
        return map;
    }
```



###### 3.索引的分析

使用我们设计的数据结构，页面的索引需要多长时间？再次考虑你的答案，然后再继续。

为了索引页面，我们遍历其 DOM 树，找到所有`TextNode`对象，并将字符串拆分成检索词。这一切都与页面上的单词数成**正比**。

对于每个检索词，我们在`HashMap`中增加一个计数器，这是一个常数时间的操作。所以创建`TermCounter`的所需时间与页面上的单词数成正比。

将`TermCounter`推送到 Redis ，需要删除`TermCounter`，对于唯一检索词的数量是线性的。那么对于每个检索词，我们必须：

- 向`URLSet`添加元素，并且
- 向 Redis`TermCounter`添加元素。

这两个都是常数时间的操作，所以推送`TermCounter`的总时间对于唯一检索词的数量是线性的。

总之，`TermCounter`的创建与页面上的单词数成正比。向 Redis 推送`TermCounter`与唯一检索词的数量成正比。

由于页面上的单词数量通常超过唯一检索词的数量，因此整体复杂度与页面上的单词数成正比。理论上，一个页面可能包含索引中的所有检索词，因此最坏的情况是`O(M)`，但实际上我们并不期待看到更糟糕的情况。

这个分析提出了一种提高效率的方法：**我们应该避免索引很常见的词语（停止词）**。首先，他们占用了大量的时间和空间，因为它们出现在几乎每一个`URLSet`和`TermCounter`中。此外，它们不是很有用，因为它们不能帮助识别相关页面。



###### 4.图的遍历

如果你在第七章中完成了“到达哲学”练习，你已经有了一个程序，它读取页面，找到第一个链接，使用链接加载下一页，然后重复。这个程序是一种专用的爬虫，但是当人们说“网络爬虫”时，他们通常意味着一个程序：

**加载起始页面并对内容进行索引， 查找页面上的所有链接**，并将链接的 URL 添加到集合中 通过收集，加载和索引页面，以及添加新的 URL，来按照它的方式工作。 如果它找到已经被索引的 URL，会跳过它。

你可以将 Web 视为图，其中每个页面都是一个节点，每个链接都是从一个节点到另一个节点的有向边。

**从源节点开始，爬虫程序遍历该图，访问每个可达节点一次。**

我们用于存储 URL 的集合决定了爬虫程序执行哪种遍历：

- 如果它是先进先出（FIFO）的队列，则爬虫程序将执行广度优先遍历。
- 如果它是后进先出（LIFO）的栈，则爬虫程序将执行深度优先遍历。
- 更通常来说，集合中的条目可能具有优先级。例如，我们可能希望对尚未编入索引的页面给予较高的优先级。



###### 5.构建WikiCrawler

> 构建这个类的主要目的为爬取百度百科，并将爬取过的数据持久化，方便日后查询

需要使用到的类：

- `JedisMaker.java`
- `WikiFetcher.java`
- `TermCounter.java`
- `WikiNodeIterable.java`

这里提供`WikiCrawler`的起始代码：

```java
public class WikiCrawler {

    public final String source;
    private JedisIndex index;
    private Queue<String> queue = new LinkedList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    public WikiCrawler(String source, JedisIndex index) {
        this.source = source;
        this.index = index;
        queue.offer(source);
    }

    public int queueSize() {
        return queue.size();
    }
```

这里边的变量代表为：

- `source`是我们开始抓取的网址。
- `index`是`JedisIndex`，连接Redis做数据持久化，结果应该放进这里。
- `queue`是`LinkedList`，这里面我们跟踪已发现但尚未编入索引的网址。
- `wf`是`WikiFetcher`，我们用来读取和解析网页。

下面是`crawl()`方法：

```java
   public String crawl(boolean testing) throws IOException {
        if (queue.isEmpty()) {
            return null;
        }
        String url = queue.poll();
        System.out.println("Crawling..."+ url);

        if (testing==false && index.isIndexed(url)) {
            System.out.println("Already indexed.");
            return null;
        }

        Elements para;
        if (testing) {
            para = wf.readWikiPedia(url);
        } else {
            para = wf.fetchWikiPedia(url);
        }
        index.indexPage(url,para);
        queueInternalLinks(para);
        return url;
    }
```

> 该方法主要用来获取队列中的Url地址并索引化

当传递参数 testing为true时:

- 以 FIFO 的顺序从队列中选择并移除一个 URL。
- 使用`WikiFetcher.readWikipedia`读取页面的内容
- 它应该索引页面，而不管它们是否已经被编入索引。
- 它应该找到页面上的所有内部链接，并按他们出现的顺序将它们添加到队列中。
- 它应该返回其索引的页面的 URL

当传递参数为`false`时

- 以 FIFO 的顺序从队列中选择并移除一个 URL。
- 如果 URL 已经被编入索引，它不应该再次索引，并应该返回`null`。
- 否则它应该使用`WikiFetcher.fetchWikipedia`读取页面内容，从 Web 中读取当前内容。
- 然后，它应该对页面进行索引，将链接添加到队列，并返回其索引的页面的 URL。



下面提供一下其中用到的`queueInternalLinks()`

> 该方法的主要作用为**解析para,并将内部链接添加到队列**

下面是该方法的具体实现：

```java
    /**
     * @Author Ragty
     * @Description 解析para,并将内部链接添加到队列
     * @Date 11:38 2019/5/22
     **/
    public void queueInternalLinks(Elements paras) {
        for (Element para: paras) {
            queueInternalLinks(para);
        }
    }


    /**
     * @Author Ragty
     * @Description 单个段落解析并添加
     * @Date 11:39 2019/5/22
     **/
    private void queueInternalLinks(Element para) {
        Elements elts = para.select("a[href]");
        for (Element element: elts) {
            String relURL = element.attr("href");

            // need to fix
            if (relURL.startsWith("/item/")) {
                String absURL = "https://baike.baidu.com" + relURL;
                queue.offer(absURL);
            }
        }
    }
```

要确定链接是否为“内部”链接，我们检查 URL 是否以`/item/`开头。这可能包括我们不想索引的一些页面，如有关百度百科的元页面。它可能会排除我们想要的一些页面，例如非英语语言页面的链接。但是，这个简单的测试足以起步了。





### 第十七章 布尔检索

接着上一节的内容，我们已经构建了一个爬取百度百科并持久化的爬虫。现在客户使用，我们需要构建一个搜索工具。



###### 1.信息检索

这个项目的下一个阶段是实现一个搜索工具。我们需要的部分包括：

- 一个界面，其中用户可以提供检索词并查看结果。
- 一种查找机制，它接收每个检索词并返回包含它的页面。
- **用于组合来自多个检索词的搜索结果的机制**。
- 对搜索结果**打分和排序的算法**。



###### 2.布尔搜索

大多数搜索引擎可以执行“布尔搜索”，这意味着你**可以使用布尔逻辑来组合来自多个检索词的结果**。例如：

- 搜索“java + 编程”（加号可省略）可能只返回包含两个检索词：“java”和“编程”的页面。
- “java OR 编程”可能会返回包含任一检索词但不一定同时出现的页面。
- “java -印度尼西亚”可能返回包含“java”，不包含“印度尼西亚”的页面。

**包含检索词和运算符的表达式称为“查询”。**

当应用给搜索结果时，布尔操作符`+`，`OR`和`-`对应于集合操作 交，并和差。例如，假设

- `s1`是包含“java”的页面集，
- `s2`是包含“编程”的页面集，以及
- `s3`是包含“印度尼西亚”的页面集。

在这种情况下：

- `s1`和`s2`的交集是含有“java”和“编程”的页面集。
- `s1`和`s2`的并集是含有“java”或“编程”的页面集。
- `s1`与`s2`的差集是含有“java”而不含有“印度尼西亚”的页面集。

布尔搜索实现：

```java
   /**
     * @Author Ragty
     * @Description 计算两个搜索结果的并集(更广了)
     * @Date 23:13 2019/5/30
     **/
    public WikiSearch or(WikiSearch that) {
        Map<String,Integer> union = new HashMap<String, Integer>(map);
        for(String term: that.map.keySet()) {
            union.put(term,totalRelevence(this.getRelevance(term),that.getRelevance(term)));
        }
        return new WikiSearch(union);
    }


    /**
     * @Author Ragty
     * @Description 计算搜索项的交集
     * @Date 23:22 2019/5/30
     **/
    public WikiSearch and(WikiSearch that) {
        Map<String,Integer> intersection = new HashMap<String, Integer>();
        for(String term: that.map.keySet()) {
            if(map.containsKey(term)) {
                intersection.put(term,totalRelevence(this.getRelevance(term),that.getRelevance(term)));
            }
        }
        return new WikiSearch(intersection);
    }


    /**
     * @Author Ragty
     * @Description 计算搜索项的差集
     * @Date 23:35 2019/5/30
     **/
    public WikiSearch minus(WikiSearch that) {
        Map<String,Integer> difference = new HashMap<String, Integer>(map);
        for (String term: that.map.keySet()) {
            if (difference.containsKey(term)) {
                difference.remove(term);
            }
        }
        return  new WikiSearch(difference);
    }
```



###### 3.打分排序

现在需要根据检索词获取用户最需要的数据，`WikiSearch`对象包含 URL 到它们的相关性分数的映射。在信息检索的上下文中，**“相关性分数”用于表示页面多么满足从查询推断出的用户需求**。相关性分数的构建有很多种方法，但大部分都基于“检索词频率”，它是搜索词在页面上的显示次数。一种常见的相关性分数称为 TF-IDF，代表“**检索词频率 - 逆向文档频率”**。

下面是`WikiSearch`的初始构造:

```java
public class WikiSearch {

    // map from URLs that contain the term(s) to relevance score
    private Map<String, Integer> map;

    public WikiSearch(Map<String, Integer> map) {
        this.map = map;
    }

    public Integer getRelevance(String url) {
        Integer relevance = map.get(url);
        return relevance==null ? 0: relevance;
    }
}
```

`WikiSearch`对象包含 URL 到它们的相关性分数的映射。在信息检索的上下文中，**“相关性分数”用于表示页面多么满足从查询推断出的用户需求**。相关性分数的构建有很多种方法，但大部分都基于“检索词频率”，它是搜索词在页面上的显示次数。一种常见的相关性分数称为 TF-IDF，代表“检索词频率 - 逆向文档频率”。

我们将从一些更简单的 TF 开始：

- 如果查询包含单个检索词，页面的相关性就是其词频；也就是说该词在页面上出现的次数。
- 对于具有多个检索词的查询，页面的相关性是检索词频率的总和；也就是说，任何检索词出现的总次数。

搜索结果排序实现：

```java
   public List<Entry<String,Integer>> sort(){
        List<Entry<String,Integer>>  entries = new LinkedList<Entry<String, Integer>>(map.entrySet());

        Comparator<Entry<String,Integer>> comparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };

        Collections.sort(entries,comparator);
        return entries;
    }
```

有两个`sort`版本：

- 单参数版本接受列表并使用它的`compareTo`方法对元素进行排序，因此元素必须是`Comparable`。
- 双参数版本接受任何对象类型的列表和一个`Comparator`，它是一个提供`compare`方法的对象，用于比较元素。



###### 4.检索测试🎈

下面提供检索代码：

```java
public static void main(String[] args) throws IOException {
        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        // search for the first term
        String term1 = "java";
        System.out.println("Query: " + term1);
        WikiSearch search1 = search(term1, index);
        search1.print();

        // search for the second term
        String term2 = "programming";
        System.out.println("Query: " + term2);
        WikiSearch search2 = search(term2, index);
        search2.print();

        // compute the intersection of the searches
        System.out.println("Query: " + term1 + " AND " + term2);
        WikiSearch intersection = search1.and(search2);
        intersection.print();

    }
```

检索结果为：

```java
Query: java🎈
https://baike.baidu.com/item/Java/85979         38
https://baike.baidu.com/item/%E6%AC%A7%E6%9C%8B%E6%B5%8F%E8%A7%88%E5%99%A8         12
https://baike.baidu.com/item/%E7%94%B2%E9%AA%A8%E6%96%87/471435         10
https://baike.baidu.com/item/PHP/9337         6
https://baike.baidu.com/item/Brendan%20Eich         6
https://baike.baidu.com/item/Rhino         6
https://baike.baidu.com/item/LiveScript         6
https://baike.baidu.com/item/Sun/69463         4

Query: programming🎈
https://baike.baidu.com/item/C%E8%AF%AD%E8%A8%80         6
https://baike.baidu.com/item/%E8%84%9A%E6%9C%AC%E8%AF%AD%E8%A8%80         1

Query: java AND programming🎈
https://baike.baidu.com/item/C%E8%AF%AD%E8%A8%80         7
https://baike.baidu.com/item/%E8%84%9A%E6%9C%AC%E8%AF%AD%E8%A8%80         3
```



###### 5.搜索优化

现存的问题：

> 若同一主题的文章，A1万字，它的关键词出现的次数肯定会高，B100字，出现的次数肯定会少。对于具有多个检索词的查询，每个页面的总体相关性目前是每个检索词的相关性的总和。但实际上采用我们上述所用的方法不能客观的去选出内容最符合的文章。

我们需要的解决方案：

>去掉网络**中止词**后，显示一个词在文本中所出现的频率，频率越高，则说明该搜索词与文章的相关性越大。
>
>后期可用`TF-IDF`算法优化

[Github代码源码实现](https://github.com/huoji555/Shadow/tree/master/DataStructure/SearchEngine)

[TF-IDF原理介绍](<https://blog.csdn.net/huoji555/article/details/90732576>)





### 第十八章 排序算法



###### 1.插入排序

> 插入排序是一种简单的排序算法，从头遍历数组，依此比较排序

代码实现：

```java
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
```

测试：

```java
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
```

`insertionSort`有两个嵌套循环，所以你可能会猜到，它的运行时间是二次的。在这种情况下，一般是正确的，但你做出这个结论之前，你必须检查，每个循环的运行次数与`n`，数组的大小成正比。

外部循环从`1`迭代到`list.size()`，因此对于列表的大小`n`是线性的。内循环从`i`迭代到`0`，所以在`n`中也是线性的。因此，两个循环运行的总次数是二次的。

如果你不确定，这里是证明：

第一次循环中，`i = 1`，内循环最多运行一次。 第二次，`i = 2`，内循环最多运行两次。 最后一次，`i = n - 1`，内循环最多运行`n`次。

因此，内循环运行的总次数是序列`1, 2, ..., n - 1`的和，即`n(n - 1)/2`。该表达式的主项（拥有最高指数）为`n^2`。

在最坏的情况下，插入排序是二次的。然而：

- 如果这些元素已经有序，或者几乎这样，插入排序是线性的。具体来说，如果每个元素距离它的有序位置不超过`k`个元素，则内部循环不会运行超过`k`次，并且总运行时间是`O(kn)`。
- 由于实现简单，开销较低；也就是，尽管运行时间是`an^2`，主项的系数`a`，也可能是小的。

所以如果我们知道数组几乎是有序的，或者不是很大，插入排序可能是一个不错的选择。



###### 2.归并排序

>归并操作(merge)，也叫归并算法，指的是将两个顺序序列合并成一个顺序序列的方法。
>
>如　设有数列{6，202，100，301，38，8，1}
>
>初始状态：6,202,100,301,38,8,1
>
>第一次归并后：{6,202},{100,301},{8,38},{1}，比较次数：3；
>
>第二次归并后：{6,100,202,301}，{1,8,38}，比较次数：4；
>
>第三次归并后：{1,6,8,38,100,202,301},比较次数：4；
>
>总的比较次数为：3+4+4=11；逆序数为14；

![](https://images2015.cnblogs.com/blog/1023577/201610/1023577-20161011232321687-190186195.png)



###### 3.归并排序分析

为了对归并排序的运行时间进行划分，对递归层级和每个层级上完成多少工作方面进行思考，是很有帮助的。假设我们从包含`n`个元素的列表开始。以下是算法的步骤：

- 生成两个新数组，并将一半元素复制到每个数组中。
- 排序两个数组。
- 合并两个数组。

流程图如下：

![](https://wizardforcel.gitbooks.io/think-dast/content/img/17-1.jpg)

第一步复制每个元素一次，因此它是线性的。第三步也复制每个元素一次，因此它也是线性的。现在我们需要弄清楚步骤`2`的复杂性。为了做到这一点，查看不同的计算图片会有帮助，它展示了递归的层数，如下图所示。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/17-2.jpg)

> 上图为**归并排序的展示**，它展示了递归的所有层级。

在顶层，我们有`1`个列表，其中包含`n`个元素。为了简单起见，我们假设`n`是`2`的幂。在下一层，有`2`个列表包含`n/2`个元素。然后是`4`个列表与`n/4`元素，以此类推，直到我们得到`n`个列表与`1`元素。

在每一层，我们共有`n`个元素。在下降的过程中，我们必须将数组分成两半，这在每一层上都需要与`n`成正比的时间。在回来的路上，我们必须合并`n`个元素，这也是线性的。

如果层数为`h`，算法的总工作量为`O(nh)`。那么有多少层呢？有两种方法可以考虑：

- 我们用多少步，可以将`n`减半直到`1`？
- 或者，我们用多少步，可以将`1`加倍直到`n`？

第二个问题的另一种形式是“`2`的多少次方是`n`”？

```
2^h = n
```

对两边取以`2`为底的对数：

```
h = log2(n)
```

所以总时间是`O(nlogn)`。我没有纠结于对数的底，因为底不同的对数差别在于一个常数，所以所有的对数都是相同的增长级别。

`O(nlogn)`中的算法有时被称为“线性对数”的，但大多数人只是说`n log n`。

事实证明，`O(nlogn)`是通过元素比较的排序算法的理论下限。这意味着没有任何“比较排序”的增长级别比`n log n`好。



###### 4.归并排序实现

实现代码：

```java
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
```

测试代码：

```java
list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
sorter.mergeSortInplace(list, comparator);
System.out.println(list);
```

测试结果：

```java
[1, 2, 3, 4, 5]
```



###### 5.基数排序

`基数排序`是一种**非比较排序算法**，如果元素的大小是**有界**的，例如 32 位整数或 20 个字符的字符串，它就可以工作。

为了看看它是如何工作的，想象你有一堆索引卡，每张卡片包含三个字母的单词。以下是一个方法，可以对卡进行排序：

- 根据第一个字母，将卡片放入桶中。所以以`a`开头的单词应该在一个桶中，其次是以`b`开头的单词，以此类推
- 根据第二个字母再次将卡片放入每个桶。所以以`aa`开头的应该在一起，其次是以`ab`开头的，以此类推当然，并不是所有的桶都是满的，但是没关系。
- 根据第三个字母再次将卡片放入每个桶。

此时，每个桶包含一个元素，桶按升序排列。下图展示了三个字母的例子。

![](https://wizardforcel.gitbooks.io/think-dast/content/img/17-3.jpg)

最上面那行显示未排序的单词。第二行显示第一次遍历后的桶的样子。每个桶中的单词都以相同的字母开头。

第二遍之后，每个桶中的单词以相同的两个字母开头。在第三遍之后，每个桶中只能有一个单词，并且桶是有序的。

在每次遍历期间，我们遍历元素并将它们添加到桶中。只要桶允许在恒定时间内添加元素，每次遍历是线性的。

遍历数量，我会称之为`w`，取决于单词的“宽度”，但不取决于单词的数量，`n`。所以增长级别是`O(wn)`，对于`n`是线性的。



###### 6.堆排序

基数排序适用于大小有界的东西，除了他之外，还有一种你可能遇到的其它专用排序算法：有界堆排序。如果你在处理非常大的数据集，你想要得到前 10 个或者前`k`个元素，其中`k`远小于`n`，它是很有用的。

例如，假设你正在监视一 个Web 服务，它每天处理十亿次事务。在每一天结束时，你要汇报最大的`k`个事务（或最慢的，或者其它最 xx 的）。一个选项是存储所有事务，在一天结束时对它们进行排序，然后选择最大的`k`个。需要的时间与`nlogn`成正比，这非常慢，因为我们可能无法将十亿次交易记录在单个程序的内存中。我们必须使用“外部”排序算法。

我们首先了解一下堆，这是一个类似于二叉搜索树（BST）的数据结构。有一些区别：

- 在 BST 中，每个节点`x`都有“BST 特性”：`x`左子树中的所有节点都小于`x`，右子树中的所有节点都大于`x`。
- 在堆中，每个节点`x`都有“堆特性”：两个子树中的所有节点都大于`x`。
- 堆就像平衡的 BST；当你添加或删除元素时，他们会做一些额外的工作来重新使树平衡。因此，可以使用元素的数组来有效地实现它们。

> 现在讨论的是小根堆。如果子树中的节点都小于根节点，则为大根堆。

堆中最小的元素总是在根节点，所以我们可以在常数时间内找到它。在堆中添加和删除元素需要的时间与树的高度`h`成正比。而且由于堆总是平衡的，所以`h`与`log n`成正比。

Java`PriorityQueue`使用堆实现。`PriorityQueue`提供`Queue`接口中指定的方法，包括`offer`和`poll`：

- `offer`：将一个元素添加到队列中，更新堆，使每个节点都具有“堆特性”。需要`logn`的时间。
- `poll`：从根节点中删除队列中的最小元素，并更新堆。需要`logn`的时间。

给定一个`PriorityQueue`，你可以像这样轻松地排序的`n`个元素的集合 ：

- 使用`offer`，将集合的所有元素添加到`PriorityQueue`。
- 使用`poll`从队列中删除元素并将其添加到`List`。

因为`poll`返回队列中剩余的最小元素，所以元素按升序添加到`List`。这种排序方式称为堆排序 。

向队列中添加`n`个元素需要`nlogn`的时间。删除`n`个元素也是如此。所以堆排序的运行时间是`O(n logn)`。

代码实现：

```java
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
```

测试代码：

```java
list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
sorter.heapSort(list, comparator);
System.out.println(list);
```



###### 7.有界堆排序

有界堆是一个限制为最多包含`k`个元素的堆。如果你有`n`个元素，你可以跟踪这个最大的`k`个元素：

最初堆是空的。对于每个元素`x`：

- 分支 1：如果堆不满，请添加`x`到堆中。
- 分支 2：如果堆满了，请与堆中`x`的最小元素进行比较。如果`x`较小，它不能是最大的`k`个元素之一，所以你可以丢弃它。
- 分支 3：如果堆满了，并且`x`大于堆中的最小元素，请从堆中删除最小的元素并添加`x`。

使用顶部为最小元素的堆，我们可以跟踪最大的`k`个元素。我们来分析这个算法的性能。对于每个元素，我们执行以下操作之一：

- 分支 1：将元素添加到堆是`O(log k)`。
- 分支 2：找到堆中最小的元素是`O(1)`。
- 分支 3：删除最小元素是`O(log k)`。添加`x`也是`O(log k)`。

在最坏的情况下，如果元素按升序出现，我们总是执行分支 3。在这种情况下，处理`n`个元素的总时间是`O(n log k)`，对于`n`是线性的。

代码实现：

```java
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
```

测试代码：

```java
list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
List<Integer> queue = sorter.topK(4, list, comparator);
System.out.println(queue);
```



###### 8.空间复杂性

到目前为止，我们已经谈到了很多运行时间的分析，但是对于许多算法，我们也关心空间。例如，归并排序的一个缺点是它会复制数据。在我们的实现中，它分配的空间总量是`O(n log n)`。通过优化，可以将空间降至`O(n)`。

相比之下，插入排序不会复制数据，因为它会原地排序元素。它使用临时变量来一次性比较两个元素，并使用一些其它局部变量。但它的空间使用不取决于`n`。

我们的堆排序实现创建了新`PriorityQueue`，来存储元素，所以空间是`O(n)`; 但是如果你能够原地对列表排序，则可以使用`O(1)`的空间执行堆排序 。

刚刚实现的有界堆栈算法的一个好处是，它只需要与`k`成正比的空间（我们要保留的元素的数量），而`k`通常比`n`小得多 。

软件开发人员往往比空间更加注重运行时间，对于许多应用程序来说，这是适当的。但是对于大型数据集，空间可能同等或更加重要。例如：

- 如果一个数据集不能放入一个程序的内存，那么运行时间通常会大大增加，或者根本不能运行。如果你选择一个需要较少空间的算法，并且这样可以将计算放入内存中，则可能会运行得更快。同样，使用较少空间的程序，可能会更好地利用 CPU 缓存并运行速度更快。
- 在同时运行多个程序的服务器上，如果可以减少每个程序所需的空间，则可以在同一台服务器上运行更多程序，从而降低硬件和能源成本。





#### 后记

第一次接触到数据结构是大二的时候，那时候只觉得做些网站，搞点新奇的东西出来，就能满足那个时候的好奇心，没办法沉下心好好研究这些东西，等到实习后，才越来越觉得洗净铅华之后，哪些东西才是最重要的，最根本的。我只是很浅显的将书读了几次，并基于书内的练习做了一些对于我很新奇的事情。

我在做这件事的时候就明白，这些东西是很难看到直接利益的，但我仍愿意去做这些看起来"无用的事情"，我觉得阅读它时，能看到别人的思路，也可以自己思考能利用这些做些什么有趣的事，世界上最有趣的事就是安静的做自己喜欢的事情，完全能沉浸在当时的环境中，不管周围喧嚣或是平静，只能感觉到一种新事物通过自己的双手和脑袋在磅礴而出。

写这个的时候，大概是今年四月初，不忙的时候就抽空写这个，后来事情比较多，在五月初停顿了一段时间，我觉得记下这些对我来说很有意义，即使再次看文章的还是我自己。

没想到最后一部分是在火车上完成的，算是行走中的Coding吧。现在窗外变黑了，城市的灯很亮，忽然有点想念前几天在银川看到的星星跟天空，我觉得夜晚的天空就该是深蓝或墨紫的，像傍晚深处的海。

 车停了，这次也就到此为止了。