/**
 * Задание:
 * 1. Создать класс DataContainer у которого есть один дженерик (обобщение). Например литерал E.
 * Данный класс как раз и будет решать задачу поставленную перед нами: хранить неограниченное
 * количество передаваемых объектов обобщённого типа.
 *
 * 2. Внутри DataContainer должно быть поле E[] data, внутренний массив, которое предназначено
 * для хранения передаваемых объектов. Да, именно E[] а не Object[]. Если использовать Object[] то
 * это будет опасно и постоянно придётся делать привидение типов.
 *
 * 3. Из-за особенностей дженериков в данном классе обязательно будет присутствовать один конструктор
 * DataContainer(E[]). Есть и другие способы, но в рамках обучения они будут сложными и с ними
 * мы разбираться будем слишком сложно.
 *
 * 4. В данном классе должен быть метод int add(E item) который добавляет данные во внутреннее
 * поле data, и возвращает номер позиции, в которую были вставлены данные, начиная с 0.
 * 	 4.1 Если поле data не переполнено то данные нужно добавлять в первую позицию (ячейку) после
 * 	     последней заполненной позиции (ячейки).
 * 	 4.1.1 Пример: data = [1, 2, 3, null, null, null]. Вызвали add(777).
 * 	       Должно получиться data = [1, 2, 3, 777, null, null]. Метод add вернёт число 3.
 *   4.1.2 Пример: data = [1, 2, 3, null, null, null]. Вызвали add(null).
 *         Должно получиться data = [1, 2, 3, null, null, null]. Метод add вернёт число -1.
 *        -1 будет индикатором того что данный элемент в наш контейнер вставлять нельзя.
 * 	 4.1.3 Пример: data = [1, null, 3, null, null, null]. Вызвали add(777).
 * 	       Должно получиться data = [1, 777, 3, null, null, null]. Метод add вернёт число 1.
 * 	 4.1.4 Пример: data = []. Вызвали add(777). Должно получиться data = [777].
 * 	       Метод add вернёт число 0.
 * 	 4.2 В случае если поле data переполнено, нужно придумать механизм, который будет расширять
 * 	     пространство для новых элементов. Тут вам поможет Arrays.copyOf.
 * 	 4.2.1 Пример: data = [1, 2, 3]. Вызвали add(777). Должно получиться data = [1, 2, 3, 777].
 * 	       Метод add вернёт число 3.
 */

package hw4_DataContainer;

