package hw4_DataContainer;

//import generics.comparators.BMWModelComparator;
import objects.car.BMWCar;

public class DataContainerMain1 {
    public static void main(String[] args) {
      //String[] strings = new String[1];
      //DataContainer<String> container = new DataContainer<>(strings);
      //strings[0] = "Внезапно";

        DataContainer<String> container = new DataContainer<>(new String[1]);
        String[] items = container.getItems();
        items[0] = "Внезапно 2";

        int index1 = container.add("Привет");
        int index2 = container.add("Как дела");
        int index3 = container.add("Работаю");
        int index4 = container.add("Давай потом");

        String text1 = container.get(index1);
        String text2 = container.get(index2);
        String text3 = container.get(index3);
        String text4 = container.get(index4);
        System.out.println(text1);//Привет
        System.out.println(text2);//Как дела
        System.out.println(text3);//Работаю
        System.out.println(text4);//Давай потом

        System.out.println("_____");
        container.delete(index1);
        System.out.println(container.get(index1));//Как дела
        container.delete(text2);
        System.out.println(container.get(index1));//Работаю

        DataContainer.sort(container);

        DataContainer<BMWCar> carDataContainer = new DataContainer<>(new BMWCar[0]);
      //BMWModelComparator comparator = new BMWModelComparator();

      //DataContainer.sort(carDataContainer, comparator);

    }
}
