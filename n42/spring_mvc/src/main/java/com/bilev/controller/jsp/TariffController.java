package com.bilev.controller.jsp;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/tariff")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private ContractService contractService;

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(final Exception e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", "Something bad happened");

        return model;
    }

    @ExceptionHandler(OperationFailed.class)
    public ModelAndView operationFailedHandler(final OperationFailed e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", e.getMessage());

        return model;
    }


    @GetMapping(value = "/new")
    public ModelAndView newTariffPage() {
        return new ModelAndView("createTariff", "tariff", new BasicTariffDto());
    }


    @GetMapping
    public String tariffPage(@RequestParam("tariffId") Integer tariffId, ModelMap model) throws OperationFailed {

        TariffDto tariff = tariffService.getTariff(tariffId);

        model.addAttribute("tariff", tariff);

        model.addAttribute("clientsCount", contractService.contractCountWithTariff(tariffId));

        return "tariff";
    }

    @GetMapping(value = "/list")
    public String tariffListPage(ModelMap model) throws OperationFailed {
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        model.addAttribute("title", "Tariff management");
        model.addAttribute("path", "tariff");
        model.addAttribute("method", "get");
        model.addAttribute("hiddenName", "");
        model.addAttribute("hiddenValue", "");
        model.addAttribute("btnName", "Manage");
        model.addAttribute("showBtn", true);

        return "tariffs";
    }

    @GetMapping(value = "/list/available")
    public String availableTariffsPage(ModelMap model) throws OperationFailed {
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());
        model.addAttribute("title", "N42 Tariffs");
        model.addAttribute("path", "");
        model.addAttribute("method", "get");
        model.addAttribute("hiddenName", "");
        model.addAttribute("hiddenValue", "");
        model.addAttribute("btnName", "");
        model.addAttribute("showBtn", false);

        return "tariffs";
    }

    @GetMapping(value = "/option/new")
    public String newOptionPage(@RequestParam("tariffId") Integer tariffId, ModelMap model) throws OperationFailed {

        BasicOptionDto option = new BasicOptionDto();
        option.setTariffId(tariffId);
        option.setRelatedOptions(new ArrayList<>(tariffService.getTariffBasicOptions(tariffId)));
        model.addAttribute("option", option);
        return "createOption";
    }


    @GetMapping(value = "/replace")
    public String replaceTariffPage(@RequestParam("tariffId") Integer tariffId, ModelMap model) throws OperationFailed {

        model.addAttribute("tariffs", tariffService.getAvailableTariffs());
        model.addAttribute("title", "Select tariff for replacement");
        model.addAttribute("path", "/replaceTariff");
        model.addAttribute("method", "post");
        model.addAttribute("hiddenName", "originTariffId");
        model.addAttribute("hiddenValue", tariffId);
        model.addAttribute("btnName", "Select");
        model.addAttribute("showBtn", true);

        return "tariffs";
    }

}
