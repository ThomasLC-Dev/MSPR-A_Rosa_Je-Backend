package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.ForgotPasswordToken;
import fr.cttt.arosaje.model.User;

public interface ForgotPasswordTokenService {
    ForgotPasswordToken getForgotPasswordTokenByToken(String token);
    ForgotPasswordToken saveForgotPasswordToken(User user);
    void deleteForgotPasswordTokenByUser(User user);
}
