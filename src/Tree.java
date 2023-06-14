import java.util.Stack;

class Tree   // Класс для работы с деревом. Ряд метод предназначен для работы только с непустым деревом
{
    private Node root;             //  Корень

    // -------------------------------------------------------------
    public Tree()                 // Конструктор
    {
        root = null;
    }              //  Инициализируем корень null

    // -------------------------------------------------------------
    public Node find(int key)      // Получаем ключ
    {
        Node current = root;       // Вспомогательная переменная для прохождения по дереву
        while (current.iData != key)  // Проходим по дереву
        {

            // Используем преимущества бинарного поиска(отбрасываем приблизительно половину элементов на каждой итерации
            if (key < current.iData)         // Переход к левому потомку
                current = current.leftChild;
            else                            // В противном случае к рпавому потомку
                current = current.rightChild;
            if (current == null)             // Проверяем если нет потомка
                return null;                 // Вовзращаем null, т.е. указываем что элемента нет
        }
        return current;                    // Возвращаем элемент
    }  // end find()
    // -------------------------------------------------------------

    /**
     * Метод для вставки
     *
     * @param id ключ
     * @param dd данные
     */
    public void insert(int id, double dd) {
        Node newNode = new Node();    // Создаем новый узел
        newNode.iData = id;           // вставляем в него требуемые данные
        newNode.dData = dd;
        if (root == null)                // проверяем есть ли корень
            root = newNode;           // Если корня нет, то текущий элемент становится им
        else                          // в противном случае будем искать место
        {
            Node current = root;       // Создаем вспомогательную переменную
            Node parent;               // Родитель
            while (true)                // Проходим по дереву
            {
                parent = current;      // Текущий элемент становится родителем
                // Пользуемся преимуществами бинарного поиска
                if (id < current.iData)  // Переходим вправо или влево, пока не найдем элемент
                {
                    current = current.leftChild;
                    if (current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if (current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;    // Сама вставка
                        return;                         // return в данном случае завершает работу метода(возвращает управление в точку вызова)
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()

    // -------------------------------------------------------------
    public boolean delete(int key) // delete node with given key
    {
        Node current = root;        // Запоминаем текущий элемент и родителя
        Node parent = root;
        boolean isLeftChild = true;  // Определяем левосторонним или правосторонним является потомок

        while (current.iData != key)        // В цикле перебираем все элементы дерева
        {


            // Бинарный поиск - отбрасываем половину значений на каждой итерации
            parent = current;
            if (key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            } else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete


        // Проверяем какой стороны потомком является удаляемый элемент для корректной перетановки оставшихся значений
        // Левый потомок
        if (current.leftChild == null &&
                current.rightChild == null) {
            if (current == root)             // if root,
                root = null;                 // tree is empty
            else if (isLeftChild)
                parent.leftChild = null;     // disconnect
            else                            // from parent
                parent.rightChild = null;
        }

        // Правый потомок
        else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // Определяем узел, который займет позицию удаленного элемента
        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // Вставляем этот узел
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)
        return true;                                // Если получилось удаление- true
    }  // end delete()
    // -------------------------------------------------------------

    /**
     * Метод для определения приемника на позицию удаленного элемента
     *
     * @param delNode удаляемый элемент
     * @return приемник
     */
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;   //Всегда смотрим на правый элемент
        while (current != null)               // Проходим по дереву
        {                                   // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // Если мы нашли приемника, то делаем связи для него на новой позиции
        if (successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }
    // -------------------------------------------------------------

    // TODO Разобраться, если все остальное понятно
//    public void traverse(int traverseType)
//    {
//        switch(traverseType)
//        {
//            case 1: System.out.print("\nPreorder traversal: ");
//                preOrder(root);
//                break;
//            case 2: System.out.print("\nInorder traversal:  ");
//                inOrder(root);
//                break;
//            case 3: System.out.print("\nPostorder traversal: ");
//                postOrder(root);
//                break;
//        }
//        System.out.println();
//    }
//    // -------------------------------------------------------------
//    private void preOrder(Node localRoot)
//    {
//        if(localRoot != null)
//        {
//            System.out.print(localRoot.iData + " ");
//            preOrder(localRoot.leftChild);
//            preOrder(localRoot.rightChild);
//        }
//    }
//    // -------------------------------------------------------------
//    private void inOrder(Node localRoot)
//    {
//        if(localRoot != null)
//        {
//            inOrder(localRoot.leftChild);
//            System.out.print(localRoot.iData + " ");
//            inOrder(localRoot.rightChild);
//        }
//    }
//    // -------------------------------------------------------------
//    private void postOrder(Node localRoot)
//    {
//        if(localRoot != null)
//        {
//            postOrder(localRoot.leftChild);
//            postOrder(localRoot.rightChild);
//            System.out.print(localRoot.iData + " ");
//        }
//    }
//    // -------------------------------------------------------------
    public void displayTree() {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');

            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if (temp.leftChild != null ||
                            temp.rightChild != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            }  // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()
}
