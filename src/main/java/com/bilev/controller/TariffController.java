package com.bilev.controller;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@SessionAttributes("roles")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @RequestMapping(value = "/newTariff")
    public ModelAndView newTariffPage() {
        return new ModelAndView("editTariff", "tariff", new TariffDto());
    }

    @RequestMapping(value = "/addTariff", method = RequestMethod.POST)
    public String addTariff(@Valid @ModelAttribute("tariff") TariffDto tariff, RedirectAttributes redirectAttributes) {
        int tariffId = tariffService.saveTariff(tariff);
        redirectAttributes.addAttribute("tariffId", tariffId);
        return "redirect:/tariff";
    }

    @RequestMapping(value = "/tariff")
    public String tariff(ModelMap model, @RequestParam("tariffId") Integer tariffId) {
        TariffDto tariff = tariffService.getTariff(tariffId);
        model.addAttribute("tariff", tariff);
        return "tariff";
    }

    @RequestMapping(value = "/tariffs")
    public ModelAndView tariffs() {
        return new ModelAndView("tariffs", "tariffs", tariffService.getAllTariffs());
    }

    @RequestMapping(value = "/newOption")
    public String newOptionPage(ModelMap model, @RequestParam("tariffId") Integer tariffId) {

        BasicOptionDto option = new OptionDto();
        option.setTariffId(tariffId);
        option.setRelatedOptions(new ArrayList<>(tariffService.getBasicTariffOptions(tariffId)));
        model.addAttribute("option", option);
        return "editOption";
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public String addOption(@ModelAttribute("option") BasicOptionDto basicOptionDto,
                            RedirectAttributes redirectAttributes) {
        tariffService.saveOption(basicOptionDto);
        redirectAttributes.addAttribute("tariffId", basicOptionDto.getTariffId());
        return "redirect:/tariff";
    }

    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public String addOption(@RequestParam("optionId") Integer optionId,
                            @RequestParam("tariffId") Integer tariffId,
                            RedirectAttributes redirectAttributes) {
        tariffService.removeOption(optionId);
        redirectAttributes.addAttribute("tariffId", tariffId);
        return "redirect:/tariff";
    }


}
