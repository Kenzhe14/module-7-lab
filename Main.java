import java.util.Scanner;

// ShippingStrategy interface
interface ShippingStrategy {
    double calculateShippingCost(double weight, double distance, boolean night);
}

// StandardShippingStrategy
class StandardShippingStrategy implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance, boolean night) {
        double cost = weight * 0.5 + distance * 0.1;
        if (night) {
            cost += 10;
        }
        return cost;
    }
}

// ExpressShippingStrategy
class ExpressShippingStrategy implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance, boolean night) {
        double cost = (weight * 0.75 + distance * 0.2) + 10; 
        if (night) {
            cost += 10; 
        }
        return cost;
    }
}

// InternationalShippingStrategy 
class InternationalShippingStrategy implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance, boolean night) {
        double cost = weight * 1.0 + distance * 0.5 + 15; 
        if (night) {
            cost += 10;
        }
        return cost;
    }
}

// DeliveryContext
class DeliveryContext {
    private ShippingStrategy shippingStrategy;

    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    public double calculateCost(double weight, double distance, boolean night) {
        if (shippingStrategy == null) {
            throw new IllegalStateException("Shipping strategy is not set.");
        }
        return shippingStrategy.calculateShippingCost(weight, distance, night);
    }
}

// Main 
public class Main {
    public static void main(String[] args) {
        DeliveryContext deliveryContext = new DeliveryContext();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип доставки: 1 - Стандартная, 2 - Экспресс, 3 - Международная");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                deliveryContext.setShippingStrategy(new StandardShippingStrategy());
                break;
            case "2":
                deliveryContext.setShippingStrategy(new ExpressShippingStrategy());
                break;
            case "3":
                deliveryContext.setShippingStrategy(new InternationalShippingStrategy());
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        System.out.println("Введите вес посылки (кг):");
        double weight = Double.parseDouble(scanner.nextLine());
        if(weight < 0){
            System.out.println("Ошибка: вес не может быть отрицательным.");
            return;
        }

        System.out.println("Введите расстояние доставки (км):");
        double distance = Double.parseDouble(scanner.nextLine());
         if (distance < 0) {
            System.out.println("Ошибка: расстояние не может быть отрицательным.");
            return;
        }

        System.out.println("Будет ли ночная доставка (true/false)?");
        boolean night = scanner.nextBoolean();

        double cost = deliveryContext.calculateCost(weight, distance, night);
        System.out.printf("Стоимость доставки: %.2f%n", cost);
    }
}




import java.util.ArrayList;
import java.util.List;

interface IObserver {
    void update(float temperature);
}

interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}

class WeatherStation implements ISubject {
    private List<IObserver> observers;
    private float temperature;

    public WeatherStation() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Наблюдатель удален.");
        } else {
            System.out.println("Ошибка: Наблюдатель не найден.");
        }
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(temperature);
        }
    }

    public void setTemperature(float newTemperature) {
        System.out.println("Изменение температуры: " + newTemperature + "°C");
        temperature = newTemperature;
        notifyObservers();
    }
}

class WeatherDisplay implements IObserver {
    private String name;

    public WeatherDisplay(String name) {
        this.name = name;
    }

    @Override
    public void update(float temperature) {
        System.out.println(name + " показывает новую температуру: " + temperature + "°C");
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        WeatherDisplay mobileApp = new WeatherDisplay("Мобильное приложение");
        WeatherDisplay digitalBillboard = new WeatherDisplay("Электронное табло");
        WeatherDisplay email = new WeatherDisplay("Почта");

        weatherStation.registerObserver(mobileApp);
        weatherStation.registerObserver(digitalBillboard);
        weatherStation.registerObserver(email);

        weatherStation.setTemperature(25.0f);
        weatherStation.setTemperature(30.0f);
        
        WeatherDisplay nonObserver = new WeatherDisplay("Нет такого наблюдателя");
        weatherStation.removeObserver(nonObserver);

        weatherStation.removeObserver(digitalBillboard);
        weatherStation.setTemperature(28.0f);
    }
}
