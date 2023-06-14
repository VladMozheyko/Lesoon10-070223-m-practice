class Node                         // Узел
{
    public int iData;              // Ключ    - для идентификации
    public double dData;           // Дата    - значение
    public Node leftChild;         // Левый ребенок
    public Node rightChild;        // Правый ребенок

    public void displayNode()      // Метод для отображения узела
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
}
