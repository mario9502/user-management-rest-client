package bg.softuni.usermanagementappclient.web;

import bg.softuni.usermanagementappclient.model.dto.UserInfoDto;
import bg.softuni.usermanagementappclient.model.dto.UserNoIdInfoDto;
import bg.softuni.usermanagementappclient.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String viewAdd(){

        return "add-user";
    }

    @PostMapping("/add")
    public String daAdd(@Valid UserNoIdInfoDto user, Model model){

        model.addAttribute("userInfo", this.userService.add(user));

        return "show-added-user";
    }

    @GetMapping("/all")
    public String viewAll(Model model){

        List<UserInfoDto> allUsers = this.userService.getAll();

        model.addAttribute("allUsers", allUsers);

        return "show-all-users";
    }

    @PostMapping("/id")
    public String forwardId(@RequestParam String id) {

        return "redirect:/users/" + id;
    }

    @GetMapping("/{id}")
    public String viewById(@PathVariable UUID id, Model model){

        UserInfoDto userById = this.userService.getById(id);

        model.addAttribute("userInfo", userById);

        return "show-user";
    }

    @PostMapping("/email")
    public String forwardEmail(@RequestParam String email) {

        return "redirect:/users/email=" + email;
    }

    @GetMapping("/email={email}")
    public String viewByEmail(@PathVariable String email, Model model){

        UserNoIdInfoDto userByEmail = this.userService.getByEmail(email);

        model.addAttribute("userInfo", userByEmail);

        return "show-user";
    }

    @GetMapping("/allSorted")
    public String viewAllSorted(Model model){

        List<UserNoIdInfoDto> allSorted = this.userService.getAllSorted();

        model.addAttribute("allUsers", allSorted);

        return "show-all-users-sorted";
    }

    @PostMapping("/year")
    public String forwardYear(@RequestParam String year) {

        return "redirect:/users/all/year=" + year;
    }

    @GetMapping("/all/year={year}")
    public String viewAllWithYearOfBirthBefore(@PathVariable int year, Model model){

        List<UserNoIdInfoDto> allWithYearOfBirthBefore = this.userService.findAllWithYearOfBirthBefore(year);
        model.addAttribute("allUsers", allWithYearOfBirthBefore);

        return "show-all-users-sorted";
    }

    @GetMapping("/edit/select")
    public String selectUserToEdit(Model model){

        List<UserInfoDto> all = this.userService.getAll();
        model.addAttribute("allUsers", all);

        return "select-user-edit";
    }

    @PostMapping("/edit/email")
    public String forwardEditEmail(@RequestParam String email) {

        return "redirect:/users/edit/" + email;
    }

    @GetMapping("/edit/{email}")
    public String viewEditByEmail(@PathVariable String email, Model model){

        model.addAttribute("email", email);

        return "edit-user";
    }

    @PutMapping("/edit/{email}")
    public String doEditByEmail(@PathVariable String email, UserNoIdInfoDto editedInfo, Model model){

        UserNoIdInfoDto editedUser = this.userService.editByEmail(email, editedInfo);
        model.addAttribute("userInfo", editedUser);

        return "show-user";
    }

    @GetMapping("/delete/select")
    public String selectUserToDelete(Model model){

        List<UserInfoDto> all = this.userService.getAll();
        model.addAttribute("allUsers", all);

        return "select-user-delete";
    }

    @PostMapping("/delete/email")
    public String forwardDeleteEmail(@RequestParam String email) {

        return "redirect:/users/delete/" + email;
    }

    @GetMapping("/delete/{email}")
    public String viewDeleteByEmail(@PathVariable String email, Model model){

        UserNoIdInfoDto userByEmail = this.userService.getByEmail(email);

        model.addAttribute("userInfo", userByEmail);
        model.addAttribute("email", email);


        return "delete-user";
    }

    @DeleteMapping("/{email}")
    public String deleteByEmail(@PathVariable String email){

        this.userService.delete(email);

        return "redirect:/users/all";
    }
}
