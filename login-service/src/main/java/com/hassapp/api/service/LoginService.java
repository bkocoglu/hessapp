package com.hassapp.api.service;

import com.hassapp.api.assembler.LoginUserAssembler;
import com.hassapp.api.client.group.GroupClient;
import com.hassapp.api.client.message.MessageClient;
import com.hassapp.api.client.message.dto.MessagesResponse;
import com.hassapp.api.dto.LoginResponseDTO;
import com.hassapp.api.exceptions.UserNotFoundException;
import com.hassapp.api.model.User;
import com.hassapp.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:message.properties")
public class LoginService {
    static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

    private UserRepository userRepository;
    private GroupClient groupClient;
    private GroupSettingService groupSettingService;
    private MessageClient messageClient;
    private LoginUserAssembler loginUserAssembler;

    LoginService(UserRepository userRepository,
                 GroupClient groupClient,
                 GroupSettingService groupSettingService,
                 MessageClient messageClient,
                 LoginUserAssembler loginUserAssembler){
        this.userRepository = userRepository;
        this.groupClient = groupClient;
        this.groupSettingService = groupSettingService;
        this.messageClient = messageClient;
        this.loginUserAssembler = loginUserAssembler;
    }

    public User findUser (String email, String password){
        List<User> users = userRepository.findUserByMailAndPassword(email, password);
        if (users.isEmpty()){
            LOGGER.error(loginFailed);
            throw new UserNotFoundException(loginFailed);
        }
        return users.get(0);

    }

    public LoginResponseDTO loginUserInfo(User user) {
        LoginResponseDTO loginResponseDTO = loginUserAssembler.createLoginResponse(user);

        List<com.hassapp.api.client.group.dto.Group> groups = groupClient.getGroupList(user.getNicname());

        List<com.hassapp.api.dto.Group> groupsview = groupSettingService.changeParticipant(groups);

        loginResponseDTO.setGroups(groupsview);

        //burada message servise gidilerek mesajlar alınacak.
        if (!groups.isEmpty() && groups != null) {
            MessagesResponse messagesResponse = messageClient.getMessages(user.getNicname(), groupsview);
            if (messagesResponse == null) {  //client te hata olursa
                loginResponseDTO = groupSettingService.setMessageEmpty(loginResponseDTO);
            } else if (messagesResponse.isOnlineUser()) { // kişi zaten başka bir yerde online demektir girişe izin verilmez !

            } else if (!messagesResponse.isOnlineUser()) {
                // .......DEVAMMMM........ MESAJLAR YERİNE YERLEŞTİRİLECEK
                loginResponseDTO = groupSettingService.messageToGroupMap(loginResponseDTO, messagesResponse.getGroupMessageMaps());
            }
        } else {        //hiçbir gruba dahil değilse
            loginResponseDTO = groupSettingService.setMessageEmpty(loginResponseDTO);
        }

        return loginResponseDTO;
    }

    public LoginResponseDTO getLoginResponseDTO() {
        return loginResponseDTO;
    }

    public void setLoginResponseDTO(LoginResponseDTO loginResponseDTO) {
        this.loginResponseDTO = loginResponseDTO;
    }

    @Value("${exception.login.fail}")
    private String loginFailed;
}
