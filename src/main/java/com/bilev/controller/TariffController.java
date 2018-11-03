package com.bilev.controller;

import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("roles")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @RequestMapping(value = "/newTariff")
    public ModelAndView newTariffPage() {
        return new ModelAndView("editTariff", "tariff", new Tariff());
    }

    @RequestMapping(value = "/newOption")
    public String newOptionPage(ModelMap model, @RequestParam("tariffId") Integer tariffId) {
        Tariff tariff = tariffService.findById(tariffId);
        Option option = new Option();
        option.setTariff(tariff);
        model.addAttribute("option", option);
        return "editOption";
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public String addOption(@ModelAttribute("option") Option option, RedirectAttributes redirectAttributes) {
        //tariffService.saveTariff(tariff);
        tariffService.saveOption(option);
        redirectAttributes.addAttribute("tariffId", option.getTariff().getId());
        return "redirect:/tariff";
    }

//    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
//    public String addOption(@RequestParam("optionID") Integer tariffId, RedirectAttributes redirectAttributes) {
//        //tariffService.saveTariff(tariff);
//        tariffService.saveOption(option);
//        redirectAttributes.addAttribute("tariffId", option.getTariff().getId());
//        return "redirect:/tariff";
//    }


    @RequestMapping(value = "/addTariff", method = RequestMethod.POST)
    public String addTariff(@Valid @ModelAttribute("tariff") Tariff tariff, RedirectAttributes redirectAttributes) {
        tariffService.saveTariff(tariff);
        redirectAttributes.addAttribute("tariffId", tariff.getId());
        return "redirect:/tariff";
    }

    @RequestMapping(value = "/tariff")
    public String tariff(ModelMap model, @RequestParam("tariffId") Integer tariffId) {
        Tariff tariff = tariffService.findById(tariffId);
        model.addAttribute("tariff", tariff);
        return "tariff";
    }

    @RequestMapping(value = "/tariffs")
    public ModelAndView tariffs() {
        return new ModelAndView("tariffs", "tariffs", tariffService.getAllTariffs());
    }
}
