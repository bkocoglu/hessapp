package com.hessapp.infoservice.controller;

import com.hessapp.infoservice.client.chat.ChatClient;
import com.hessapp.infoservice.client.spend.SpendClient;
import com.hessapp.infoservice.client.spend.dto.*;
import com.hessapp.infoservice.dto.DebtsDTO;
import com.hessapp.infoservice.dto.GeneralResponseDTO;
import com.hessapp.infoservice.dto.GroupStatusRequestDTO;
import com.hessapp.infoservice.dto.GroupStatusResponseDTO;
import com.hessapp.infoservice.service.UserInfoService;
import com.hessapp.infoservice.service.ValidationService;
import com.hessapp.infoservice.providers.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = SpendController.END_POINT)
public class SpendController {
    static final String END_POINT = "/spend";
    static final Logger LOGGER = LoggerFactory.getLogger(SpendController.class);

    private ValidationService validationService;
    private SpendClient spendClient;
    private UserInfoService userInfoService;
    private ChatClient chatClient;

    SpendController(ValidationService validationService,
                    SpendClient spendClient,
                    UserInfoService userInfoService,
                    ChatClient chatClient){
        this.validationService = validationService;
        this.spendClient = spendClient;
        this.userInfoService = userInfoService;
        this.chatClient = chatClient;
    }

    @CrossOrigin
    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createSpend(@RequestBody CreateSpendRequestDTO createSpendRequestDTO){
        LOGGER.info(createSpendRequestDTO.toString());
        boolean isValid = validationService.isValidCreateSpendRequest(createSpendRequestDTO);

        if (!isValid){
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.validationError)
                            .build()
            );
        }

        CreateSpendResponseDTO createSpendResponseDTO = spendClient.clientCreateSpend(createSpendRequestDTO);

        if (createSpendResponseDTO == null) {
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.spendCreateClientNotWorking)
                            .build()
            );
        }

        List<String> from = new ArrayList<>();
        from.add(createSpendResponseDTO.getFrom());

        List<String> name = userInfoService.getUserNames(from);

        createSpendResponseDTO.setFrom(name.get(0));

        chatClient.createSpendMessage(createSpendResponseDTO);
        return ResponseEntity.ok().body(createSpendResponseDTO);
    }

    @CrossOrigin
    @GetMapping(
            value = "/{gruopID}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity listSpend(@PathVariable("gruopID") int groupId){
        if ( groupId <= 0 ) {
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.validationError)
                            .build()
            );
        }

        ListSpendsResponseDTO listSpendsResponseDTO = spendClient.clientListSpends(groupId);

        if(listSpendsResponseDTO == null) {
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.spendListNotWorking)
                            .build()
            );
        }
        List<String> froms = new ArrayList<>();
        for (com.hessapp.infoservice.client.spend.dto.Spend spend: listSpendsResponseDTO.getSpends()) {
            froms.add(spend.getFrom());
        }

        List<String> names = userInfoService.getUserNames(froms);

        for (com.hessapp.infoservice.client.spend.dto.Spend spend: listSpendsResponseDTO.getSpends()) {
            spend.setFrom(names.remove(0));
        }

        return ResponseEntity.ok().body(listSpendsResponseDTO);
    }

    @CrossOrigin
    @GetMapping(
            value = "/status/general/{nickname}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getGeneralStatus(@PathVariable("nickname") String nickname){
        GeneralStatusListDTO res = spendClient.clientGeneralStatus(nickname);

        if ( res == null) {
            return ResponseEntity.ok().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.spendGeneralStatusNotWorking)
                            .build()
            );
        }

        return ResponseEntity.ok(res);
    }


    @CrossOrigin
    @PostMapping(
            value = "/status/group",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getGroupStatus(@RequestBody GroupStatusRequestDTO groupStatusRequestDTO){
        boolean isValid = validationService.isValidGroupStatusReq(groupStatusRequestDTO);

        if (!isValid) {
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.validationError)
                            .build()
            );
        }

        GroupStatusResponseDTO res = spendClient.clientGroupStatus(groupStatusRequestDTO);

        if ( res == null) {
            return ResponseEntity.ok().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.spendGeneralStatusNotWorking)
                            .build()
            );
        }


        if (res.getListDebt() != null) {

            List<DebtsDTO> debts = res.getListDebt();

            List<String> names = new ArrayList<>();

            for (DebtsDTO debt: debts) {
                names.add(debt.getFrom());
            }

            names = userInfoService.getUserNames(names);

            for (DebtsDTO debt: debts) {
                debt.setFrom(names.remove(0));
            }

            res.setListDebt(debts);
        } else {
            res.setListDebt(new ArrayList<DebtsDTO>());
        }

        return ResponseEntity.ok(res);
    }

    @CrossOrigin
    @GetMapping(
            value = "/payDebt/{activityId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity payDebt(@PathVariable("activityId") int activityId){
        if (activityId<=0){
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.validationError)
                            .build()
            );
        }

        PayDebtResponseDTO res = spendClient.clientPayDebt(activityId);

        if (res == null) {
            return ResponseEntity.ok().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.spendPayDebtNotWorking)
                            .build()
            );
        }

        return ResponseEntity.ok(res);
    }
}
