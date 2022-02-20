package com.banktemplate.controller;

import com.banktemplate.common.dto.CreditDto;
import com.banktemplate.common.helper.ApiHelper;
import com.banktemplate.common.helper.RestHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/credit")
public class CreditController {

    //GET Credit Insert Form
    // http://localhost:8090/credit/insert
    @GetMapping("/insert")
    public String getCreditInsertForm(Model model){
        model.addAttribute("credit_dto",new CreditDto());
        return "credit/insert";
    }

    //POST Credit Insert Form
    // http://localhost:8090/credit/insert
    @PostMapping("/insert")
    public String postCreditInsertForm(Model model, @Valid @ModelAttribute("credit_dto") CreditDto dto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "credit/insert";
        }
        else {
            String url = ApiHelper.baseApiUrl + ApiHelper.postInsertCredit;
            HttpEntity<CreditDto> httpEntity = new HttpEntity<CreditDto>(dto);
            ResponseEntity<CreditDto> response = RestHelper.getRestTemplate().exchange(url, HttpMethod.POST,httpEntity,CreditDto.class);

            String result = response.getHeaders().getFirst("response");
            if(result.equals("true")){
                CreditDto newDto = response.getBody();

                model.addAttribute("response_message","Kaydınız başarıyla oluşturuldu.");
                model.addAttribute("response_dto",newDto);

                return "global/success";

            }
            else{
                model.addAttribute("response_message","Girilen kullanıcının kaydı bulunmaktadır.");
                return "credit/insert";
            }
        }
    }

    //GET Find Credit
    // http://localhost:8090/credit/search
    @GetMapping("/search")
    public String getCreditSearchForm(){

        return "credit/search";
    }

    //GET Update Form - Partial Page
    // http://localhost:8090/credit/update/2
    @GetMapping("/update/{identity_number}")
    public String getCreditUpdateForm(@PathVariable(value = "identity_number") String identity_number, Model model){

        String url = ApiHelper.baseApiUrl + ApiHelper.getCreditByIdentityNumber;
        url += identity_number;

        var response = RestHelper.getRestTemplate().exchange(url, HttpMethod.GET,HttpEntity.EMPTY ,CreditDto.class);

        String result = response.getHeaders().getFirst("response");

        if(result.equals("true")){
            model.addAttribute("credit_dto",response.getBody());
            model.addAttribute("response_message",null);
            return "credit/update";
        }
        else{
            model.addAttribute("response_message","Kullanıcı bulunamadı");
            return "global/fail";
        }
    }

    //PUT Update Form
    // http://localhost:8090/credit/update
    @PostMapping("/update")
    public String putCreditUpdateForm(Model model, @Valid @ModelAttribute("credit_dto") CreditDto dto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            model.addAttribute("response_message","Kullanıcı bilgilerini yanlış doldurdunuz !");
            return "global/fail";
        }
        else {
            String url = ApiHelper.baseApiUrl + ApiHelper.putUpdateCredit;
            HttpEntity<CreditDto> httpEntity = new HttpEntity<CreditDto>(dto);
            ResponseEntity<CreditDto> response = RestHelper.getRestTemplate().exchange(url, HttpMethod.PUT,httpEntity,CreditDto.class);

            String result = response.getHeaders().getFirst("response");
            if(result.equals("true")){
                CreditDto newDto = response.getBody();

                model.addAttribute("response_message","Kaydınız başarıyla güncellendi");
                model.addAttribute("response_dto",newDto);

                return "global/success";
            }
            else{
                model.addAttribute("response_message","Girilen kullanıcının kaydı bulunmamaktadır.");
                return "global/fail";
            }
        }
    }

    //DELETE Credit
    // http://localhost:8090/credit/delete/2
    @GetMapping("/delete/{id}")
    public String deleteCredit(@PathVariable(value = "id") Long id, Model model){

        String url = ApiHelper.baseApiUrl + ApiHelper.deleteCredit;
        url += id.toString();

        var response = RestHelper.getRestTemplate().exchange(url, HttpMethod.DELETE,HttpEntity.EMPTY ,Boolean.class);

        boolean result = response.getBody();

        if(result){
            model.addAttribute("response_message","Kullanıcı başarıyla silindi");
            return "global/success";
        }
        else{
            model.addAttribute("response_message","Kullanıcı bulunamadı");
            return "global/fail";
        }
    }
}
