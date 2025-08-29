package mephi.b23902.service;

import model.DatabaseManager;
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
        
        DatabaseManager dbManager = new DatabaseManager();
        
        final int sessionId = dbManager.startNewSession();
        
        Runtime.getRuntime().addShutdownHook(new Thread(dbManager::close));
        
        System.out.println("Инициализация графического интерфейса...");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServiceForm(dbManager, sessionId).setVisible(true);
            }
        });
    }
}
