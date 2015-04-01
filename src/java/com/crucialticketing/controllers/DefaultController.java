package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.ApplicationService;
import com.crucialticketing.daos.services.AttachmentService;
import com.crucialticketing.daos.services.SeverityService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.TicketTypeService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.daos.services.WorkflowService;
import com.crucialticketing.daos.services.WorkflowStatusService;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.UploadedFile;
import com.crucialticketing.entities.UploadedFileLog;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/main/")
@Controller
public class DefaultController {

    @Autowired
    TicketTypeService ticketTypeService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    SeverityService severityService;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserQueueConService userQueueConService;

    @Autowired
    AttachmentService attachmentService;

    @RequestMapping(value = "/{pagename}/", method = RequestMethod.GET)
    public String index(@PathVariable(value = "pagename") String pageName, ModelMap map) {
        //  map.addAttribute("uploadedFile", new UploadedFile());
        map.addAttribute("page", "menu/" + pageName + ".jsp");
        return "mainview";
    }

    @RequestMapping(value = "/hub/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        user.setUserQueueConList(userQueueConService.getQueueListByUserId(user.getUserId()));

        ArrayList<String> queueList = new ArrayList<>();

        for (UserQueueCon userQueueCon : user.getUserQueueConList()) {
            queueList.add(String.valueOf(userQueueCon.getQueue().getQueueId()));
        }

        ArrayList<String> emptyList = new ArrayList<>();
        List<Ticket> ticketList = ticketService.getListByCriteria(emptyList, emptyList, emptyList, emptyList,
                emptyList, emptyList, queueList, emptyList, emptyList, emptyList, "", "", "", "");

        map.addAttribute("ticketList", ticketList);
        map.addAttribute("page", "menu/hub.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/reporting/", method = RequestMethod.GET)
    public String reporting(ModelMap map) {
        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("applicationList", applicationService.getApplicationList());
        map.addAttribute("severityList", severityService.getList());
        map.addAttribute("workflowList", workflowService.getList());
        map.addAttribute("workflowStatusList", workflowStatusService.getList());
        map.addAttribute("userList", userService.getList());

        map.addAttribute("page", "menu/reporting.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/profile/", method = RequestMethod.GET)
    public String profile(HttpServletRequest request, ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        File file = new File(request.getServletContext().getRealPath("/profile/" + user.getUserId() + ".png"));

        if ((file.exists()) && (!file.isDirectory())) {
            map.addAttribute("profileImageLocation", request.getContextPath() + "/profile/" + user.getUserId() + ".png");
        } else {
            map.addAttribute("profileImageLocation", request.getContextPath() + "/profile/default.png");
        }

        map.addAttribute("page", "menu/profile.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/profile/save/", method = RequestMethod.POST)
    public String saveProfile(HttpServletRequest request,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        this.profileUploadHandler(request, uploadedFileLog.getFiles().get(0), user);

        map.addAttribute("success", "Profile updated, your profile may take a few minutes to update");
        return this.profile(request, map);
    }

    private void profileUploadHandler(
            HttpServletRequest request,
            UploadedFile uploadedFile,
            User user) {
        try {
            if (!uploadedFile.getFile().isEmpty()) {
                byte[] bytes = uploadedFile.getFile().getBytes();

                String fileName = uploadedFile.getFile().getOriginalFilename();

                String extension = "png";

                String saveFile = request.getServletContext().getRealPath("/profile/" + user.getUserId() + "." + extension);

                try (FileOutputStream output = new FileOutputStream(new File(saveFile))) {
                    output.write(bytes);
                }
            }
        } catch (Exception e) {
        }
    }
}
