package Main;

import Controller.Service;
import View.UI;

public class Main {
    public static void main(String[] args) {

        Service service = new Service();
        UI ui = new UI(service);
        ui.start();
    }
}
