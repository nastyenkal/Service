package mephi.b23902.service;

import view.ServiceForm;

/**
 *
 * @author Настя
 */
public class Service {

    public static void main(String[] args) {
        System.out.println("Запуск приложения...");
        
        YamlService yamlService = new YamlService();
        
        yamlService.generateAndSaveUsers();
        
        System.out.println("Инициализация графического интерфейса...");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServiceForm().setVisible(true);
            }
        });
    }
}
