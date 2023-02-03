package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.ForgotPasswordToken;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.repository.ForgotPasswordTokenRepository;
import fr.cttt.arosaje.service.ForgotPasswordTokenService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class ForgotPasswordTokenServiceImpl implements ForgotPasswordTokenService {
    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    public ForgotPasswordTokenServiceImpl(ForgotPasswordTokenRepository forgotPasswordTokenRepository) {
        this.forgotPasswordTokenRepository = forgotPasswordTokenRepository;
    }

    @Override
    public ForgotPasswordToken getForgotPasswordTokenByToken(String token) {
        return forgotPasswordTokenRepository.findByToken(token).orElseThrow(() -> new ElementNotFoundException("Token not found !"));
    }

    @Transactional
    @Override
    public ForgotPasswordToken saveForgotPasswordToken(User user) {
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        String token = UUID.randomUUID().toString()+ Instant.now().toString();
        forgotPasswordToken.setToken(token);
        forgotPasswordToken.setUser(user);
        return forgotPasswordTokenRepository.save(forgotPasswordToken);
    }

    @Transactional
    @Override
    public void deleteForgotPasswordTokenByUser(User user) {
        forgotPasswordTokenRepository.deleteAllByUserId(user.getId());
    }
}
