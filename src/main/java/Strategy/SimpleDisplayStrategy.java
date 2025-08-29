/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Strategy;

import model.User;

/**
 *
 * @author Настя
 */
public class SimpleDisplayStrategy implements DisplayStrategy{
    @Override
    public String format(User user) {
        return user.toString();
    }
}
