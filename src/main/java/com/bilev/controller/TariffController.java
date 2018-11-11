package com.bilev.controller;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToRemoveException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;
import com.bilev.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception e) {

        return "forward:/serverError";
    }

    @RequestMapping(value = "/newTariff")
    public ModelAndView newTariff() {
        return new ModelAndView("editTariff", "tariff", new BasicTariffDto());
    }

    @RequestMapping(value = "/newTariff", method = RequestMethod.POST)
    public String newTariffAction(ModelMap model, @Valid @ModelAttribute("tariff") BasicTariffDto tariff,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "editTariff";

        try {
            int tariffId = tariffService.saveTariff(tariff);
            redirectAttributes.addAttribute("tariffId", tariffId);
            return "redirect:/tariff";
        } catch (UnableToSaveException e) {
            model.addAttribute("exception", e.getMessage());
            return "editTariff";
        }
    }

    @RequestMapping(value = "/tariff")
    public String tariff(ModelMap model, @RequestParam("tariffId") Integer tariffId,
                         RedirectAttributes redirectAttributes) {
        try {
            TariffDto tariff = tariffService.getTariff(tariffId);
            model.addAttribute("tariff", tariff);
            return "tariff";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/tariffs";
        }
    }

    @RequestMapping(value = "/tariffs")
    public String tariffs(ModelMap model) {
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        model.addAttribute("title", "Tariff management");
        model.addAttribute("path", "/tariff");
        model.addAttribute("method", "get");
        model.addAttribute("hiddenName", "");
        model.addAttribute("hiddenValue", "");
        model.addAttribute("btnName", "Manage");

        return "tariffs";
    }

    @RequestMapping(value = "/newOption")
    public String newOption(ModelMap model, @RequestParam("tariffId") Integer tariffId,
                                RedirectAttributes redirectAttributes) {

        try {
            BasicOptionDto option = new BasicOptionDto();
            option.setTariffId(tariffId);
            option.setRelatedOptions(new ArrayList<>(tariffService.getBasicTariffOptions(tariffId)));
            model.addAttribute("option", option);
            return "editOption";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("tariffId", tariffId);
            return "redirect:/tariff";
        }

    }

    @RequestMapping(value = "/newOption", method = RequestMethod.POST)
    public String newOptionAction(@Valid @ModelAttribute("option") BasicOptionDto option,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            ModelMap model) {
        try {
            if (bindingResult.hasErrors()) {
                option.setRelatedOptions(new ArrayList<>(tariffService.getBasicTariffOptions(option.getTariffId())));
                model.addAttribute("option", option);
                return "editOption";
            }

            try {
                tariffService.saveOption(option);
            } catch (UnableToSaveException | NotFoundException e) {
                option.setRelatedOptions(new ArrayList<>(tariffService.getBasicTariffOptions(option.getTariffId())));
                model.addAttribute("option", option);
                model.addAttribute("exception", e.getMessage());
                return "editOption";
            }
            redirectAttributes.addAttribute("tariffId", option.getTariffId());
            return "redirect:/tariff";

        }  catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("tariffId", option.getTariffId());
            return "redirect:/tariff";
        }
    }


    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public String removeOption(@RequestParam("optionId") Integer optionId,
                            @RequestParam("tariffId") Integer tariffId,
                            RedirectAttributes redirectAttributes) {
        try {
            tariffService.removeOption(optionId);
        } catch (UnableToRemoveException | UnableToUpdateException | NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("tariffId", tariffId);
        return "redirect:/tariff";
    }


    @RequestMapping(value = "/changeTariffStatus", method = RequestMethod.POST)
    public String changeTariffStatus(@RequestParam("tariffId") Integer tariffId,
                            RedirectAttributes redirectAttributes) {
        try {
            tariffService.changeTariffStatus(tariffId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("tariffId", tariffId);
        return "redirect:/tariff";
    }

    @RequestMapping(value = "/replaceTariff", method = RequestMethod.GET)
    public String replaceTariff(ModelMap model,
                                @RequestParam("tariffId") Integer tariffId) {
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());
        model.addAttribute("title", "Select tariff for replacement");
        model.addAttribute("path", "/replaceTariff");
        model.addAttribute("method", "post");
        model.addAttribute("hiddenName", "originTariffId");
        model.addAttribute("hiddenValue", tariffId);
        model.addAttribute("btnName", "Select");

        return "tariffs";
    }

    @RequestMapping(value = "/replaceTariff", method = RequestMethod.POST)
    public String replaceTariffAction(ModelMap model,
                                      @RequestParam("tariffId") Integer tariffId,
                                      @RequestParam("originTariffId") Integer originTariffId,
                                      RedirectAttributes redirectAttributes) {
        try {
            tariffService.replaceTariff(originTariffId, tariffId);
            redirectAttributes.addAttribute("tariffId", originTariffId);
            return "redirect:/tariff";
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("tariffId", originTariffId);
            return "redirect:/replaceTariff";
        }
    }

    @RequestMapping(value = "/removeTariff", method = RequestMethod.POST)
    public String removeTariff(@RequestParam("tariffId") Integer tariffId,
                               RedirectAttributes redirectAttributes) {
        try {
            tariffService.removeTariff(tariffId);
            return "redirect:/tariffs";
        } catch (NotFoundException | UnableToRemoveException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("tariffId", tariffId);
            return "redirect:/tariff";
        }
    }
}
