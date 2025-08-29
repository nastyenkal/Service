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
public class DetailedDisplayStrategy implements DisplayStrategy{
    @Override
    public String format(User user) {
        return String.format("[ID: %s] %s (%s) | Status: %s",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserType());
    }
}
