package com.hassapp.api.service;

import com.hassapp.api.assembler.RegisterDTOAssembler;
import com.hassapp.api.dto.RegisterRequestDTO;
import com.hassapp.api.enums.RandomType;
import com.hassapp.api.exceptions.AlreadyTakenException;
import com.hassapp.api.exceptions.DatabaseException;
import com.hassapp.api.model.AccountActivateCode;
import com.hassapp.api.model.User;
import com.hassapp.api.repository.AccountActivateCodeRepository;
import com.hassapp.api.exceptions.AccountActivateException;
import com.hassapp.api.repository.UserRepository;
import com.hassapp.api.providers.RandomPinProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:message.properties")
public class RegisterService {
    static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);
    static final int NIC_COUNT = 6;
    public static final int ACCOUNTCODE_COUNT = 15;

    private UserRepository userRepository;
    private RegisterDTOAssembler registerDTOAssembler;
    private AccountActivateCodeRepository accountActivateCodeRepository;

    RegisterService(UserRepository userRepository,
                    RegisterDTOAssembler registerDTOAssembler,
                    AccountActivateCodeRepository accountActivateCodeRepository){
        this.userRepository = userRepository;
        this.registerDTOAssembler = registerDTOAssembler;
        this.accountActivateCodeRepository = accountActivateCodeRepository;
    }

    public boolean alreadyTakenMail(String mail){
        try {
            List<User> users = userRepository.findByMail(mail);
            if (!users.isEmpty()){
                LOGGER.error("Daha önceden kayıtlı kullanıcı tespit edildi.");
                throw new AlreadyTakenException(alreadyTakenMail);
            }
            return false;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new DatabaseException();
        }

    }

    public User createAndSave(RegisterRequestDTO registerRequestDTO, String userType){
        try {
            String nicname = RandomPinProvider.createRandomPin(RandomType.NICNAME, NIC_COUNT);

            User user = registerDTOAssembler.convertRegisterDTOtoUser(registerRequestDTO, nicname, userType);

            userRepository.save(user);

            return user;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new DatabaseException();
        }
    }

    public String setActivatedCode(User user) {
        String activationCode = RandomPinProvider.createRandomPin(RandomType.ACTIVATIONCODE, ACCOUNTCODE_COUNT);

        AccountActivateCode accountActivateCode = new AccountActivateCode();
        accountActivateCode.setUser(user);
        accountActivateCode.setDate(LocalDateTime.now());
        accountActivateCode.setActivateCode(activationCode);

        try {
            accountActivateCodeRepository.save(accountActivateCode);
        }catch (Exception e){
            throw new DatabaseException();
        }

        return activationCode;
    }

    public void accountActivate(String activationCode){
        List<AccountActivateCode> accountActivateList = accountActivateCodeRepository.getAccountActivateCodeByActivateCode(activationCode);

        if (accountActivateList == null || accountActivateList.isEmpty()) {
            throw new AccountActivateException(activationCodeUnauthorization);
        }

        AccountActivateCode accountActivate = accountActivateList.get(0);

        Optional<User> userOptional = userRepository.findById(accountActivate.getUser().getNicname());
        User user = userOptional.get();

        if (user.isActive()){
            throw new AccountActivateException(accountAlreadyActive);
        }

        user.setActive(true);
        userRepository.save(user);
    }

    public boolean deleteUser(String nickname){
        try {
            userRepository.deleteById(nickname);

            return true;
        }catch (Exception e){
            LOGGER.error("Kayıt oluştururken QR servis hatası ile karşılaşıldı fakat oluşturulmuş user silinemedi.");
            LOGGER.error(e.toString());
            return false;
        }
    }

    @Value("${exception.register.alreadyTokenMail}")
    private String alreadyTakenMail;

    @Value("${exception.activation.unauthorize}")
    private String activationCodeUnauthorization;

    @Value("${exception.activation.alreadyActive}")
    private String accountAlreadyActive;

}
