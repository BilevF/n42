package com.bilev.controller.rest;


import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;

import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.MessageService;
import com.bilev.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/rest/tariff")
public class TariffRestController {

    private final TariffService tariffService;
    private final MessageService messageService;


    @Autowired
    public TariffRestController(TariffService tariffService, MessageService messageService) {
        this.tariffService = tariffService;
        this.messageService = messageService;
    }


    @PostMapping
    public Integer addTariff(@RequestBody @Valid BasicTariffDto tariff) throws OperationFailed {

        int tariffId = tariffService.saveTariff(tariff);
        messageService.notifyTariffChanged();
        return tariffId;
    }

    @DeleteMapping
    public void removeTariff(@RequestParam("tariffId") Integer tariffId) throws OperationFailed {

        tariffService.removeTariff(tariffId);
        messageService.notifyTariffChanged();
    }

    @PostMapping(value = "/option")
    public void newOptionAction(@RequestBody @Valid BasicOptionDto option) throws OperationFailed {

        tariffService.saveOption(option);
    }

    @DeleteMapping(value = "/option")
    public void removeOption(@RequestParam("optionId") Integer optionId) throws OperationFailed {

        tariffService.removeOption(optionId);
    }

    @PatchMapping(value = "/block")
    public void blockTariff(@RequestParam("tariffId") Integer tariffId) throws OperationFailed {

        tariffService.blockTariff(tariffId);
        messageService.notifyTariffChanged();
    }

    @PatchMapping(value = "/unblock")
    public void unblockTariff(@RequestParam("tariffId") Integer tariffId) throws OperationFailed {

        tariffService.unblockTariff(tariffId);
        messageService.notifyTariffChanged();
    }

    @PatchMapping(value = "/replace")
    public void replaceTariffAction(@RequestParam("tariffId") Integer tariffId,
                                    @RequestParam("replaceId") Integer replaceId) throws OperationFailed {

        tariffService.replaceTariff(tariffId, replaceId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/list")
    public Collection<BasicTariffDto> getAvailableTariffs() throws OperationFailed {
        return tariffService.getAvailableTariffs();
    }

}