import objects.students.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class DataContainer<E> {
    private E[] data;//Мы предполагаем какой-то массив информации.

    public DataContainer(E[] data) {
        if (data == null) {//Если нам ничего не передали, выдать ошибку.
            throw new IllegalArgumentException("Ошибка, надо передать массив");
        }
        //this.data = data;-Так не очень правильно, могут возникнуть нежданчики.
        //Вкладка DataContainerMain1 "внезапно" 7 строчка.
        this.data = Arrays.copyOf(data, data.length);//Теперь нам "Внезапно" в data не добавится.
        //Т.к. в strings[0] будет храниться ссылка на старый массив, а мы создали из старого новый.
    }

    /**
     * Данный метод добавляет данные в хранилище, переданный элемент сохраняется в конец хранилища.
     * Запрещено вставлять null.
     *
     * @param item Элемент для сохранения.
     * @return Вернёт -1 если данный элемент вставлять нельзя.
     * Вернёт 0 и больше если элемент был вставлен. Возвращаемое число = индексу,
     * на который был вставлен элемент.
     */
    public int add(E item) {
        if (item == null) {//Если передали null вернётся -1.
            return -1;
        }
        for (int i = 0; i < this.data.length; i++) {//Если попали сюда знаем, что null нам не придёт.
            if (this.data[i] == null) {//Если ячейка пустая - тогда вставляем данные.
                this.data[i] = item;
                return i;
            }
        }
/** 4.2 В случае если поле data переполнено, нужно придумать механизм, который будет расширять
 *  пространство для новых элементов. Тут вам поможет Arrays.copyOf.
 *     4.2.1 Пример: data = [1, 2, 3]. Вызвали add(777). Должно получиться data = [1, 2, 3, 777].
 *     Метод add вернёт число 3.
 */
        this.data = Arrays.copyOf(this.data, this.data.length + 1);//Если мы дошли сюда -
        //мы знаем, что пустых ячеек нет. Тогда горим, что хотим скопировать этот массив и увеличить
        //его длину на 1. Если мы укажем размер больше - то он создаст пустые ячейки, если меньше -
        //то обрежется конец предыдущего массива.
        this.data[this.data.length - 1] = item;//В последнюю ячейку вставляем item.
        return this.data.length - 1;//Возвращаем индекс последней ячейки.
    }

/** 5. В данном классе должен быть метод E get(int index). Данный метод получает из DataContainer-а,
 *     из поля data, предварительно сохранённый объект который мы передали
 *     на предыдущем шаге через метод add.
 5.1 Пример: data = []. Вызвали add(9999). Получили data = [9999]. Метод add вернул число 0.
 Вызываем get(0). Метод get возвращает 9999
 5.2 Пример: data = [9999]. Вызываем get(1). Метод get возвращает null
 */
    /**
     * Данный метод возвращает данные, хранимые в контейнере.
     *
     * @param index Индекс элемента.
     * @return null - если элемент не найден.
     */
    public E get(int index) {
        if (isIndexOutOfBound(index)) {
            return null;
        }
        return this.data[index];
    }

/** 6. В данном классе должен быть метод E[] getItems(). Данный метод возвращает поле data.
 */
    /**
     * Получить содержимое контейнера.
     *
     * @return копия контейнера.
     */
    public E[] getItems() {
        //return this.data; - Опять же, не очень правильно. Смотри вкладку DataContainerMain1 11 строчка.
        return Arrays.copyOf(this.data, this.data.length);//Так лучше.
    }

/** 7. Добавить метод boolean delete(int index) который будет удалять элемент из нашего поля data
 *     по индексу.
 7.1 Метод возвращает true если у нас получилось удалить данные.
 7.1.1 Пример data = [1, 2, 3, 777]. Вызывают delete(3). Должно получиться data = [1, 2, 3].
 Метод delete вернёт true
 7.2 Метод возвращает false если нет такого индекса.
 7.2.1 Пример data = [1, 2, 3, 777]. Вызывают delete(9). Должно получиться data = [1, 2, 3, 777].
 Метод delete вернёт false
 7.3. Освободившуюся ячейку в поле data необходимо удалить полностью.
 Пустых элементов не должно быть.
 7.3.1 Пример data = [1, 2, 3, 777]. Вызывают delete(2). Должно получиться data = [1, 2, 777].
 Метод delete вернёт true.
 */
    /**
     * Удалить элемент из контейнера по индексу.
     *
     * @param index индекс элемента.
     * @return true если удалили, false если ячейки под таким номером нет.
     */
    public boolean delete(int index) {
        if (isIndexOutOfBound(index)) {
            return false;
        }
        for (int i = index + 1; i < this.data.length; i++) {
            this.data[i - 1] = this.data[i];
        }
        this.data = Arrays.copyOf(this.data, this.data.length - 1);
        return true;
    }

/** 8. Добавить метод boolean delete(T item) который будет удалять элемент из нашего поля data.
 8.1 Метод возвращает true если у нас получилось удалить данные.
 8.1.1 Пример data = [1, 2, 3, 777]. Вызывают delete(777). Должно получиться data = [1, 2, 3].
 Метод delete вернёт true
 8.2 Метод возвращает false если нет такого элемента.
 8.2.1 Пример data = [1, 2, 3, 777]. Вызывают delete(111).
 Должно получиться data = [1, 2, 3, 777]. Метод delete вернёт false
 8.3 Освободившуюся ячейку необходимо удалить полностью. Пустых элементов не должно быть.
 8.3.1 Пример data = [1, 2, 3, 777, 3]. Вызывают delete(3).
 Должно получиться data = [1, 2, 777, 3]. Метод delete вернёт true
 */
    /**
     * Удалить первый найденный элемент в контейнере равный переданному.
     *
     * @param element
     * @return
     */
    public boolean delete(E element) {
        int index = -1;

        for (int i = 0; i < this.data.length; i++) {
            if (Objects.equals(this.data[i], element)) {
                index = i;
                break;
            }
        }
        return delete(index);
    }

/** Вынесем эту проверку if (index < 0 || index >= this.data.length) в отдельный метод,
 * т.к. эта проверка у нас используется несколько раз.
 * Делаем в данном случае метод приватным т.к. мы не хотим, чтобы кто-то снаружи пользовался
 * этим нашим методом.
 */
    /**
     * Проверка на попадание индекса в размер контейнера.
     *
     * @param index
     * @return
     */
    private boolean isIndexOutOfBound(int index) {//Если индекс за пределами массива.
        return index < 0 || index >= this.data.length;
    }

    /**
     * 9. Добавить НЕ СТАТИЧЕСКИЙ метод void sort(Comparator<.......> comparator).
     * Данный метод занимается сортировкой данных записанных в поле data используя реализацию
     * сравнения из ПЕРЕДАННОГО объекта comparator. Классом Arrays пользоваться запрещено.
     */
    public void sort(Comparator<E> comparator) {
        sort(this, comparator);
    }

    /**
     * 10. Переопределить метод toString() в классе и выводить содержимое data без ячеек
     * в которых хранится null.
     */
    @Override
    public String toString() {
        if (this.data.length == 0) {
            return "DataContainer{}";
        }
        StringBuilder result = new StringBuilder();

        boolean needComma = false;
        for (E datum : this.data) {

            if (datum != null) {
                if (!needComma) {
                    needComma = true;
                } else {
                    result.append(", ");
                }

                result.append(datum);
            }
        }
        return "DataContainer{" + result + "}";
    }

    /**
     * 11.* В даном классе должен быть СТАТИЧЕСКИЙ метод sort который будет принимать объект
     * DataContainer с дженериком extends Comparable. Данный метод будет сортировать элементы
     * в ПЕРЕДАННОМ объекте DataContainer используя реализацию сравнения вызываемый у хранимых объектов.
     * Для этого надо сделать дженерик метод.
     * <p>
     * Когда применять Comparable, а когда Comparator?
     * Имплементируя Comparable мы прописываем туда сравнение, которое будет выполняться в большей части
     * случаев, например по имени. (Илья приводил пример журнал в школе - по ФИО сортировка.)
     * А в остальных случаях, если нужно например ещё отсортировать по возрасту, по оценке и т.д. -
     * нужно писать Comparator. Т.е. когда есть какое-то дополнительное условие к нашей частой
     * сортировке по какому-то параметру.
     * P.S. Comparable смотри класс Student.
     */
    public static <T extends Comparable<T>> void sort(DataContainer<T> dataContainer) {
        ComparableComparator<T> comparator = new ComparableComparator<>();
        sort(dataContainer, comparator);
    }

    /**
     * 12.* В данном классе должен быть СТАТИЧЕСКИЙ метод void sort
     * (DataContainer<.............> container, Comparator<.......> comparator)
     * который будет принимать объект DataContainer и реализацию интерфейса Comparator.
     * Данный метод будет сортировать элементы в ПЕРЕДАННОМ объекте DataContainer используя
     * реализацию сравнения из ПЕРЕДАННОГО объекта интерфейса Comparator.
     */
    public static <T> void sort(DataContainer<T> dataContainer, Comparator<T> comparator) {
        boolean isSorted;
        Student buffer;

        do {
            isSorted = true;
            for (int i = 1; i < dataContainer.data.length; i++) {
                T from = dataContainer.data[i - 1];
                T to = dataContainer.data[i];
                if (comparator.compare(from, to) > 0) {
                    isSorted = false;

                    dataContainer.data[i] = from;
                    dataContainer.data[i - 1] = to;
                }
            }
        } while (!isSorted);
    }

}
