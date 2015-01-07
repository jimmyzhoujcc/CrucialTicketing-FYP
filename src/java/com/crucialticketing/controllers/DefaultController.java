package com.crucialticketing.controllers;

import com.crucialticketing.entities.Person;
import com.crucialticketing.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/{pagename}/", method = RequestMethod.GET)
    public String index(@PathVariable(value = "pagename") String pageName, ModelMap map) {
        //personService.getPersonById("1");

        map.addAttribute("page", "menu/" + pageName + ".jsp");
        return "mainview";
    }

    @RequestMapping(value = "/crearrete/", method = RequestMethod.GET)
    public String demo(ModelMap map) {
        
        /*Person person = new Person();
        person.setName("Jack");
        person.setAge(52);
                
        map.put("personObject", person); */
        map.addAttribute("page", "menu/create.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/person/{name}", method = RequestMethod.GET)
    public String demo(@PathVariable(value = "name") String name, ModelMap map) {

        //Normally the parameter would be used to retrieve the object
        //In this case we keep it simple and return the name
        Person person = new Person();
        person.setName(name);
        person.setAge(52);
        map.put("personObject", person);

        map.addAttribute("helloAgain", "The name passed in is the name returned.");
        return "demo";
    }

    @RequestMapping(value = "/person/{name}/{age}", method = RequestMethod.GET)
    public String demo(@PathVariable(value = "name") String name, @PathVariable(value = "age") Integer age, ModelMap map) {

        //Normally the parameter would be used to retrieve the object
        //In this case we keep it simple and return the name
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        map.put("personObject", person);

        map.addAttribute("helloAgain", "The name passed in along with the age.");
        return "demo";
    }

    @RequestMapping(value = "/paramdemo", method = RequestMethod.GET)
    public String paramDemo(ModelMap map) {

        map.addAttribute("id", "Not passed In");
        map.addAttribute("other", "Not Passed In");

        return "paramdemo";
    }

    @RequestMapping(value = "/paramdemo1", method = RequestMethod.GET)
    public String paramDemo1(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "other", required = true) String other,
            ModelMap map) {

        map.addAttribute("id", id);
        map.addAttribute("other", other);

        return "paramdemo";
    }

    @RequestMapping(value = "/paramdemo2", method = RequestMethod.GET)
    public String paramDemo2(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "other", required = false) String other,
            ModelMap map) {

        map.addAttribute("id", id);

        if (other != null) {
            map.addAttribute("other", other);
        } else {
            map.addAttribute("other", "Not passed in");
        }

        return "paramdemo";
    }

}